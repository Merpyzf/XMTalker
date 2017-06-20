package com.merpyzf.italker.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wangke on 17-6-19.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindows();
        Log.i("wk", "执行了==》");
        if (initArgs(getIntent().getExtras())) {
            //ButterKnife绑定Activity
            setContentView(getContentLayoutId());
            mUnbinder = ButterKnife.bind(this);
            Log.i("wk", "执行了");
            initWidget();
            initData();

        } else {

            finish();
        }

    }




    /**
     * 初始化窗体的数据,在界面未初始化之前调用
     */
    protected void initWindows() {

    }

    /**
     * 处理传递过来的参数
     *
     * @param bundle
     * @return
     */
    protected boolean initArgs(Bundle bundle) {

        return true;
    }


    /**
     * 获取布局id
     *
     * @return 布局id
     */
    protected abstract int getContentLayoutId();

    /**
     * 初始化控件
     */
    protected void initWidget() {


    }

    //初始化数据
    protected void initData() {


    }


    @Override
    public boolean onSupportNavigateUp() {
        //当点击界面导航栏中的返回按钮时结束掉当前页面
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {

        @SuppressLint("RestrictedApi")
        List<Fragment> fragments = getSupportFragmentManager().getFragments();

        if (fragments != null && fragments.size() > 0) {

            for (int i = 0; i < fragments.size(); i++) {

                if (fragments.get(i) instanceof BaseFragment) {
                    //onBackPressed方法返回true表示fragment自行处理事件
                    if (((BaseFragment) fragments.get(i)).onBackPressed()) {

                        return;

                    }

                }

            }

        }
        super.onBackPressed();
        finish();

    }


    @Override
    protected void onDestroy() {
        //解除绑定
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        super.onDestroy();

    }
}
