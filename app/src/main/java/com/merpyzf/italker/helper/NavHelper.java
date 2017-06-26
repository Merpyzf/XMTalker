package com.merpyzf.italker.helper;

/**
 * Created by 春水碧于天 on 2017/6/25.
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.SparseArray;

import com.merpyzf.italker.R;

/**
 * Fragment调度的工具类
 */
public class NavHelper<T> {

    private SparseArray<Tab> tabs = new SparseArray<>();
    private Context context;
    private int container;
    private final FragmentManager fragmentManager;
    private Tab<T> currentShowTab = null;
    private onTabChangedlistener listener;


    public NavHelper(Context context, int container, FragmentManager fragmentManager, onTabChangedlistener listener) {
        this.context = context;
        this.container = container;
        this.fragmentManager = fragmentManager;
        this.listener = listener;

    }


    public NavHelper addTab(int menuId, Tab<T> tab) {

        tabs.put(menuId, tab);

        return this;
    }

    /**
     * 将当前显示的Tab返回
     *
     * @return
     */
    public Tab<T> getCurrentShowTab() {

        return currentShowTab;
    }


    public boolean performMenuClick(int menus) {

        //首次进入页面的时候，当前页面默认展示的界面为主页,手动设置currentShowTab的值
        if (currentShowTab == null) {

            currentShowTab = tabs.get(R.id.action_home);
        }

        Tab<T> tab = tabs.get(menus);

        if (tab != null) {

            doSelect(tab);

            return true;
        }

        return false;
    }

    private void doSelect(Tab<T> tab) {

        Tab<T> oldTab = null;

        if (currentShowTab != null) {

            oldTab = currentShowTab;

        }

        if (tab == oldTab) {


            Log.i("wk", "两次点击了同一个tab");

            refresh(tab);

        }


        doRealSelectTab(oldTab, tab);


        currentShowTab = tab;

    }

    /**
     * 两次触摸同一个tab时触发此方法
     *
     * @param tab
     */
    private void refresh(Tab<T> tab) {

        // TODO: 2017/6/26  界面刷新的操作

    }


    /**
     * 进行真正的Fragment调度的处理
     *
     * @param oldTab 上一个tab
     * @param newTab 新点击的tab
     */
    private void doRealSelectTab(Tab<T> oldTab, Tab<T> newTab) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (oldTab != null) {

            if (oldTab.fragment != null) {

                transaction.detach(oldTab.fragment);

            }
        }

        if (newTab != null) {

            if (newTab.fragment != null) {

                transaction.attach(newTab.fragment);

            } else {


                Fragment fragment = Fragment.instantiate(context, newTab.clazz.getName());
                transaction.add(container, fragment);
                newTab.fragment = fragment;

            }


        }
        //tab切换时的回调用监听
        listener.onTabChanged(oldTab, newTab);
        transaction.commit();


    }


    public static class Tab<T> {

        private Class<?> clazz;
        private int menuId;
        public  T extra;

        //用来保存创建出来的fragment
        public Fragment fragment;

        public Tab(int menuId, Class<?> clazz, T extra) {
            this.menuId = menuId;
            this.clazz = clazz;
            this.extra = extra;

        }
    }

    public interface onTabChangedlistener<T> {

        void onTabChanged(Tab<T> oldTab, Tab<T> newTab);

    }

}
