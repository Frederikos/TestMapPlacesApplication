package ru.atf.test.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import ru.atf.test.R;
import ru.atf.test.fragment.MapFragment;

public class MapActivity extends FragmentActivity {

    MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
    }
}
