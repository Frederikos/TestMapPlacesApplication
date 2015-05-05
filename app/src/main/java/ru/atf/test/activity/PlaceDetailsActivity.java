package ru.atf.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import ru.atf.test.R;

public class PlaceDetailsActivity extends Activity {

    public static final String TITLE = "title";
    public static final String IMAGE_URL = "image_url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);

        ImageView ivPlaceImage = (ImageView) findViewById(R.id.iv_place_image);
        TextView tvPlaceTitle = (TextView) findViewById(R.id.tv_place_title);

        tvPlaceTitle.setText(getIntent().getStringExtra(TITLE));
        ImageLoader.getInstance().displayImage(getIntent().getStringExtra(IMAGE_URL), ivPlaceImage);
    }
}
