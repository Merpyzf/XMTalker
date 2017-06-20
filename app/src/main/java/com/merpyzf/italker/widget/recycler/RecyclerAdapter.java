package com.merpyzf.italker.widget.recycler;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.merpyzf.italker.R;

import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wangke on 17-6-19.
 */

public abstract class RecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerAdapter<T>.MyViewHolder<T>>
        implements View.OnClickListener,
        View.OnLongClickListener
        , AdapterCallBack<T> {


    private final List<T> mListDatas = null;
    private AdapterListener<T> mAdapterListener;


    /**
     * 创建一个ViewHolder
     *
     * @param parent   RecyclerView
     * @param viewType 布局类型
     * @return MyVIewHolder
     */
    @Override
    public MyViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View root = inflater.inflate(viewType, parent, false);
        MyViewHolder<T> myViewHolder = onCreateViewHolder(parent, viewType);
        //给viewHolder设置事件点击
        root.setTag(R.id.tag_recycler_holder, myViewHolder);
        root.setOnClickListener(this);
        root.setOnLongClickListener(this);
        //ButterKnife注解绑定
        myViewHolder.unbinder = ButterKnife.bind(myViewHolder, root);
        //绑定AdapterCallBack
        myViewHolder.mAdapterCallBack = this;
        return myViewHolder;
    }

    /**
     * 创建一个自己的ViewHolder(子类创建的时候实现此方法)
     *
     * @param root
     * @param viewType
     */
    public abstract MyViewHolder<T> onCreateMyViewHolder(View root, int viewType);

    /**
     * 重写此方法的返回值 ViewType对应我们要加载的布局文件的xml的id
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return getItemViewType(mListDatas.get(position), position);
    }

    /**
     * 返回一个布局的id,用来创建viewHolder(由子类进行重写)
     *
     * @param data
     * @param position
     * @return
     */
    @LayoutRes
    protected abstract int getItemViewType(T data, int position);


    /**
     * ViewHolder数据的绑定
     *
     * @param holder   MyViewHolder
     * @param position 下标
     */

    @Override
    public void onBindViewHolder(MyViewHolder<T> holder, int position) {

        //根据position获取需要绑定的数据
        T data = mListDatas.get(position);
        //触发绑定的方法
        holder.bindData(data);


    }

    @Override
    public int getItemCount() {

        return mListDatas.size();
    }

    /**
     * 向mDataList中添加一个数据并刷新
     *
     * @param data
     */
    public void addData(T data) {

        if (data != null) {
            mListDatas.add(data);

            //优化,只刷新指定的条目数据
            notifyItemInserted(mListDatas.size() - 1);

        }
    }

    /**
     * 添加可变参数
     *
     * @param data
     */
    public void addDatas(T... data) {

        if (data != null && data.length > 0) {

            //起始刷新的位置
            int startPosition = mListDatas.size();
            Collections.addAll(mListDatas, data);
            notifyItemRangeInserted(startPosition, mListDatas.size() - 1);

        }
    }


    /**
     * 添加一个list集合
     *
     * @param dataList
     */
    public void addData(List<T> dataList) {


        if (dataList != null && dataList.size() > 0) {

            int startPosition = mListDatas.size();
            //添加一个集合进去
            mListDatas.addAll(dataList);

            notifyItemRangeInserted(startPosition, mListDatas.size() - 1);

        }

    }

    /**
     * 清空mDataList集合中的内容
     */
    public void clear() {

        if (mListDatas != null && mListDatas.size() > 0) {
            mListDatas.clear();
            notifyDataSetChanged();
        }
    }


    /**
     * 替换已存在的mDataList集合中的内容
     */
    public void replace(List<T> dataList) {

        if (mListDatas != null && mListDatas.size() > 0) {
            mListDatas.clear();

        }

        if (dataList != null && dataList.size() > 0) {
            mListDatas.addAll(dataList);
        }

        notifyDataSetChanged();
    }


    /**
     * 自定义实现的ViewHolder
     *
     * @param <T>
     */
    protected abstract class MyViewHolder<T> extends RecyclerView.ViewHolder {

        //保存当前viewHolder中的data数据
        protected T mData = null;
        private Unbinder unbinder = null;

        //疑惑！！ 什么时候给mAdapterCallBack设置值？？
        private AdapterCallBack mAdapterCallBack = null;


        public MyViewHolder(View itemView) {
            super(itemView);
        }

        //进行数据的设置
        void bindData(T data) {

            this.mData = data;
            onBind(data);

        }

        /**
         * 控件数据的绑定
         *
         * @param data 绑定的数据
         */
        public abstract void onBind(T data);

        public void upData(T data) {


            if (mAdapterCallBack != null) {
                //this表示当前所在的viewHolder
                mAdapterCallBack.updata(data, this);

            }


        }

    }

    public interface AdapterListener<T> {

        void onItemLongClick(T data, RecyclerAdapter.MyViewHolder holder);

        void onItemClick(T data, RecyclerAdapter.MyViewHolder holder);

    }

    //设置按键的回调
    public void setOnAdapterListener(AdapterListener<T> adapterListener) {

        this.mAdapterListener = adapterListener;

    }


    /**
     * 条目根布局的长按事件
     *
     * @param v
     * @return
     */
    @Override
    public boolean onLongClick(View v) {

        MyViewHolder viewHolder = (MyViewHolder) v.getTag(R.id.tag_recycler_holder);

        //获取ViewHolder的坐标
        int position = viewHolder.getAdapterPosition();

        if (mAdapterListener != null) {

            mAdapterListener.onItemLongClick(mListDatas.get(position), viewHolder);

            //事件被处理
            return true;

        }

        return false;
    }

    /**
     * 条目的根部局的点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

        MyViewHolder viewHolder = (MyViewHolder) v.getTag(R.id.tag_recycler_holder);
        //获取ViewHolder的所处下标
        int position = viewHolder.getAdapterPosition();

        if (mAdapterListener != null) {

            mAdapterListener.onItemClick(mListDatas.get(position), viewHolder);
        }


    }


}
