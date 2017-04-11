package com.example.archimvp;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.example.archimvp.di.component.AppComponent;
import com.example.archimvp.di.component.DaggerAppComponent;
import com.example.archimvp.di.module.AppModule;
import com.example.archimvp.di.module.HttpModule;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by LeoPoldCrossing on 2017/3/13.
 */

public class ArchiApplication extends Application {
    public static final String TAG = "ArchiApplication";

    private static ArchiApplication mInstance;
    public static AppComponent appComponent;
    private static List<Activity> mActivities = Collections.synchronizedList(new LinkedList<Activity>());
    private static int activeCount;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        registerActivityListener();
    }

    public static ArchiApplication getInstace() {
        return mInstance;
    }

    /**
     * get current activity
     */
    public static Activity currentActivity() {
        if (mActivities == null || mActivities.isEmpty()) {
            return null;
        }
        return mActivities.get(mActivities.size() - 1);
    }

    /**
     * 结束当前Activity
     */
    public static void finishCurrentActivity() {
        if (mActivities == null || mActivities.isEmpty()) {
            return;
        }
        Activity activity = mActivities.get(mActivities.size() - 1);
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    private static void finishActivity(Activity activity) {
        if (mActivities == null || mActivities.isEmpty()) {
            return;
        }
        if (activity != null) {
            mActivities.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public static void finishActivity(Class<?> cls) {
        if (mActivities == null || mActivities.isEmpty()) {
            return;
        }
        for (Activity activity : mActivities) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 按照指定类名找到activity
     *
     * @param cls
     * @return
     */
    public static Activity findActivity(Class<?> cls) {
        Activity targetActivity = null;
        if (mActivities != null) {
            for (Activity activity : mActivities) {
                if (activity.getClass().equals(cls)) {
                    targetActivity = activity;
                    break;
                }
            }
        }
        return targetActivity;
    }

    /**
     * 获取当前最顶部activity的实例
     */
    public Activity getTopActivity() {
        Activity mBaseActivity = null;
        synchronized (mActivities) {
            final int size = mActivities.size() - 1;
            if (size < 0) {
                return null;
            }
            mBaseActivity = mActivities.get(size);
        }
        return mBaseActivity;
    }

    /**
     * 获取当前最顶部的acitivity 名字
     */
    public String getTopActivityName() {
        return getTopActivity().getClass().getName();
    }


    public static void finishAllActivity() {
        if (mActivities == null) {
            return;
        }
        for (Activity activity : mActivities) {
            activity.finish();
        }
        mActivities.clear();
    }

    /**
     * 退出应用程序
     */
    public void appExit() {
        try {
            finishAllActivity();
        } catch (Exception e) {
            Log.e(getClass().toString(), e.getMessage(), e);
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    public boolean isBackground() {
        if (activeCount == 0) {
            // 进入后台
            Log.e(TAG, "enter background");
        } else if (activeCount == 1) {
            // 进入前台
            Log.e(TAG, "enter foreground");
        }
        return activeCount == 0;
    }


    private void registerActivityListener() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                /*
                * 监听到 Activity 创建事件，将Activity 加入到 list
                * */
                pushActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
                activeCount++;
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                activeCount--;
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                if (null == mActivities && mActivities.size() == 0) {
                    return;
                }
                if (mActivities.contains(activity)) {
                    popActivity(activity);
                }
            }
        });
    }

    private void popActivity(Activity activity) {
        mActivities.remove(activity);
        Log.e(TAG, "mActivities size" + mActivities.size());
    }

    private void pushActivity(Activity activity) {
        mActivities.add(activity);
        Log.e(TAG, "mActivities size" + mActivities.size());
    }

    public static AppComponent getAppComponent(){
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(mInstance))
                    .httpModule(new HttpModule())
                    .build();
        }
        return appComponent;
    }

}
