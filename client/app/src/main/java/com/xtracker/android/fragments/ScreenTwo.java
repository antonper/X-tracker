package com.xtracker.android.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.xtracker.android.R;
import com.xtracker.android.activities.TrackActivity;
import com.xtracker.android.adapters.TracksListAdapter;
import com.xtracker.android.objects.GApiClient;
import com.xtracker.android.objects.Point;
import com.xtracker.android.objects.Track;
import com.xtracker.android.rest.ApiService;
import com.xtracker.android.rest.RestClient;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ScreenTwo extends Fragment implements View.OnClickListener {

    private View rootView;
    private TextView helloOutput;
    private ApiService apiService;
    private ArrayList<Track> mTracks;
    private ArrayList<Long> tracksArray;
    private LinearLayoutManager mLayoutManager;
    private TracksListAdapter mAdapter;

    public ScreenTwo() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.screen_two, container,
                false);

        helloOutput = (TextView) rootView.findViewById(R.id.textView);

        apiService = RestClient.getInstance().getApiService();

        //Initialize tracksArray
        mTracks = new ArrayList<Track>();

        getTracks();

        RecyclerView resView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        //Create an adapter for ListView

        resView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        resView.setLayoutManager(mLayoutManager);
        //bing adapter to ListView
        mAdapter = new TracksListAdapter(getActivity(), mTracks);
        resView.setAdapter(mAdapter);
//        resView.setOnItemClickListener(mMessageClickedHandler);
//        resView.setOnClickListener((View.OnClickListener) mMessageClickedHandler);

        return rootView;
    }

    private void getTracks() {
        apiService.getTracksList(new Callback<List<Track>>() {
            @Override
            public void success(List<Track> tracks, Response response) {
                helloOutput.setText(String.valueOf(tracks.size()));
                setTracks(tracks);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println(error.toString());
                updateTracksArray(3);
            }
        });
    }


    public void setTracks(List<Track> tracks) {
        this.mTracks.addAll(tracks);
    }

    private void updateTracksArray(long trackId) {
        this.tracksArray.add(trackId);
    }

    @Override
    public void onClick(View v) {

    }

    private void startTrackActivity(long trackId) {
        Intent intent = new Intent(this.getActivity(), TrackActivity.class);
        intent.putExtra("TRACK_ID", trackId);
        startActivity(intent);
    }


    private AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (parent.getId() == R.id.recyclerView) {
                TextView idText = (TextView) view;
                startTrackActivity(Long.valueOf(String.valueOf(idText.getText())));
            }
        }
    };

}