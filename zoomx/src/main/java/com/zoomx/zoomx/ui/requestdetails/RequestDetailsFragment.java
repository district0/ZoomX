package com.zoomx.zoomx.ui.requestdetails;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zoomx.zoomx.R;
import com.zoomx.zoomx.model.RequestEntity;
import com.zoomx.zoomx.ui.requestlist.RequestListFragment;
import com.zoomx.zoomx.util.ColorUtils;
import com.zoomx.zoomx.util.FormatUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ahmed Fathallah on 2/17/2018.
 */

public class RequestDetailsFragment extends Fragment {

    public static final String BODY_URL_KEY = "url";
    public static final String BODY_JSON_KEY = "body";
    private static final String TEXT_DATA_TYPE = "text/plain";
    private RequestDetailsViewModel viewModel;
    private RequestEntity mRequestEntity;
    private View view;
    private RelativeLayout requestLayout, responseLayout;


    public static RequestDetailsFragment newInstance(int requestId) {
        Bundle args = new Bundle();
        args.putInt(RequestListFragment.REQUEST_ID, requestId);
        RequestDetailsFragment fragment = new RequestDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.request_details_fragment, container, false);
        ((RequestDetailsActivity) getActivity()).getSupportActionBar().setTitle(R.string.request_details_screen_title);
        setHasOptionsMenu(true);
        if (getArguments() != null && getArguments().containsKey(RequestListFragment.REQUEST_ID)) {
            int requestId = (int) getArguments().get(RequestListFragment.REQUEST_ID);
            viewModel = ViewModelProviders.of(this).get(RequestDetailsViewModel.class);
            viewModel.getRequestById(requestId).observe(this, new Observer<RequestEntity>() {
                @Override
                public void onChanged(@Nullable RequestEntity requestEntity) {
                    Map<String, String> item = new HashMap<>();
                    if (requestEntity != null) {
                        item.put(RequestListFragment.REQUEST_ID, requestEntity.getMethod());
                        initUi(requestEntity);
                    }
                }
            });
        }
        return view;
    }

    private void initUi(final RequestEntity requestEntity) {
        mRequestEntity = requestEntity;
        TextView methodTextView = view.findViewById(R.id.request_details_method_txt);
        TextView codeTextView = view.findViewById(R.id.request_details_code_txt);
        TextView urlTextView = view.findViewById(R.id.request_details_url_txt);
        TextView dateTextView = view.findViewById(R.id.request_details_startDate_txt);
        responseLayout = view.findViewById(R.id.request_details_response_layout);
        requestLayout = view.findViewById(R.id.request_details_request_body_layout);
        RecyclerView recyclerView = view.findViewById(R.id.request_details_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        RequestDetailsAdapter requestDetailsAdapter = new RequestDetailsAdapter(getHeadersKeyList(getHeaderMap(requestEntity))
                , getHeadersValueList(getHeaderMap(requestEntity)));

        recyclerView.setAdapter(requestDetailsAdapter);
        methodTextView.setText(requestEntity.getMethod());
        codeTextView.setText(String.valueOf(requestEntity.getCode()));
        codeTextView.setTextColor(ColorUtils.getCodeColor(requestEntity.getCode(), getContext()));
        urlTextView.setText(requestEntity.getUrl());
        dateTextView.setText(FormatUtil.formatDate(requestEntity.getStartDate(), FormatUtil.DATE_FORMAT));

        responseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBody(requestEntity.getUrl(), requestEntity.getResponseBody());
            }
        });

        requestLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBody(requestEntity.getUrl(), requestEntity.getRequestBody());
            }
        });
    }

    public void showBody(String url, String body) {
        Intent intent = new Intent(getActivity(), JsonViewActivity.class);
        intent.putExtra(BODY_URL_KEY, url);
        intent.putExtra(BODY_JSON_KEY, body);
        startActivity(intent);
    }

    public Map<String, String> getHeaderMap(RequestEntity requestEntity) {
        Map<String, String> headersMap = new HashMap<>();
        if (requestEntity.getResponseHeaders() != null && requestEntity.getResponseHeaders().getHeadersMap() != null) {
            headersMap.put(getString(R.string.response_headers), "");
            for (Map.Entry<String, String> entry : requestEntity.getResponseHeaders().getHeadersMap().entrySet()) {
                headersMap.put(entry.getKey(), entry.getValue());
            }
        }

        if (requestEntity.getRequestHeaders() != null && requestEntity.getRequestHeaders().getHeadersMap() != null) {
            headersMap.put(getString(R.string.request_headers), "");
            for (Map.Entry<String, String> entry : requestEntity.getRequestHeaders().getHeadersMap().entrySet()) {
                headersMap.put(entry.getKey(), entry.getValue());
            }
        }

        return headersMap;
    }

    public List getHeadersKeyList(Map<String, String> map) {
        List<String> list = new ArrayList<String>(map.keySet());
        return list;
    }

    public List getHeadersValueList(Map<String, String> map) {
        List<String> list = new ArrayList<String>(map.values());
        return list;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_requests_details, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_share_request) {
            shareRequest();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void shareRequest() {
        new RequestToFileTask().execute(mRequestEntity);
    }


    public class RequestToFileTask extends AsyncTask<RequestEntity, Void, File> {
        @Override
        protected File doInBackground(RequestEntity... requestEntities) {
            try {
                RequestEntity requestEntity = requestEntities[0];
                File tmpFile = File.createTempFile(getFileName(), ".txt", getActivity().getCacheDir());
                FileWriter writer = new FileWriter(tmpFile);
                writer.write(requestEntity.getUrl());
                writer.append("\n");
                writer.append(requestEntity.getRequestBody());
                writer.append("\n");
                writer.append(requestEntity.getResponseBody());
                writer.close();
                return tmpFile;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }


        @Override
        protected void onPostExecute(File file) {
            if (file != null) {
                Uri sharedFileUri = FileProvider.getUriForFile(getActivity(), "com.zoomx.fileprovider", file);
                Intent share = ShareCompat.IntentBuilder.from(getActivity())
                        .setType(TEXT_DATA_TYPE)
                        .setStream(sharedFileUri)
                        .setChooserTitle(R.string.share_file_chooser_title)
                        .createChooserIntent()
                        .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(share);

            }
        }

        private String getFileName() {
            return Uri.parse(mRequestEntity.getUrl()).getLastPathSegment();
        }

        private void grandReadPermissions(Intent intent, Uri sharedFileUri) {
            final PackageManager packageManager = getActivity().getPackageManager();
            final List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolvedIntentInfo : activities) {
                final String packageName = resolvedIntentInfo.activityInfo.packageName;
                getActivity().grantUriPermission(packageName, sharedFileUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
        }
    }
}
