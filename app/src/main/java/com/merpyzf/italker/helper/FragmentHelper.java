package com.merpyzf.italker.helper;

/**
 * Created by 春水碧于天 on 2017/6/25.
 */

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import java.util.HashMap;

/**
 * Fragment调度的工具类
 */
public class FragmentHelper {

    private  HashMap<Integer,Fragment> mFragmentsMap = new HashMap<>();
    private  Boolean isFirst;
    private FragmentManager mFragmentManager;
    private FragmentActivity mContext;

    public FragmentHelper(FragmentActivity context) {
        this.mContext = context;
        mFragmentManager = context.getSupportFragmentManager();

    }

    public  void switchFragment(Activity activity, int container, int action, Fragment fragment){

        //每添加依次fragment就将其保存

        if(mFragmentManager.getFragments().size()==0){

            //第一次添加Fragment 采用add的方式进行添加


        }else {

            //如果不是第一次添加则根据action 进行判断 ,


        }








    }







}
