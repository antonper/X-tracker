package com.xtracker.android.activities;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.xtracker.android.R;
import com.xtracker.android.Utils;
import com.xtracker.android.caching.CacheManager;
import com.xtracker.android.objects.GoogleMapsManager;
import com.xtracker.android.objects.Point;
import com.xtracker.android.objects.Track;
import com.xtracker.android.rest.ApiService;
import com.xtracker.android.rest.RestClient;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TrackActivity extends ActionBarActivity {

    private long trackId;
    private ApiService apiService;
    GoogleMapsManager mapsManager;
    Track track;
    private CacheManager cacheManager = CacheManager.getInstance();


    public void setTrack(Track track) {
        this.track = track;
        for (Point point : track.getPoints()) {
            System.out.println(point.getLat());

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);
        Intent intent = this.getIntent();
        trackId = intent.getLongExtra("TRACK_ID", 1);
        apiService = RestClient.getInstance().getApiService();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //final TextView editText = (TextView) findViewById(R.id.textView2);
        //editText.setText(String.valueOf(trackId));

        if (Utils.isNetworkConnected(this)) {
            apiService.getTrack(trackId, new Callback<Track>() {
                @Override
                public void success(Track track, Response response) {

                    setTrack(track);
                    mapsManager = new GoogleMapsManager(getFragmentManager(), track);
                    //cacheManager.saveTrack(track);
                }

                @Override
                public void failure(RetrofitError error) {
                   // editText.setText(error.getMessage());
                }
            });
        } else {
            Track track = cacheManager.getTrack(trackId);
            if (track != null) {
                setTrack(track);
            } else {
                Toast.makeText(this, R.string.track_not_cached, Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_track, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
