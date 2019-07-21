package com.zoomx.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zoomx.example.model.User;
import com.zoomx.example.retrofit.ApiService;
import com.zoomx.example.retrofit.NetworkManager;
import com.zoomx.zoomx.config.ZoomX;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startUsersService();
    }

    private void startUsersService() {
        NetworkManager networkManager = new NetworkManager(getApplicationContext());
        ApiService service = networkManager.service();
        service.getUsers(0, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .repeatWhen((Function<Observable<?>, ObservableSource<?>>) objectObservable -> objectObservable.delay(10, TimeUnit.SECONDS))
                .subscribe(new Observer<List<User>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<User> value) {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
        ZoomX.hideHead();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ZoomX.showMenu();
    }
}
