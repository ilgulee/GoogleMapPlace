package ilgulee.com.googlemapplace;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "MapActivity";
    public static final int REQUEST_CODE = 1234;

    private GoogleMap mMap;
    private static final String FINE_LOCATION= Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION=Manifest.permission.ACCESS_COARSE_LOCATION;
    private Boolean mLocationPermissionGranted=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Log.d(TAG, "onCreate: in MapActivity");
        getLocationPermission();
    }

    private void initMap() {
        Log.d(TAG, "initMap: starts");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: starts");
        String[] explicitPermissionCheck={FINE_LOCATION,COURSE_LOCATION};
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),COURSE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                mLocationPermissionGranted=true;
            }else{
                ActivityCompat.requestPermissions(this,explicitPermissionCheck,REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,explicitPermissionCheck,REQUEST_CODE);
        }
        Log.d(TAG, "getLocationPermission: ends");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: starts");
        mLocationPermissionGranted=false;
        switch(requestCode){
            case REQUEST_CODE:{
                if(grantResults.length>0){
                    for(int i=0;i<grantResults.length;i++){
                        if(grantResults[i]!=PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionGranted=false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    mLocationPermissionGranted=true;
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    //initialize our map
                    initMap();
                    Log.d(TAG, "onRequestPermissionsResult: initMap starts");
                }
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady: starts");
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        Log.d(TAG, "onMapReady: ends");
    }
}
