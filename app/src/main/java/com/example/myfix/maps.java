package com.example.myfix;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfix.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class maps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        Button lokasiSaya = (Button) findViewById(R.id.btnLokasiSaya);
        lokasiSaya.setOnClickListener(op);

    }


    View.OnClickListener op = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnLokasiSaya:
                    sembunyikanKeyBoard(view);
                    lokasiSaya();
                    break;
            }
        }
    };



    private void sembunyikanKeyBoard(View v){
        InputMethodManager a = (InputMethodManager)
                getSystemService(INPUT_METHOD_SERVICE);
        a.hideSoftInputFromWindow(v.getWindowToken(),0);
    }

    //marker tempat dan mindahin kamera
    private void gotoPeta(Double lat, Double lng, float z){
        LatLng Lokasibaru = new LatLng(lat,lng);
        mMap.addMarker(new MarkerOptions().
                position(Lokasibaru).
                title("Marker in " +lat +":" +lng));
        mMap.moveCamera(CameraUpdateFactory.
                newLatLngZoom(Lokasibaru,z));
    }

    //nyari lokasi saya
    private void lokasiSaya(){
        LocationManager mylocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener mylocationListener = new lokasiListener();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

            mylocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 100, mylocationListener);
    }

    //perubahan lokasi
    private class lokasiListener implements LocationListener {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(maps.this, Locale.getDefault());
            try {
                addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                String ambilcountry = addresses.get(0).getCountryName();
                String ambilstate = addresses.get(0).getAdminArea();
                String ambilcity = addresses.get(0).getSubAdminArea();
                String ambilkecamatan = addresses.get(0).getLocality();


//                TextView txtLat = (TextView) findViewById(R.id.editLatitude);
//                TextView txtLong = (TextView) findViewById(R.id.editLongitude);
                EditText zoom = findViewById(R.id.editZoom);

                Float dblzoom = Float.parseFloat(zoom.getText().toString());

                String Lat = String.valueOf(location.getLatitude());
                String Long = String.valueOf(location.getLongitude());

                Double floatLat = Double.parseDouble(Lat);
                Double floatLong = Double.parseDouble(Long);

//                txtLat.setText(Lat);
//                txtLong.setText(Long);

                Toast.makeText(getBaseContext(),
                        "GPS Capture " + ambilkecamatan + ", "
                                + ambilcity + ", " + ambilstate + ", "
                                + ambilcountry
                        , Toast.LENGTH_SHORT).show();

                gotoPeta(floatLat, floatLong, dblzoom);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng ITS = new LatLng(-7.28,112.79);
        mMap.addMarker(new MarkerOptions().position(ITS).
                title("Marker in ITS"));
        LatLng Booth1 = new LatLng(-7.288,112.79);
        mMap.addMarker(new MarkerOptions().position(Booth1).title("Marker in booth 1"));
        LatLng Booth2 = new LatLng(-7.28,112.795);
        mMap.addMarker(new MarkerOptions().position(Booth2).title("Marker in booth 1"));
        LatLng Booth3 = new LatLng(-7.283,112.79);
        mMap.addMarker(new MarkerOptions().position(Booth3).title("Marker in booth 1"));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ITS,11));
    }
}