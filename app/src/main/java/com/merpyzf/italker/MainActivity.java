package com.merpyzf.italker;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
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
import com.merpyzf.italker.helper.NavHelper;

import net.qiujuer.genius.ui.Ui;
import net.qiujuer.genius.ui.widget.FloatActionButton;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener
,NavHelper.onTabChangedlistener<Integer>{
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

    private NavHelper<Integer> mNavHelper;


    @Override
    protected int getContentLayoutId() {
        Log.i("wk", "执行了");

        return R.layout.activity_main;
    }

    @Override
    protected void initWidget() {
        super.initWidget();


        
        mNavHelper = new NavHelper<>(this,R.id.fl_container,getSupportFragmentManager(),this);

        mNavHelper.addTab(R.id.action_home,new NavHelper.Tab<Integer>(R.id.action_home,ActiveFragment.class,R.string.title_home));
        mNavHelper.addTab(R.id.action_contact,new NavHelper.Tab<Integer>(R.id.action_contact,ContactFragment.class,R.string.title_contact));
        mNavHelper.addTab(R.id.action_group,new NavHelper.Tab<Integer>(R.id.action_group,GroupFragment.class,R.string.title_group));


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

        //手动触发BottomNavigation的触摸事件,用于首次进入时能够进行填充Fragmnet
        Menu menu = mBottomNavigation.getMenu();
        menu.performIdentifierAction(R.id.action_home,0);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        return mNavHelper.performMenuClick(item.getItemId());
    }

    @Override
    public void onTabChanged(NavHelper.Tab<Integer> oldTab, NavHelper.Tab<Integer> newTab) {

        Log.i("wk","oldTab==> "+oldTab.fragment+"newTab==> "+newTab);

        mTvtitle.setText(newTab.extra);

        int translateY = 0;
        int rotate = 0;

        if(newTab.extra.equals(R.string.title_home)){

            Log.i("wk","首页");
            translateY = (int) Ui.dipToPx(getResources(),76f);

        }else {

            if(newTab.extra.equals(R.string.title_group)){

                mFabAdd.setImageResource(R.drawable.ic_group_add);
                rotate = -360;

            }else {

                mFabAdd.setImageResource(R.drawable.ic_contact_add);
                rotate = 360;
            }

        }

        mFabAdd.animate()
                .rotation(rotate)
                .translationY(translateY)
                .setDuration(200)
                .setInterpolator(new AnticipateInterpolator(2))
                .start();


    }
}
