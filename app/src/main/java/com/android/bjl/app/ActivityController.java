package com.android.bjl.app;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * 王京杰
 * 日期：2017/12/28
 * 邮箱：wangjj@bjrrtx.com
 */

public class ActivityController {
    public static List<Activity> activityList = new ArrayList<Activity>();

    public static void addActivity(Activity activity) {
        if (!activityList.contains(activity))
            activityList.add(activity);
    }

    public static void removeActivity(Activity activity){
        if (activity!=null&&activityList.contains(activity  )){
            activityList.remove(activity);
            activity.finish();
        }
    }

    public static void finishAll(){
        if (activityList.size()>0){
            for (Activity activity:activityList){
                activity.finish();
            }
            activityList.clear();
        }
    }
}
