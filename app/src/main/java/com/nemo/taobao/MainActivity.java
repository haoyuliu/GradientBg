package com.nemo.taobao;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.jaeger.library.StatusBarUtil;
import com.nemo.taobao.adapter.BannerAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Nemo
 * @Date: 2019-09-04 16:48
 * @Description:
 */
public class MainActivity extends AppCompatActivity {
    private RecyclerView rvHome;
    private List<String>banners = new ArrayList<>();
    private DelegateAdapter adapters;
    private BannerAdapter mBannerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        initData();
        initView();
    }

    private void initData() {
        banners.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1567598745703&di=334a123900f2da2fb9b073f11d460575&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01093a59a8c918a801211d254920fa.jpg%402o.jpg");
        banners.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1567598745703&di=005730d81c5e1d3f235b92c78952e326&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01654f5777e1a70000012e7eefe905.jpg");
        banners.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1567598745699&di=9bab3b23f12c49a5bb09560396760ee2&imgtype=0&src=http%3A%2F%2Fpic.90sjimg.com%2Fback_pic%2Fqk%2Fback_origin_pic%2F00%2F01%2F57%2Fb1ed943793cb94acf58be7f7570306b3.jpg");
    }

    private void initView() {
        rvHome = findViewById(R.id.rv_home);
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        viewPool.setMaxRecycledViews(10, 0);
        rvHome.setRecycledViewPool(viewPool);
        VirtualLayoutManager layoutManager = new VirtualLayoutManager(this);
        rvHome.setLayoutManager(layoutManager);
        adapters = new DelegateAdapter(layoutManager, false);

        mBannerAdapter = new BannerAdapter(this,new SingleLayoutHelper());
        adapters.addAdapter(mBannerAdapter);
        rvHome.setAdapter(adapters);

        mBannerAdapter.setData(banners);
        mBannerAdapter.notifyDataSetChanged();


    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEvent(Integer vibrantColor){
        StatusBarUtil.setColor(this, vibrantColor, 20);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
