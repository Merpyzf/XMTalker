package com.merpyzf.italker.widget.recycler;

/**
 * Created by wangke on 17-6-19.
 */

public interface AdapterCallBack<T> {
    /**
     * 更新操作（目前个人理解给控件设置值）
     * @param data
     * @param viewHolder
     */
    void updata(T data, RecyclerAdapter.MyViewHolder viewHolder);

}
