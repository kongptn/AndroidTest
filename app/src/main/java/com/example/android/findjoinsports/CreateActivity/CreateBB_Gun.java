package com.example.android.findjoinsports.CreateActivity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.findjoinsports.DatePickerFragment;
import com.example.android.findjoinsports.NavDrawer;
import com.example.android.findjoinsports.R;
import com.example.android.findjoinsports.SearchActivity.SearchActivity;
import com.example.android.findjoinsports.SessionManager;
import com.example.android.findjoinsports.TimePickerFragment;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
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


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateBB_Gun extends AppCompatActivity implements View.OnClickListener ,DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, OnMapReadyCallback {
    private EditText editStad_name, editdescrip, PHOTO, editlocation;
    private TextView textDate, textTime, nametxt, emailtxt;
    private ImageView imgView;
    private Button btn_create, ChooseBT;
    private String stadium_name, description ,date, time, mName,location, mUser;
    private final int IMG_REQUEST = 1;
    String type_id = "2";
    String numberjoin = "0";
    private int user_id;
    private Bitmap bitmap;
    private static final String URL = "http://10.13.3.103/findjoinsport/football/InsertData.php";
    //private String UploadUrl = "http://10.13.4.117/ImageUploadApp/updateinfo.php";
    SessionManager sessionManager;
    String photoName;
    double lat;
    double lng;
    String latti;
    String lngti;
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
        setContentView(R.layout.activity_create_bb__gun);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

//        nametxt = nametxt;
//        emailtxt = emailtxt;

//        nametxt = findViewById(R.id.nametxt);
//        emailtxt = findViewById(R.id.emailtxt);

        HashMap<String, String> user = sessionManager.getUserDetail();
        mUser = user.get(sessionManager.USER_ID);
        mName = user.get(sessionManager.NAME);
//        nametxt.setText(mName);
//        emailtxt.setText(mEmail);




        Button btnTime = (Button) findViewById(R.id.btnTime);
        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });


        Button btnDate = (Button) findViewById(R.id.btnDate);
        btnDate.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker");
            }
        });
        textDate = (TextView)findViewById(R.id.textDate);
        textTime = (TextView)findViewById(R.id.textTime);
        ChooseBT = (Button) findViewById(R.id.chooseBT);
        PHOTO = (EditText) findViewById(R.id.photo);
        imgView = (ImageView) findViewById(R.id.imageView);
        btn_create = (Button) findViewById(R.id.btn_create);
        editlocation = (EditText) findViewById(R.id.editlocation);
        ChooseBT.setOnClickListener(this);
        btn_create.setOnClickListener(this);

        Long tsLong = System.currentTimeMillis()/1000;
        photoName = tsLong.toString();

        onBindView();

        btn_create = (Button) findViewById(R.id.btn_create);
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lat == 0.0 || lng == 0.0) {
                    Toast.makeText(CreateBB_Gun.this, "กรุณาระบุตำแหน่ง", Toast.LENGTH_SHORT).show();
                } else {
                    PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit().putString("Latitude", String.valueOf(lat)).apply();
                    PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit().putString("Longitude", String.valueOf(lng)).apply();
                    Intent i = new Intent(CreateBB_Gun.this, NavDrawer.class);
                    Log.d("ddd", String.valueOf(lat));
                    Log.d("ddd", String.valueOf(lng));
                    latti = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("Latitude", "Null Value");
                    lngti = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("Longitude", "Null Value");
                    Log.d("ddddd", String.valueOf(lat));
                    Log.d("ddddd", String.valueOf(lng));
                    Long tsLong = System.currentTimeMillis()/1000;
                    photoName = tsLong.toString();
                    onEditText();
                    onButtonClick();
                    startActivity(i);
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
                    Toast.makeText(CreateBB_Gun.this, "เกิดความผิดพลาดจากระบบ", Toast.LENGTH_SHORT).show();
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
                CreateBB_Gun.this.setMarker("Local", lat, lng);

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

                Geocoder gc = new Geocoder(CreateBB_Gun.this);
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
                                Toast.makeText(CreateBB_Gun.this, "เปิด GPS เพื่อระบุตำแหน่ง", Toast.LENGTH_SHORT).show();
                            } else {
                                goToLocationZoom(currentLocation.getLatitude(), currentLocation.getLongitude(), 15);
                                lat = currentLocation.getLatitude();
                                lng = currentLocation.getLongitude();
                            }
                        } else {
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(CreateBB_Gun.this, "unable to get current location", Toast.LENGTH_SHORT).show();
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


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView textTime = (TextView)findViewById(R.id.textTime);
        textTime.setText(hourOfDay + " นาฬิกา " + minute +" นาที");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        TextView textDate = (TextView) findViewById(R.id.textDate);
        textDate.setText(currentDateString);
    }

    private void onBindView() {
        editStad_name = (EditText) findViewById(R.id.editStad_name);
        editdescrip = (EditText) findViewById(R.id.editdescrip);
        btn_create = (Button) findViewById(R.id.btn_create);
    }

    private void onEditText() {
        stadium_name = editStad_name.getText().toString();
        description = editdescrip.getText().toString();
        location = editlocation.getText().toString();
        date = textDate.getText().toString();
        time = textTime.getText().toString();
        type_id = "2";

//       mName = nametxt.getText().toString();


    }
    private void onButtonClick() {
        if (!stadium_name.isEmpty() && !description.isEmpty()) {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest request = new StringRequest(Request.Method.POST, getString(R.string.Host)+"/findjoinsport/football/insert_act.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("onResponse", response);
                    editStad_name.setText("");
                    editdescrip.setText("");

                    editlocation.setText("");
                    textDate.setText("");
                    textTime.setText("");
                    // --
                    imgView.setImageResource(0);
                    imgView.setVisibility(View.GONE);
                    Toast.makeText(CreateBB_Gun.this, "สร้างกิจกรรมแล้ว", Toast.LENGTH_SHORT).show();
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.d("Create Error", error.toString());
//                    Toast.makeText(CreateFootball.this, "เกิดข้อผิดพลาดโปรดลองอีกครั้ง", Toast.LENGTH_SHORT).show();
                    Toast.makeText(CreateBB_Gun.this,"ใส่ข้อมูลไม่ครบถ้วน",Toast.LENGTH_SHORT).show();
                }
                private Context getContext() {
                    return null;
                }
            }) {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("stadium_name", stadium_name);
                    params.put("description", description);
                    params.put("photo", photoName);
                    params.put("image", imageToString(bitmap));
                    params.put("date", date);
                    params.put("time", time);
                    Log.d("skjod",date);
                    params.put("Latitude", latti);
                    params.put("Longitude", lngti);
                    params.put("location", location);
                    params.put("type_id", type_id);
                    params.put("user_id", mUser);
                    params.put("name", mName);
                    params.put("number_join", numberjoin);


                    return params;
                }
            };
            requestQueue.add(request);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chooseBT:
                selectImage();
                break;


            case R.id.btn_create:
//                uploadImage();
                break;
        }
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "select Picture"), 1);
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                imgView.setImageBitmap(bitmap);
                imgView.setVisibility(View.VISIBLE);
                //PHOTO.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
//        if (requestCode == PLACE_PICKER_REQUEST)//map api
//        {
//            if (resultCode == RESULT_OK)
//            {
//                Place place = PlacePicker.getPlace(CreateFootball.this, data);
//                tvPlace.setText(place.getAddress());
//            }
//        }

    }


    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_action, menu);

        Menu action = menu;
        action.findItem(R.id.menu_save).setVisible(false);
        action.findItem(R.id.menu_edit).setVisible(false);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_back:

                Intent back = new Intent(CreateBB_Gun.this, NavDrawer.class);
                startActivity(back);

                return true;
            default:

                return super.onOptionsItemSelected(item);
        }


    }

}
