package com.merpyzf.italker.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wangke on 17-6-19.
 */

public abstract class BaseFragment extends Fragment {

    protected View mRoot = null; //缓存Fragment中的view

    /**
     * 当Fragment添加到Activity的时候最先调用此方法
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //获取Fragment之间传递过来的参数
        initArgs(getArguments());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            //Fragment之间切换时每次都会调用onCreateView方法，导致每次Fragment的布局都重绘，无法保持Fragment原有状态
        if(mRoot == null) {

            View root = inflater.inflate(getContentLayoutId(), container, false);
            mRoot = root;
            initWidget(root);

        }else {

            if(mRoot.getParent()!=null){

                //把当前的布局从父View中进行移除,否则会发生这个rootView已经有了一个父View的错误
                ((ViewGroup)mRoot.getParent()).removeAllViews();

            }
        }

        return mRoot;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();

    }

    protected abstract int getContentLayoutId();

    /**
     * 初始化控件
     * @param view
     */
    protected void initWidget(View view){

        //进行ButterKnife的绑定


    }

    /**
     * 初始化数据
     */
    protected  abstract void initData();

    /**
     * 初始化参数(获取传递过来的参数)
     * @param bundle
     */
    protected void initArgs(Bundle bundle){

    }


    /**
     * fragment处理返回键
     *
     * @return true : 表示fragment自己来处理当前的返回事件
     *         false : 表示将frgment返回的逻辑交给activity处理
     */
    protected boolean onBackPressed(){

        return false;

    }



}
