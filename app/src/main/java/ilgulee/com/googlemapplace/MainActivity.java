package ilgulee.com.googlemapplace;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final int ERROR_DIALOG_REQUEST=9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(isGoogleServicesOK()){
            moveToMapActivity(findViewById(R.id.button));
        }
    }

    public boolean isGoogleServicesOK(){
        Log.d(TAG, "isGoogleServicesOK: checking google services version");
        int available= GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        if(available== ConnectionResult.SUCCESS){
            Log.d(TAG, "isGoogleServicesOK: Google Play services is OK");
            return true;
        }else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Log.d(TAG, "isGoogleServicesOK: Error occured but we can fix it");
            Dialog dialog=GoogleApiAvailability.getInstance().getErrorDialog(this,available,ERROR_DIALOG_REQUEST);
            dialog.show();
            return false;
        }else{
            Toast.makeText(this,"You cant't make map request!",Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    private void moveToMapActivity(View view){
        Log.d(TAG, "moveToMapActivity: ");
        startActivity(new Intent(this,MapActivity.class));
    }
}
