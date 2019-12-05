package com.lf.sinosinovo.ui.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.lf.sinosinovo.R;

import java.io.IOException;
import java.util.Locale;

import static android.location.LocationManager.NETWORK_PROVIDER;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    boolean isGpsEnable = false;
    boolean isNetworkEnable = false;
    Location location;
    LocationManager locationManager;
    Context mcontext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }

        mcontext = this;

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        isGpsEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnable = locationManager.isProviderEnabled(NETWORK_PROVIDER);

        if (isGpsEnable) {
            if (location == null) {
                if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 5, myLocationListener);
                if (locationManager != null) {
                    locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }
            }

        }

        if (isNetworkEnable) {
            if (location == null) {
                locationManager.requestLocationUpdates(NETWORK_PROVIDER, 1000, 5, myLocationListener);
                if (locationManager != null) {
                    locationManager.getLastKnownLocation(NETWORK_PROVIDER);
                }
            }

        }
        if (location != null) {
            locationManager.requestLocationUpdates(NETWORK_PROVIDER, 1000, 5, myLocationListener);
            if (locationManager != null) {
                locationManager.getLastKnownLocation(NETWORK_PROVIDER);
            }

        }


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        try {
            LatLng sydney = new LatLng(-34, 151);
            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    final LocationListener myLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {

            final double lat = location.getLatitude();
            final double lon = location.getLongitude();
            System.out.println("LOCATIONNNN::::::::::::::::::::::::::" + lat + "," + lon + "\n" + location.getProvider());
            final LatLng latLng = new LatLng(lat, lon);
            Toast.makeText(mcontext, "" + latLng + "\n" + location.getProvider(), Toast.LENGTH_SHORT).show();
            mMap.addMarker(new MarkerOptions().position(latLng).snippet("Coordinates: " + lat + " " + lon));
            // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16.2f));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20.2f));
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
//                    Toast.makeText(mapTracking.this, ""+latLng, Toast.LENGTH_SHORT).show();
                    LatLng loc = marker.getPosition();
                    double lat1 = loc.latitude;
                    double lng1 = loc.longitude;
                    Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());
                    try {
                        System.out.println(":::::::::::::::::123" + geocoder.getFromLocation(lat1, lng1, 1));
                        Toast.makeText(MapsActivity.this, "lat:" + lat1 + "long: " + lng1, Toast.LENGTH_LONG).show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return false;
                }
            });

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

}