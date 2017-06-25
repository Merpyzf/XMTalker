package com.merpyzf.italker;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.merpyzf.italker.base.BaseActivity;
import com.merpyzf.italker.fragment.main.ActiveFragment;
import com.merpyzf.italker.fragment.main.ContactFragment;
import com.merpyzf.italker.fragment.main.GroupFragment;

import net.qiujuer.genius.ui.widget.FloatActionButton;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.appbar)
    AppBarLayout mLyAppBar;

    //toolbar顶部标题栏
    @BindView(R.id.tv_title)
    TextView mTvtitle;
    //fragment容器
    @BindView(R.id.fl_container)
    FrameLayout fl_container;
    //头像
    @BindView(R.id.portraitView)
    CircleImageView mortraitView;

    @BindView(R.id.fab_add)
    FloatActionButton mFabAdd;
    //底部导航菜单
    @BindView(R.id.navigationView)
    BottomNavigationView mBottomNavigation;
    private FragmentTransaction transaction;

    @Override
    protected int getContentLayoutId() {
        Log.i("wk", "执行了");

        return R.layout.activity_main;
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        mBottomNavigation.setOnNavigationItemSelectedListener(this);
        Glide.with(this)
                .load(R.drawable.ic_app_bar_bg)
                .centerCrop()
                .into(new ViewTarget<View, GlideDrawable>(mLyAppBar) {

                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {

                        this.view.setBackground(resource.getCurrent());

                    }
                });


    }

    @Override
    protected void initData() {
        super.initData();
    }

    private Boolean isFirst = true;

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();


        int size = getSupportFragmentManager().getFragments().size();

        Log.i("wk", "fragment的数量" + size);

        switch (item.getItemId()) {

            case R.id.action_home:

                mTvtitle.setText(getResources().getText(R.string.title_home));
                ActiveFragment activeFragment = null;
                if (isFirst) {

                    activeFragment = new ActiveFragment();

                    transaction.add(R.id.fl_container, activeFragment);

                    isFirst = false;

                }else {

                    transaction.replace(R.id.fl_container,activeFragment);

                }


                break;

            case R.id.action_group:
                mTvtitle.setText(getResources().getText(R.string.title_group));
                GroupFragment groupFragment = new GroupFragment();

                transaction.replace(R.id.fl_container, new GroupFragment());


                break;
            case R.id.action_contact:

                mTvtitle.setText(getResources().getText(R.string.title_contact));

                transaction.replace(R.id.fl_container, new ContactFragment());
                break;
        }


        transaction.commit();
        //处理底部导航栏的点击事件,不然没有动画效果
        return true;
    }
}
