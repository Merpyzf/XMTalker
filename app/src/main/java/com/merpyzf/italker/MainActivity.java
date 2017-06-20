package com.merpyzf.italker;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.merpyzf.italker.base.BaseActivity;
import com.merpyzf.italker.widget.recycler.RecyclerAdapter;

import butterknife.BindView;


public class MainActivity extends BaseActivity {
    @BindView(R.id.tv_test)
    TextView tv_test;

    @Override
    protected int getContentLayoutId() {
        Log.i("wk","执行了");
        return R.layout.activity_main;
    }

    @Override
    protected void initWidget() {

        tv_test.setText("Hello Android");
        tv_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("wk","tag_recycler_holder's id == >"+R.id.tag_recycler_holder);

            }
        });


        new RecyclerAdapter<String>() {
            @Override
            public RecyclerAdapter<String>.MyViewHolder<String> onCreateMyViewHolder(View root, int viewType) {

                return null;
            }

            @Override
            protected int getItemViewType(String data, int position) {

                return 0;
            }

            @Override
            public void updata(String data, RecyclerAdapter.MyViewHolder viewHolder) {

            }
        };

    }



}
