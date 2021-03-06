package com.jameni.jamenilist;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jameni.jamenilistlib.i.ItemChildViewClickListener;
import com.jameni.jamenilistlib.i.ItemClickListener;
import com.jameni.jamenilistlib.i.OnLoadMoreListener;
import com.jameni.jamenilistlib.i.OnRefreshListener;
import com.jameni.jamenilistlib.view.RefreshView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ItemClickListener, OnRefreshListener, OnLoadMoreListener, ItemChildViewClickListener {


    RefreshView lvMain;
    List<TestModel> datalist;

    Myadapter myadapter;

    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_text);

        datalist = new ArrayList<>();
        lvMain = findViewById(R.id.lvMain);
        lvMain.setListBackgroundWhite();
        lvMain.setLinearManager();
        myadapter = new Myadapter();
        lvMain.setAdapter(myadapter);
        lvMain.setDivider();

//        lvMain.setItemClickListener(this);
        lvMain.setItemChildViewClickListener(this);
        lvMain.setOnRefreshListener(this);
        lvMain.setOnLoadMoreListener(this);

        onRefresh();
    }

    @Override
    public void onItemClick(Object itemData, int position) {
        tip(position + "");
    }

    @Override
    public void onLoadMore() {

        index++;
        print("=========onLoadMore=========");
        print("加载次数==" + index);
        if (index < 4) {
            uiHandler.sendEmptyMessageDelayed(2, 500);
        }
    }

    @Override
    public void onRefresh() {

        print("=========onRefresh=========");
        index = 0;
        uiHandler.sendEmptyMessageDelayed(1, 500);

    }

    Handler uiHandler = new Handler() {


        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 1) {
                datalist.clear();
                lvMain.refreshComplete();
//                myadapter.loadMoreEnd(false);
            } else {

            }

            datalist.addAll(getData());
            lvMain.update(datalist);
            lvMain.loadMoreComplete();


            if (index == 3) {
//                lvMain.setLoadMoreEnd();
                lvMain.setOnLoadMoreEnable(false);
            }

        }
    };


    public void print(String str) {
        Log.i("listlib", str);
    }

    public void tip(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }


    private List<TestModel> getData() {
        List<TestModel> list = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            list.add(new TestModel((datalist.size() + i) + ""));
        }
        return list;

    }

    @Override
    public void onItemChildViewClick(int viewId, int position) {
        switch (viewId) {
            case R.id.tv:
                tip("tv==" + position);
                break;
            case R.id.tv2:
                tip("tv2==" + position);
                break;
        }
    }
}
