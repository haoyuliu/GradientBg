package com.nemo.taobao.tools;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.nemo.taobao.model.ColorInfo;
import com.youth.banner.loader.ImageLoader;

import java.util.List;


/**
 * @Author: Nemo
 * @Date: 2019-09-04 17:05
 * @Description:
 */
public class GlideImageLoader extends ImageLoader {
    private List<ColorInfo> colorList;
    private Palette palette;

    public GlideImageLoader(List<ColorInfo> colorList) {
        this.colorList = colorList;
    }
    @Override
    public void displayImage(Context context, final Object path, ImageView imageView) {

        Glide.with(context).asBitmap().load(path.toString()).listener(new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                setColorList(resource, path.toString());
                return false;
            }
        }).apply(RequestOptions.bitmapTransform(new RoundedCorners(20))).into(imageView);
    }

    @Override
    public ImageView createImageView(Context context) {
        //使用fresco，需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
        ImageView imageView = new ImageView(context);
        int padding = dp2px(context,16);
        imageView.setPadding(padding,0,padding,0);
        return imageView;
    }

    private void setColorList(Bitmap bitmap, String imgUrl) {
        if (colorList == null||bitmap==null) {
            return;
        }
        palette = Palette.from(bitmap).generate();
        for (int i = 0; i < colorList.size(); i++) {
            if (colorList.get(i).getImgUrl().equals(imgUrl)) {
                if (palette.getVibrantSwatch() != null) {
                    colorList.get(i).setVibrantColor(palette.getVibrantSwatch().getRgb());
                }
                if (palette.getDarkVibrantSwatch() != null) {
                    colorList.get(i).setVibrantDarkColor(palette.getDarkVibrantSwatch().getRgb());
                }
                if (palette.getLightVibrantSwatch() != null) {
                    colorList.get(i).setVibrantLightColor(palette.getLightVibrantSwatch().getRgb());
                }
                if (palette.getMutedSwatch() != null) {
                    colorList.get(i).setMutedColor(palette.getMutedSwatch().getRgb());
                }
                if (palette.getDarkMutedSwatch() != null) {
                    colorList.get(i).setMutedDarkColor(palette.getDarkMutedSwatch().getRgb());
                }
                if (palette.getLightVibrantSwatch() != null) {
                    colorList.get(i).setMutedLightColor(palette.getLightVibrantSwatch().getRgb());
                }
            }
        }
    }


    /**
     * Vibrant （有活力）
     * Vibrant dark（有活力 暗色）
     * Vibrant light（有活力 亮色）
     * Muted （柔和）
     * Muted dark（柔和 暗色）
     * Muted light（柔和 亮色）
     */

    public int getVibrantColor(int position) {
        return colorList.get(position).getVibrantColor();
    }

    public int getVibrantDarkColor(int position) {
        return colorList.get(position).getVibrantDarkColor();
    }

    public int getVibrantLightColor(int position) {
        return colorList.get(position).getVibrantLightColor();
    }

    public int getMutedColor(int position) {
        return colorList.get(position).getMutedColor();
    }

    public int getMutedDarkColor(int position) {
        return colorList.get(position).getMutedDarkColor();
    }

    public int getMutedLightColor(int position) {
        return colorList.get(position).getMutedLightColor();
    }

    /**
     * 将dp值转换为px值
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
