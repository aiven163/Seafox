package com.aiven.meizhi.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.aiven.meizhi.R;
import com.aiven.meizhi.event.ImgGetEvent;
import com.aiven.meizhi.model.ImgBase;
import com.aiven.meizhi.ui.adapter.MeiZAdapter;
import com.aiven.seafox.controller.Mediator;
import com.aiven.seafox.controller.cmd.Cmd;
import com.aiven.seafox.controller.event.CommonEvent;
import com.aiven.seafox.controller.intef.ELayout;
import com.aiven.seafox.controller.intef.EWidget;
import com.aiven.seafox.controller.log.LogConfig;
import com.aiven.seafox.model.http.Response;
import com.aiven.seafox.view.BaseActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

@ELayout(Layout = R.layout.activity_main)
public class MainActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, MeiZAdapter.OnTapClickListener {

    private static final int TASK_ID = 100;

    @EWidget(id = R.id.tmpIv)
    private ImageView mTmpIv;

    @EWidget(id = R.id.appBarLayout)
    private AppBarLayout mAppBar;

    @EWidget(id = R.id.toolBar)
    private Toolbar mToolBar;

    @EWidget(id = R.id.swip)
    private SwipeRefreshLayout mRefreshView;

    @EWidget(id = R.id.recyView)
    private RecyclerView mRecyclerView;

    private ImgGetEvent event;
    private List<ImgBase> dataList;
    private MeiZAdapter mAdapter;

    private boolean mIsFirstTimeTouchBottom = true;

    private int mCurrentType = 1;
    private int mCurrentIndex = 1;
    private boolean noMore = false;

    @Override
    public void initView(View view, Bundle bundle) {
        setSupportActionBar(mToolBar);
        LogConfig.Debug = true;
        if (Build.VERSION.SDK_INT >= 21) {
            mAppBar.setElevation(10.6f);
        }
        initUI();
    }

    @Override
    public void initData(Bundle bundle) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                requestData(mCurrentType, mCurrentIndex);
            }
        }, 100);
    }


    private void initUI() {
        mRefreshView.setColorSchemeColors(
                getResources().getColor(R.color.md_red_100),
                getResources().getColor(R.color.md_red_300),
                getResources().getColor(R.color.md_red_500),
                getResources().getColor(R.color.md_red_800)
        );
        mRefreshView.setOnRefreshListener(this);
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(
                2, StaggeredGridLayoutManager.VERTICAL
        );
        mRecyclerView.setLayoutManager(layoutManager);
        dataList = new ArrayList<>();
        mAdapter = new MeiZAdapter(dataList, this, this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (!mRefreshView.isRefreshing() && layoutManager.findLastCompletelyVisibleItemPositions(
                        new int[2]
                )[1] >= mAdapter.getItemCount() - 2) {
                    if (noMore) {
                        return;
                    }
                    if (!mIsFirstTimeTouchBottom) {
                        mRefreshView.setRefreshing(true);
                        requestData(mCurrentType, mCurrentIndex);
                    } else {
                        mIsFirstTimeTouchBottom = false;
                    }
                }
            }
        });
    }

    @Override
    public int[] registReceiveCmdIds() {
        return new int[0];
    }

    @Override
    public void onHandCmd(int cmdId, Object o) {
        if(cmdId==1001){
            showToast(o.toString());
        }
    }

    @Override
    public void eventHandle(int i, Object o) {
        if (i == TASK_ID) {
            Response response = (Response) o;
            List<ImgBase> tmpList = (List<ImgBase>) response.getDataObj();
            if (response.getPageInfo().currentPageIndex == 1) {
                dataList.clear();
            }
            dataList.addAll(tmpList);
            mAdapter.update(dataList);
            mRefreshView.setRefreshing(false);
            if (tmpList.size() == 0) {
                noMore = true;
                showToast("全部加载完毕");
            }
            mCurrentIndex = response.getPageInfo().currentPageIndex + 1;
        }
    }

    @Override
    public void eventError(int i, int i1, String s) {
        if (i == TASK_ID) {
            mRefreshView.setRefreshing(false);
            showToast("请求失败");
        }
    }

    @Override
    public void onRefresh() {
        mCurrentIndex = 1;
        noMore = false;
        requestData(mCurrentType, mCurrentIndex);
    }

    private void requestData(final int type, final int pageIndex) {
        mRefreshView.setRefreshing(true);
        event = new ImgGetEvent(TASK_ID, panelName, type, pageIndex);
        event.excute();
    }

    @Override
    public void onClick(final ImgBase base, final View view) {
        Picasso.with(MainActivity.this).load(base.getNomalImgUrl()).into(
                mTmpIv, new Callback() {
                    @Override
                    public void onSuccess() {
                        Intent i = new Intent(MainActivity.this, ImgShowAct.class);
                        Bundle bd = new Bundle();
                        bd.putSerializable("data", base);
                        i.putExtras(bd);
                        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                MainActivity.this, view, ImgShowAct.TRANSIT_PIC
                        );
                        ActivityCompat.startActivity(
                                MainActivity.this, i, optionsCompat.toBundle()
                        );
                    }

                    @Override
                    public void onError() {
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int type = mCurrentType;
        switch (item.getItemId()) {
            case R.id.item_meinv://美女
                mCurrentType = 3;
                break;
            case R.id.item_huahui://花卉
                mCurrentType = 4;
                break;
            case R.id.item_fenjing://风景
                mCurrentType = 2;
                break;
            case R.id.item_meishi://美食
                mCurrentType = 1546;
                break;
        }
        if (type != mCurrentType) {
            mRecyclerView.smoothScrollToPosition(0);
            mCurrentIndex = 1;
            requestData(mCurrentType, mCurrentIndex);
        }
        return true;
    }

}
