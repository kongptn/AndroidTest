package com.example.android.findjoinsports;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.findjoinsports.CreateActivity.CreateFootball;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    double lat;
    double lng;
    private GoogleMap mMap;
    PlaceAutocompleteFragment placeAutoComplete;
    private LocationManager locationManager;
    private LocationListener listener;
    double locationLong;
    double locationLat;
    String local;
    private Button btFinish;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final String TAG = "MapActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private Boolean mLocationPermissionsGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setTitle("แผนที่");
        //getSupportActionBar().hide();
        btFinish = (Button) findViewById(R.id.btFinish);
        btFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lat == 0.0 || lng == 0.0) {
                    Toast.makeText(MapsActivity.this, "กรุณาระบุตำแหน่ง", Toast.LENGTH_SHORT).show();
                } else {
                    PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit().putString("lat", String.valueOf(lat)).apply();
                    PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit().putString("lng", String.valueOf(lng)).apply();
                    Intent i = new Intent(MapsActivity.this, CreateFootball.class);
                    Log.d("ddd", String.valueOf(lat));
                    Log.d("ddd", String.valueOf(lng));
                    startActivity(i);
                    finish();
                }
            }
        });
        placeAutoComplete = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete);
        placeAutoComplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {

                mMap.clear();
                local = String.valueOf(place.getName());
                Geocoder gc = new Geocoder(getApplicationContext());
                List<Address> list = null;
                try {
                    list = gc.getFromLocationName(local, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (list != null && list.size() > 0) {
                    Address address = list.get(0);
                    String locality = address.getLocality();
                    Log.d("Location ", String.valueOf(address) + " " + locality);
                    lat = address.getLatitude();
                    lng = address.getLongitude();
                    goToLocationZoom(lat, lng, 15);
                    setMarker(local, lat, lng);
                    Log.d("Maps", "Place selected: " + place.getName() + place.getLatLng() + " " + lat + " " + lng);
                } else {
                    Toast.makeText(MapsActivity.this, "เกิดความผิดพลาดจากระบบ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Status status) {
                Log.d("Maps", "An error occurred: " + status);
            }
        });
        getLocationPermission();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        try {
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json));

            if (!success) {
                Log.e("test", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("test", "Can't find style. Error: ", e);
        }
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                mMap.clear();
                lat = latLng.latitude;
                lng = latLng.longitude;
                MapsActivity.this.setMarker("Local", lat, lng);

            }
        });


        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {

                Geocoder gc = new Geocoder(MapsActivity.this);
                LatLng ll = marker.getPosition();
                lat = ll.latitude;
                lng = ll.longitude;

                List<Address> list = null;
                try {
                    list = gc.getFromLocation(lat, lng, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Address add = list.get(0);
                marker.setTitle(add.getLocality());
                marker.showInfoWindow();


            }
        });
        if (mLocationPermissionsGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);

        }
    }

    private void setMarker(String locality, double lat, double lng) {
//        if(marker != null){
//            removeEverything();
//        }

        MarkerOptions options = new MarkerOptions()
                .title(locality)
                .draggable(true)
                //.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_add_location))
                .position(new LatLng(lat, lng))
                //.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_placeholder))
                .snippet("I am Here");

        markers.add(mMap.addMarker(options));


    }

    private void setMarkerLocation(String locality, double lat, double lng) {
//        if(marker != null){
//            removeEverything();
//        }

        MarkerOptions options = new MarkerOptions()
                .title(locality)
                .draggable(true)
                //.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_navigation))
                .position(new LatLng(lat, lng))
                //.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_man))
                .snippet("I am Here");

        markers.add(mMap.addMarker(options));


    }

    ArrayList<Marker> markers = new ArrayList<Marker>();

    Polygon shape;

    private void removeEverything() {
        for (Marker marker : markers) {
            marker.remove();
        }
        markers.clear();
        shape.remove();
        shape = null;

    }

    private void goToLocationZoom(double lat, double lng, float zoom) {
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mMap.moveCamera(update);

    }

    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (mLocationPermissionsGranted) {

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();
                            if (currentLocation == null) {
                                Toast.makeText(MapsActivity.this, "เปิด GPS เพื่อระบุตำแหน่ง", Toast.LENGTH_SHORT).show();
                            } else {
                                goToLocationZoom(currentLocation.getLatitude(), currentLocation.getLongitude(), 15);
                                lat = currentLocation.getLatitude();
                                lng = currentLocation.getLongitude();
                            }
                        } else {
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(MapsActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }
    }

    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void initMap() {
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }

}
