package com.xtracker.android.objects;

import android.app.FragmentManager;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.xtracker.android.R;
import com.xtracker.android.rest.ApiService;
import com.xtracker.android.rest.RestClient;

/**
 * Created by Ilya on 28.03.2015.
 */
public class GoogleMapsManager implements OnMapReadyCallback {

    private final FragmentManager fragmentManager;
    private MapFragment mMap;
    private GApiClient mApiClient;
    private LatLngBounds AREA;
    private ApiService apiService = RestClient.getInstance().getApiService();

    public GoogleMapsManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;

        MapFragment fragment = (MapFragment) fragmentManager.findFragmentById(R.id.map);
        fragment.getMapAsync(this);

        mMap = fragment;
//        mApiClient = new GApiClient(mMap.getActivity(), null);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (mApiClient.getmCurrentLocation() != null) {
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(mApiClient.getmCurrentLocation().getLatitude(), mApiClient.getmCurrentLocation().getLatitude()))
                    .title("Hello world"));
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }
        googleMap.setMyLocationEnabled(true);
//        AREA = new LatLngBounds();
    }

    public void workMethod() {


    }
}
