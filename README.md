
//仿照 listview addHeaderview addFooterView方法，给recyclerView 添加相应的方法  

//通过 查看listview addHeaderview 源码发现

    public void addHeaderView(View v, Object data, boolean isSelectable) {
    if (v.getParent() != null && v.getParent() != this) {
        if (Log.isLoggable(TAG, Log.WARN)) {
            Log.w(TAG, "The specified child already has a parent. "
                       + "You must call removeView() on the child's parent first.");
        }
    }
    final FixedViewInfo info = new FixedViewInfo();
    info.view = v;
    info.data = data;
    info.isSelectable = isSelectable;
    mHeaderViewInfos.add(info);
    mAreAllItemsSelectable &= isSelectable;
   
    // Wrap the adapter if it wasn't already wrapped.
    //mAdapter  其实就是通过 listView.setAdapter(mAdapter); 
    //然后 调用此方法  （adapter ==setAdapter(mAdapter); 中的 mAdapter）
    //wrapHeaderListAdapterInternal(mHeaderViewInfos, mFooterViewInfos, adapter); 负的值
    if (mAdapter != null) {
        if (!(mAdapter instanceof HeaderViewListAdapter)) {
        //重点看这个方法
            wrapHeaderListAdapterInternal();
        }

        // In the case of re-adding a header view, or adding one later on,
        // we need to notify the observer.
        if (mDataSetObserver != null) {
            mDataSetObserver.onChanged();
        }
    }
}

    #在查看下 wrapHeaderListAdapterInternal();源代码
    #就不难发现  把传进来的adpater  利用HeaderViewListAdapter 重新封装一下，生成新的mAdapter
    protected HeaderViewListAdapter wrapHeaderListAdapterInternal(
        ArrayList<ListView.FixedViewInfo> headerViewInfos,
        ArrayList<ListView.FixedViewInfo> footerViewInfos,
        ListAdapter adapter) {
     return new HeaderViewListAdapter(headerViewInfos, footerViewInfos, adapter);
     }


     #此时再次查看HeaderViewListAdapter的源码就会发现很眼熟的方法
     public View getView(int position, View convertView, ViewGroup parent) {}
    //主要通过该方法来区别
    public int getItemViewType(int position) {
    int numHeaders = getHeadersCount();
    if (mAdapter != null && position >= numHeaders) {
    //adjPositions可以看做是  listview 第一个item的位置
        int adjPosition = position - numHeaders;
        int adapterCount = mAdapter.getCount();
        if (adjPosition < adapterCount) {
        //如果是listview的item直接调用原来的方法即可
            return mAdapter.getItemViewType(adjPosition);
        }
    }
    //如果不是原来的就直接返回  头部 或者尾部的布局了
    return AdapterView.ITEM_VIEW_TYPE_HEADER_OR_FOOTER;
    }
 
 //到此就可以根据源码来仿照写个 recyclerView 的 addHeaderview addFooterView
 
 
 ![image](https://github.com/shijinjom/CustomRecyclerView/blob/master/demo.jpg)
