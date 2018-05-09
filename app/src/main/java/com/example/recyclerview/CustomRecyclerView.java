package com.example.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by shijin on 2018/5/4.
 */

public class CustomRecyclerView<T> extends RecyclerView {
    private static final String TAG = "CustomRecyclerView";

    private Adapter mAdapter;
    SparseArray<View> headerView = new SparseArray<>();
    SparseArray<View> footerView = new SparseArray<>();

    //一下两个变量为了区别添加不同的headerview  作者footerview
    int indexheadview = 200000;
    int indexfooterview = 300000;


    public CustomRecyclerView(Context context) {
        this(context, null);
    }

    public CustomRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    //定义一个观察者模式  当listitem数据发生改变的时候  会自定回调观察者中相应的方法
    AdapterDataObserver observer = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            super.onItemRangeChanged(positionStart, itemCount, payload);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
        }
    };


    @Override
    public void setAdapter(Adapter adapter) {
        if (headerView.size() > 0 || footerView.size() > 0) {
            mAdapter = new HeaderViewRecyclerAdapter(adapter, headerView, footerView);
//注册观察者
            adapter.registerAdapterDataObserver(observer);
        } else
            mAdapter = adapter;
        super.setAdapter(mAdapter);
    }

    public void addHeaderView(View view) {
//先判断是否已经添加过该View
        if (headerView.indexOfValue(view) != -1) {

            Log.w(TAG, "不可以添加同一个View");

        } else {
            headerView.put(indexheadview++, view);
            if (mAdapter != null) {
                if (mAdapter instanceof HeaderViewRecyclerAdapter) {
//                    mAdapter = new HeaderViewRecyclerAdapter(mAdapter, headerView, footerView);
                    mAdapter.notifyDataSetChanged();
                }
            }
        }


    }


    public void addFooterView(View view) {
        if (footerView.indexOfValue(view) != -1) {
            Log.w(TAG, "不可以添加同一个View");

        } else {
            footerView.put(indexfooterview++, view);
            if (mAdapter != null) {
                if (mAdapter instanceof HeaderViewRecyclerAdapter)
//                    mAdapter = new HeaderViewRecyclerAdapter(mAdapter, headerView, footerView);
                    mAdapter.notifyDataSetChanged();
            }

        }
    }


    public void removeHeaderView(View view) {
        if (view != null) {
            if (headerView.indexOfValue(view) > -1) {
                headerView.removeAt(headerView.indexOfValue(view));
                mAdapter.notifyDataSetChanged();
            }
        } else {
            Log.w(TAG, "要删除的View为NULL");
        }
    }

    public void removeFooterView(View view) {
        if (view != null) {
            if (footerView.indexOfValue(view) > -1) {
                footerView.removeAt(footerView.indexOfValue(view));
                mAdapter.notifyDataSetChanged();
            }
        } else {
            Log.w(TAG, "要删除的View为NULL");
        }
    }
}
