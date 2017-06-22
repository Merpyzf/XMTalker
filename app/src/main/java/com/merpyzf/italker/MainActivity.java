package com.merpyzf.italker;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.merpyzf.italker.base.BaseActivity;

import net.qiujuer.genius.ui.widget.FloatActionButton;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;




public class MainActivity extends BaseActivity {
    @BindView(R.id.appbar)
    AppBarLayout mLyAppBar;

    //toolbar顶部标题栏
    @BindView(R.id.tv_title)
    TextView tv_title;
    //fragment容器
    @BindView(R.id.fl_container)
    FrameLayout fl_container;
    //头像
    @BindView(R.id.portraitView)
    CircleImageView portraitView;

    @BindView(R.id.fab_add)
    FloatActionButton fab_add;
    //底部导航菜单
    @BindView(R.id.navigationView)
    BottomNavigationView navigationView;




    @Override
    protected int getContentLayoutId() {
        Log.i("wk","执行了");

        return R.layout.activity_main;
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        Glide.with(this)
                .load(R.drawable.ic_app_bar_bg)
                .centerCrop()
                .into(new ViewTarget<View,GlideDrawable>(mLyAppBar) {
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
}
