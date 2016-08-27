package com.transition.scorekeeper.mobile.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.wearable.view.WearableListView;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;
import com.transition.scorekeeper.R;
import com.transition.scorekeeper.common.Constants;
import com.transition.scorekeeper.mobile.IntentFactory;
import com.transition.scorekeeper.mobile.model.Player;
import com.transition.scorekeeper.mobile.view.adapter.PlayerAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

public class MainActivity extends Activity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, WearableListView.ClickListener, DataApi.DataListener {
    private GoogleApiClient googleApiClient;
    private WearableListView list;
    private View empty;
    private PlayerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeGoogleApiClient();
        googleApiClient.connect();

        list = (WearableListView) findViewById(R.id.wearable_list);
        empty = findViewById(R.id.empty);
        adapter = new PlayerAdapter(this);
        list.setAdapter(adapter);
        list.setClickListener(this);
    }

    private void initializeGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Wearable.DataApi.addListener(googleApiClient, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (googleApiClient != null) {
            Wearable.DataApi.removeListener(googleApiClient, this);
        }
    }

    @NonNull
    private PutDataMapRequest generateDataMapRequest(Long idPlayer) {
        PutDataMapRequest putDataMapRequest = PutDataMapRequest.create(Constants.Path.GOAL);
        DataMap dataMap = putDataMapRequest.getDataMap();
        dataMap.putLong(Constants.Key.ID_PLAYER, idPlayer);
        dataMap.putLong(Constants.Key.TIME, new Date().getTime());
        return putDataMapRequest;
    }

    private void sendRequest(PutDataMapRequest putDataMapRequest) {
        PutDataRequest request = putDataMapRequest.asPutDataRequest();
        Wearable.DataApi.putDataItem(googleApiClient, request)
                .setResultCallback(new ResultCallback<DataApi.DataItemResult>() {
                    @Override
                    public void onResult(@NonNull DataApi.DataItemResult dataItemResult) {
                        if (!dataItemResult.getStatus().isSuccess()) {
                            Toast.makeText(getApplicationContext(), dataItemResult.getStatus().getStatusMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            startActivity(IntentFactory.getConfirmation(getApplicationContext(), null));
                        }
                    }
                });
    }

    @Override
    public void onClick(WearableListView.ViewHolder viewHolder) {
        Long id = (Long) viewHolder.itemView.getTag();
        sendRequest(generateDataMapRequest(id));
    }

    @Override
    public void onTopEmptyRegionClick() {

    }

    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {
        ArrayList<Player> players = new ArrayList<>();
        for (DataEvent dataEvent : dataEventBuffer) {
            DataItem dataItem = dataEvent.getDataItem();
            if (dataItem.getUri().getPath().equals(Constants.Path.PLAYERS)) {
                DataMap dataMap = DataMapItem.fromDataItem(dataItem).getDataMap();
                Set<String> keySet = dataMap.keySet();
                for (String key : keySet) {
                    if (isPlayer(key)) {
                        players.add(new Player((Long) dataMap.get(key), key));
                    }
                }
            }
        }
        if (!players.isEmpty()) {
            list.setVisibility(View.VISIBLE);
            empty.setVisibility(View.GONE);
            adapter.clear();
            adapter.addAll(players);
        }
    }

    private boolean isPlayer(String key) {
        return !(key.equals(Constants.Key.ID_MATCH) || key.equals(Constants.Key.TIME));
    }
}
