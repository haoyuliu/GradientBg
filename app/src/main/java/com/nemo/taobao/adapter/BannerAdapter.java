package com.nemo.taobao.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.nemo.taobao.R;
import com.nemo.taobao.model.ColorInfo;
import com.nemo.taobao.tools.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Nemo 
 * @Date: 2019-09-04 17:02
 * @Description:
 */
public class BannerAdapter extends DelegateAdapter.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private LayoutHelper mHelper;
    private List<String>bannerInfos;
    private List<ColorInfo> colorList = new ArrayList<>();
    private int count;
    private GlideImageLoader imageLoader;
    public BannerAdapter(Context context, LayoutHelper helper) {
        this.mContext =context;
        this.mHelper=helper;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mHelper;
    }
    public void setData(List<String>bannerInfos){
        this.bannerInfos = bannerInfos;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_banner_layout, parent, false);
        return new RecyclerViewItemHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        RecyclerViewItemHolder recyclerViewHolder = (RecyclerViewItemHolder) holder;
        count = bannerInfos.size();
        colorList.clear();
        if (count>0){
            for (int i = 0; i <= count + 1; i++) {
                ColorInfo info = new ColorInfo();
                if (i == 0) {
                    info.setImgUrl(bannerInfos.get(count - 1));
                } else if (i == count + 1) {
                    info.setImgUrl(bannerInfos.get(0));
                } else {
                    info.setImgUrl(bannerInfos.get(i - 1));
                }
                colorList.add(info);
            }
        }
        imageLoader = new GlideImageLoader(colorList);
        recyclerViewHolder.banner.setImageLoader(imageLoader);
        recyclerViewHolder.banner.setBannerAnimation(Transformer.Default);
        List<String> images = new ArrayList<>();
        for (String info:bannerInfos) {
            images.add(info);
        }
        recyclerViewHolder.banner.setImages(images);
        recyclerViewHolder.banner.setDelayTime(5000);
        recyclerViewHolder.banner.start();

        recyclerViewHolder.banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffset > 1) {//会出现极个别大于1的数据
                    return;
                }
                //修正position，解决两头颜色错乱，来自Banner控件源码
                if (position == 0) {
                    position = count;
                }
                if (position > count) {
                    position = 1;
                }
                int pos = (position + 1) % count;//很关键
                int vibrantColor = ColorUtils.blendARGB(imageLoader.getVibrantColor(pos), imageLoader.getVibrantColor(pos + 1), positionOffset);
                EventBus.getDefault().post(vibrantColor);
                recyclerViewHolder.rlBg.setBackgroundColor(vibrantColor);
            }

            @Override
            public void onPageSelected(final int position) {
                new Handler().postDelayed(() -> {
                    int vibrantColor = imageLoader.getVibrantColor(position+1);
                    recyclerViewHolder.rlBg.setBackgroundColor(vibrantColor);
                }, 500);

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    @Override
    public int getItemCount() {
        if (bannerInfos==null){
            return 0;
        }
        return 1;
    }

    /**
     * 正常条目的item的ViewHolder
     */
    private class RecyclerViewItemHolder extends RecyclerView.ViewHolder {

        public Banner banner;
        private RelativeLayout rlBg;
        public RecyclerViewItemHolder(View itemView) {
            super(itemView);
            banner = itemView.findViewById(R.id.banner);
            rlBg= itemView.findViewById(R.id.rl_bg);
        }
    }
}
