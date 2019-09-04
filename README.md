# GradientBg
根据banner页面，渐变背景和状态栏

##最近看到很多国外的购物app，状态栏和banner背景会根据当前的banner图片发生变化
刚开始还以为是通过后台配置色值，前端展示的

#Palette
后来发现了com.android.support:palette-v7 这个库
然后通过Palette.from(bitmap).generate()
可以取到bitmap的色值

包含以下几种类型
*Vibrant （有活力）
*Vibrant dark（有活力 暗色）
*Vibrant light（有活力 亮色）
*Muted （柔和）
*Muted dark（柔和 暗色）
*Muted light（柔和 亮色）

效果如下

![video](https://github.com/FishInWater-1999/GithubUseTest/blob/master/bac_3.jpg)


