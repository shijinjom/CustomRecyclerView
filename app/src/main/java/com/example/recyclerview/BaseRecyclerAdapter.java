package com.example.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/11/28.
 */

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<T> list = null;
    Context context = null;
    int layout;

    public BaseRecyclerAdapter(Context context, List<T> list, int layout) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(layout, viewGroup, false);
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        getView(viewHolder, position, list.get(position));
    }

    public abstract void getView(RecyclerView.ViewHolder holder, int position, T t);

    @Override
    public int getItemCount() {
        return list.size();
    }

}
