package com.example.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by shijin on 2018/5/4.
 */

public class HeaderViewRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    SparseArray<View> headerView;
    SparseArray<View> footerView;

    private RecyclerView.Adapter mAdapter;
    public boolean isaddHeadview;

    public HeaderViewRecyclerAdapter(RecyclerView.Adapter mAdapter, SparseArray<View> listheaderView, SparseArray<View> listfooterView) {
        this.mAdapter = mAdapter;
        headerView = listheaderView;
        footerView = listfooterView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (headerView.indexOfKey(viewType) > -1) {//说明是头部
            return new RecyclerView.ViewHolder(headerView.get(viewType)) {
            };

        } else if (footerView.indexOfKey(viewType) > -1) {//说明是底部
            return new RecyclerView.ViewHolder(footerView.get(viewType)) {
            };
        }

        return mAdapter.onCreateViewHolder(parent, viewType);


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int numHeaders = headerView.size();
        if (numHeaders > position) {
            return;

        }
        final int adjPosition = position - numHeaders;
        int adapterCOunt = 0;

        if (mAdapter != null) {
            adapterCOunt = mAdapter.getItemCount();
            if (adapterCOunt > adjPosition) {
                mAdapter.onBindViewHolder(holder, position - headerView.size());
            }
        }
        //底布局不需要管理
    }


    @Override
    public int getItemViewType(int position) {

        int numHeaders = headerView.size();
        if (numHeaders > position) {
            return headerView.keyAt(position);
        }

        final int adjPosition = position - numHeaders;
        int adapterCOunt = 0;
        if (mAdapter != null) {
            adapterCOunt = mAdapter.getItemCount();
            if (adapterCOunt > adjPosition) {
                //最重要的这句，要调用本身的getItemViewType 方法，以防扰乱item布局，不这样写会有很大的坑
                return mAdapter.getItemViewType(adjPosition);
            }
        }

        return footerView.keyAt(adjPosition - adapterCOunt);

    }

    @Override
    public int getItemCount() {
        if (mAdapter != null) {
            return getHeaderViewCount() + getFooterViewCount() + mAdapter.getItemCount();
        } else {
            return getHeaderViewCount() + getFooterViewCount();
        }
    }


    private int getHeaderViewCount() {
        return headerView.size();
    }

    private int getFooterViewCount() {
        return footerView.size();
    }


}
