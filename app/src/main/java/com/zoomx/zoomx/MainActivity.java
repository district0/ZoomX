package com.zoomx.zoomx;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.zoomx.zoomx.model.User;
import com.zoomx.zoomx.retrofit.ApiService;
import com.zoomx.zoomx.retrofit.NetworkManager;
import com.zoomx.zoomx.ui.requestlist.request.RequestActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ImageView overLayImage;
    String TAG = MainActivity.class.getSimpleName();
    WindowManager wm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startOverlay();
        startUsersService();
    }

    private void startUsersService() {
        NetworkManager networkManager = new NetworkManager();

        ApiService service = networkManager.service();
        Call<List<User>> getUsersCall = service.getUsers(0, 1);
        getUsersCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void startOverlay() {
        overLayImage = new ImageView(this);
        overLayImage.setImageResource(R.drawable.ic_launcher_background);
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                80,
                80,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        //Specify the view position
        params.gravity = Gravity.CENTER | Gravity.RIGHT;        //Initially view will be added to top-left corner
        params.x = 0;
        params.y = 100;

        wm = (WindowManager) getSystemService(WINDOW_SERVICE);

        overLayImage.setOnTouchListener(new View.OnTouchListener() {

            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:


                        //remember the initial position.
                        initialX = params.x;
                        initialY = params.y;


                        //get the touch location
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();

                        return false;

                    case MotionEvent.ACTION_MOVE:
                        //Calculate the X and Y coordinates of the view.
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);


                        //Update the layout with new X & Y coordinate
                        wm.updateViewLayout(overLayImage, params);
                        return false;
                }
                return false;
            }
        });
        overLayImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , RequestActivity.class);
                startActivity(intent);
            }
        });

        // Check if Android M or higher
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            // Show alert dialog to the user saying a separate permission is needed
            // Launch the settings activity if the user prefers
            Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            startActivity(myIntent);
            wm.addView(overLayImage, params);
        }
        else
        {
            wm.addView(overLayImage, params);
        }
    }

    @Override
    protected void onDestroy() {
        if (overLayImage != null && wm != null) {
            wm.removeView(overLayImage);
        }
        super.onDestroy();
    }


}
