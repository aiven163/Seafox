package com.aiven.meizhi.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.aiven.meizhi.R;
import com.aiven.meizhi.model.ImgBase;
import com.aiven.seafox.controller.intef.ELayout;
import com.aiven.seafox.controller.intef.EWidget;
import com.aiven.seafox.view.BaseActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Author: Aiven
 * Email : aiven163@sina.com
 * Date : 2015/7/23  17:20
 * Desc :
 */
@ELayout(Layout = R.layout.second_act)
public class ImgShowAct extends BaseActivity {

    @EWidget(id = R.id.picture)
    private ImageView mShowIv;

    @EWidget(id = R.id.appBarLayout)
    private AppBarLayout mBarLayout;

    @EWidget(id = R.id.toolBar)
    private Toolbar mToolBar;

    private PhotoViewAttacher mPhotoAttacher;
    private ImgBase mData;

    public static final String TRANSIT_PIC = "picture";

    @Override
    public void initView(View view, Bundle bundle) {
        setSupportActionBar(mToolBar);
        mPhotoAttacher = new PhotoViewAttacher(mShowIv);
        mData = (ImgBase) getIntent().getExtras().getSerializable("data");
        Picasso.with(this).load(mData.getNomalImgUrl()).into(mShowIv);
        setTitle("美图");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        ViewCompat.setTransitionName(mShowIv, TRANSIT_PIC);
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int[] registReceiveCmdIds() {
        return new int[0];
    }

    @Override
    public void onHandCmd(int i, Object o) {

    }

    @Override
    public void eventHandle(int i, Object o) {

    }

    @Override
    public void eventError(int i, int i1, String s) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
