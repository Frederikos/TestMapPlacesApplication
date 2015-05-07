package ru.atf.test.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

import ru.atf.test.R;
import ru.atf.test.activity.PlaceDetailsActivity;
import ru.atf.test.adapter.PlacesAdapter;
import ru.atf.test.io.net.PlacesLoader;
import ru.atf.test.model.PlaceModel;
import ru.atf.test.utils.AnimationHelper;
import ru.atf.test.utils.PlacesScrollListener;
import ru.atf.test.widget.OverScrollListView;

public class MapFragment extends Fragment {

    static final int LOCATION_DEFAULT_ZOOM = 10;

    GoogleMap map;
    MapView mapView;
    OverScrollListView placesList;
    Button btnPlaces;
    ProgressDialog progressDialog;
    PlacesAdapter placesAdapter;
    View listFooterView;

    HashMap<Marker, PlaceModel> markers = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        initMap(view, savedInstanceState);
        initPlacesList(view, inflater);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getString(R.string.loading));

        loadPlaces();
        return view;
    }

    public void updateMapLocation(double latitude, double longitude) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), LOCATION_DEFAULT_ZOOM);
        map.animateCamera(cameraUpdate);
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    private void loadPlaces() {
        progressDialog.show();
        PlacesLoader.getInstance().getPlacesAsync(0, new PlacesLoader.LoadListener() {
            @Override
            public void onSuccess(ArrayList<PlaceModel> placeModels) {
                progressDialog.dismiss();
                for (PlaceModel placeModel : placeModels) {
                    Marker marker = map.addMarker(new MarkerOptions().position(new LatLng(placeModel.location.latitude, placeModel.location.longitude)).title(placeModel.title).snippet(placeModel.subText));
                    markers.put(marker, placeModel);
                }
                if (placeModels.size() > 0) {
                    updateMapLocation(placeModels.get(0).location.latitude, placeModels.get(0).location.longitude);
                }
                placesAdapter.addItems(placeModels);
            }

            @Override
            public void onFailed(String errorString) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), errorString, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initMap(View view, Bundle savedInstanceState) {
        mapView = (MapView) view.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        map = mapView.getMap();
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.setMyLocationEnabled(true);
        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                openPlaceDetails(markers.get(marker));
            }
        });
        MapsInitializer.initialize(this.getActivity());
    }

    private void initPlacesList(View view, LayoutInflater layoutInflater) {
        placesList = (OverScrollListView) view.findViewById(R.id.lv_places);
        btnPlaces = (Button) view.findViewById(R.id.btn_places_list);

        View headerView = layoutInflater.inflate(R.layout.places_list_header_view, null);
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hidePlacesList();
            }
        });
        placesList.addHeaderView(headerView);

        listFooterView = layoutInflater.inflate(R.layout.places_list_footer_view, null);
        placesList.addFooterView(listFooterView);

        placesAdapter = new PlacesAdapter(getActivity(), R.layout.place_list_item);

        placesList.setAdapter(placesAdapter);

        placesList.setOnScrollListener(new PlacesScrollListener(new PlacesScrollListener.OnLoadMoreListener() {
            @Override
            public void onLoadMore(int pageNumber, int totalItemsCount) {
                loadNextPage(pageNumber);
            }
        }));

        placesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openPlaceDetails(placesAdapter.getItem(position - 1));
            }
        });

        placesList.setOnOverScrollListener(new OverScrollListView.OnOverScrollListener() {
            @Override
            public void onOverScrollTop() {
                hidePlacesList();
            }
        });

        btnPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPlacesList();
            }
        });
    }

    private void loadNextPage(int pageNumber) {
        if (PlacesLoader.getInstance().getPageCount() > pageNumber) {
            PlacesLoader.getInstance().getPlacesAsync(pageNumber, new PlacesLoader.LoadListener() {
                @Override
                public void onSuccess(ArrayList<PlaceModel> placeModels) {
                    placesAdapter.addItems(placeModels);
                }

                @Override
                public void onFailed(String errorString) {
                    Toast.makeText(getActivity(), errorString, Toast.LENGTH_LONG).show();
                }
            });
        } else {
            placesList.removeFooterView(listFooterView);
        }
    }

    private void openPlaceDetails(PlaceModel placeModel) {
        Intent placeDetailsIntent = new Intent(getActivity(), PlaceDetailsActivity.class);
        placeDetailsIntent.putExtra(PlaceDetailsActivity.TITLE, placeModel.title);
        placeDetailsIntent.putExtra(PlaceDetailsActivity.IMAGE_URL, placeModel.imageUrl);
        startActivity(placeDetailsIntent);
    }

    private void hidePlacesList() {
        AnimationHelper.animateHideListView(placesList, btnPlaces);
    }

    private void showPlacesList() {
        AnimationHelper.animateShowListView(placesList, btnPlaces);
    }

}
