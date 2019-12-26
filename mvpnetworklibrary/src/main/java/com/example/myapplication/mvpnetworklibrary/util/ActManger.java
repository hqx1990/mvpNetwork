package com.example.myapplication.mvpnetworklibrary.util;

import android.app.Activity;

import java.util.Stack;

/**
 * activity管理类
 */
public class ActManger {
    /**
     * 接收activity的Stack
     */
    public Stack<Activity> activityStack = null;

    public ActManger() {

    }

    public boolean isEmpty() {
        if (null == activityStack) {
            return true;
        } else {
            return activityStack.isEmpty();
        }
    }

    /**
     * 将activity推入栈内
     *
     * @param activity
     */
    public void pushActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * 将activity移出栈
     *
     * @param activity
     */
    public void popActivity(Activity activity) {
        if (null != activity && null != activityStack) {
            activityStack.remove(activity);
        }
    }

    /**
     * 获取指定的Activity；
     */
    public Activity getActivityByCls(Class<? extends Activity> cls) {
        if (null != activityStack) {
            int size = activityStack.size();
            for (int i = 0; i < size; i++) {
                if (activityStack.get(i).getClass().equals(cls)) {
                    return activityStack.get(i);
                }
            }
        }
        return null;
    }

    /**
     * 结束指定activity
     *
     * @param activity
     */
    public void endActivity(Activity activity) {
        if (null != activity) {
            activity.finish();
            if (null != activityStack) activityStack.remove(activity);
            activity = null;
        }
    }

    /**
     * 获得当前的activity(即最上层)
     *
     * @return
     */
    public Activity currentActivity() {
        Activity activity = null;
        if (null != activityStack && !activityStack.empty())
            activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 从栈弹出当前栈内，cls之上的所有Activity
     *
     * @param cls
     */
    public void popAllActivityExceptOne(Class<? extends Activity> cls) {
        while (true) {
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            if (activity.getClass().equals(cls)) {
                break;
            }
            popActivity(activity);
        }
    }

    /**
     * 结束除cls之外的所有activity；清空除cls之外栈内的其他Activity
     *
     * @param cls
     */
    public void finishAllActivityExceptOne(Class<? extends Activity> cls) {
        if (null != activityStack) {
            for (int i = 0; i < activityStack.size(); ) {
                Activity activity = currentActivity();
                if (!activity.getClass().equals(cls)) {
                    endActivity(activity);
                } else {
                    i++;
                }
            }
        }
    }

    /**
     * 结束所有activity
     */
    public void finishAllActivity() {
        while (null != activityStack && !activityStack.empty()) {
            Activity activity = currentActivity();
            endActivity(activity);
        }
    }

    /**
     * 从栈弹出当前栈内，cls之上的所有Activity 并关闭
     *关闭某一个页面后打开的所有页面
     * @param cls
     */
    public void endAllActivityExceptOne(Class<? extends Activity> cls) {
        while (true) {
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            if (activity.getClass().equals(cls)) {
                break;
            }
            endActivity(activity);
        }
    }
}