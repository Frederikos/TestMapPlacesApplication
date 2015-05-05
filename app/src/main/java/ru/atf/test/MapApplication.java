package ru.atf.test;

import android.app.Application;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class MapApplication extends Application {

    static final int FADE_DURATION = 500;
    static final int DELAY_BEFORE_LOADING = 350;

    @Override
    public void onCreate() {
        super.onCreate();
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .displayer(new FadeInBitmapDisplayer(FADE_DURATION))
                .showImageOnLoading(new ColorDrawable(Color.LTGRAY))
                .cacheInMemory(true)
                .delayBeforeLoading(DELAY_BEFORE_LOADING)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);
    }
}
