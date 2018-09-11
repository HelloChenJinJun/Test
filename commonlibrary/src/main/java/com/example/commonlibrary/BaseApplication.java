package com.example.commonlibrary;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.example.commonlibrary.adaptScreen.ScreenAdaptManager;
import com.example.commonlibrary.dagger.component.AppComponent;
import com.example.commonlibrary.dagger.component.DaggerAppComponent;
import com.example.commonlibrary.dagger.module.GlobalConfigModule;
/**
 * Created by COOTEK on 2017/7/28.
 */

public class BaseApplication extends Application {


    private static AppComponent appComponent;
    private static BaseApplication instance;
    private ApplicationDelegate applicationDelegate;

    public static BaseApplication getInstance() {
        return instance;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        applicationDelegate = new ApplicationDelegate(base);
        applicationDelegate.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initDagger();
        initScreenAdapt();
        instance = this;
        applicationDelegate.onCreate(this);

    }

    private void initScreenAdapt() {
        new ScreenAdaptManager.Builder().designedHeight(640)
                .designedWidth(360).build().initData(this);
    }


    private void initDagger() {
        appComponent=DaggerAppComponent.builder().globalConfigModule(new GlobalConfigModule(this))
                .build();

    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        applicationDelegate.onTerminate(this);
    }
}
