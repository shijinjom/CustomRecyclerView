package com.example.recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    CustomRecyclerView recyclerView;
    int headindex = 0;
    int footindex = 0;
    int datas = 0;

    View footview;//测试要删除的角标
    View headview;//测试要删除的头部
    BaseRecyclerAdapter recyclerAdapter;
    List<String> data;

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bt1:
                recyclerView.removeHeaderView(headview);
                break;

            case R.id.bt2:
                TextView textView = new TextView(this);
                textView.setText("第一个头部" + headindex++);
                recyclerView.addHeaderView(textView);

                break;

            case R.id.bt3:
                TextView textView1 = new TextView(this);
                textView1.setText("第一个角标" + footindex++);
                recyclerView.addFooterView(textView1);

                break;

            case R.id.bt4:

                recyclerView.removeFooterView(footview);
                break;


            case R.id.bt5:
                data.addAll(getdata());
                recyclerAdapter.notifyDataSetChanged();

                break;

        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.recyclerview);


        headview = LinearLayout.inflate(this, R.layout.head_layout, null);
        recyclerView.addHeaderView(headview);
        View headview1 = LinearLayout.inflate(this, R.layout.head_layout, null);
//        recyclerView.addHeaderView(headview1);


        footview = LinearLayout.inflate(this, R.layout.foot_layout, null);
        recyclerView.addFooterView(footview);
        View footview1 = LinearLayout.inflate(this, R.layout.foot_layout, null);
//        recyclerView.addFooterView(footview1);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        linearLayoutManager.setItemPrefetchEnabled(false);


        data = getdata();
        recyclerAdapter = new BaseRecyclerAdapter<String>(this, data, R.layout.item) {
            @Override
            public void getView(RecyclerView.ViewHolder holder, int position, String str) {
                TextView tv = holder.itemView.findViewById(R.id.tv);
                tv.setText(str + "");

            }
        };
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerAdapter);
    }

    public List<String> getdata() {
        List<String> list = new ArrayList<>();

        list.add("item" + datas++);
        list.add("item" + datas++);
        list.add("item" + datas++);
        list.add("item" + datas++);

        return list;
    }

}
