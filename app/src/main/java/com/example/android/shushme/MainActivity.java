package com.example.android.shushme;

/*
* Copyright (C) 2017 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*  	http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
import android.Manifest.permission;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.Place.Field;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
    GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String API_KEY = "AIzaSyCTmMdhIcJoJfDk7IuxA-YO7U3BCa-oP3U";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 0;
    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    private PlaceListAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private CheckBox mLocationPermissionCheckbox;

    /**
     * Called when the activity is starting
     *
     * @param savedInstanceState The Bundle that contains the data supplied in onSaveInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the recycler view
        mRecyclerView = (RecyclerView) findViewById(R.id.places_list_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new PlaceListAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), API_KEY);
        }

        mLocationPermissionCheckbox = findViewById(R.id.checkbox_location_permission);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (ContextCompat.checkSelfPermission(this, permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionCheckbox.setChecked(true);
            mLocationPermissionCheckbox.setEnabled(false);
        } else {
            mLocationPermissionCheckbox.setChecked(false);
            mLocationPermissionCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    ActivityCompat.requestPermissions(
                        MainActivity.this, new String[] {permission.ACCESS_FINE_LOCATION},
                        LOCATION_PERMISSION_REQUEST_CODE);
                }
            });
        }

        Button addNewLocationButton = findViewById(R.id.button_add_new_location);
        addNewLocationButton.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, R.string.location_permission_needed, Toast.LENGTH_SHORT)
                    .show();
            } else {
                List<Field> fields = Arrays.asList(Field.ID, Field.NAME);
                Intent intent =
                    new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                        .build(this);
                startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PLACE_AUTOCOMPLETE_REQUEST_CODE: {
                if (resultCode == RESULT_OK) {
                    Place place = Autocomplete.getPlaceFromIntent(data);
                    Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                    Status status = Autocomplete.getStatusFromIntent(data);
                    Log.i(TAG, status.getStatusMessage());
                }
                return;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionCheckbox.setChecked(true);
                    mLocationPermissionCheckbox.setEnabled(false);
                    Toast.makeText(this, R.string.location_permission_granted, Toast.LENGTH_SHORT)
                        .show();
                } else {
                    Toast.makeText(this, R.string.location_permission_not_granted, Toast.LENGTH_SHORT)
                        .show();
                }
            }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "Google API Client connection successful!");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Google API Client connection suspended!");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "Google API Client connection failed!");
    }
}
