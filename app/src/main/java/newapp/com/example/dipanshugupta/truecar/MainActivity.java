package newapp.com.example.dipanshugupta.truecar;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private Button mail;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private EditText edittext1,edittext2,edittext3;
    double s1,s2;
    String address,city,state,country,postalCode,knownName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //button = (Button) findViewById(R.id.location);
        edittext1 = (EditText) findViewById(R.id.editText);
        edittext2 = (EditText) findViewById(R.id.editText2);
        edittext3 = (EditText) findViewById(R.id.editText3);
        mail = (Button) findViewById(R.id.mail);

        edittext1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(edittext1.getText().length()<1){
                    edittext1.setError("NAME is require");
                }
            }
        });
        edittext2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(edittext2.getText().length()<10){
                    edittext2.setError("Contact no. is Invalid ");
                }
            }
        });
        edittext3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(edittext3.getText().length()<5){
                    edittext3.setError("CAR MODEL is Invalid");
                }
            }
        });

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                s1=location.getLatitude();
                s2=location.getLongitude();

                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(MainActivity.this, Locale.getDefault());

                try {
                    addresses = geocoder.getFromLocation(s1, s2, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    city = addresses.get(0).getLocality();
                    state = addresses.get(0).getAdminArea();
                    country = addresses.get(0).getCountryName();
                    postalCode = addresses.get(0).getPostalCode();
                    knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL

                } catch (IOException e) {
                    e.printStackTrace();
                }





            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent intent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);

            }
        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.INTERNET
            }, 10);
            return;
        }
        }else{
            configureButton();
        }
       configureButton();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
                    configureButton();
                return;
        }
    }

    public void configureButton(){

                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.INTERNET
                        },10);
                    }
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, locationListener);
                //Toast.makeText(MainActivity.this, "\n "+s1+" "+s2, Toast.LENGTH_LONG).show();




    }
    public void process(View view) {
        Intent intent = null, chooser=null;
        edittext1 = (EditText) findViewById(R.id.editText);
        edittext2 = (EditText) findViewById(R.id.editText2);

        if(view.getId()==R.id.mail)
        {

            if(edittext1.getText().length()==0){
                edittext1.setError("NAME is Empty");
                return;
            }

            if(edittext2.getText().length()==0){
                edittext2.setError("Contact no. is Empty ");
                return;
            }
            if(edittext3.getText().length()==0){
                edittext3.setError("CAR MODEL is Empty");
                return;
            }

            SendMail sm = new SendMail(this, "dipudipanshu@gmail.com", "Need Repairing", "\n "+edittext1.getText().toString()+"\n "+edittext2.getText().toString()+"\n "+edittext3.getText().toString()+"\n "+"http://maps.googleapis.com/maps/api/geocode/json?latlng=" + s1 + ","+ s2 + "&sensor=true"+"\n "+address+"\n "+city+"\n "+state+"\n "+country+"\n "+postalCode+"\n "+knownName );

            //Executing sendmail to send email
            sm.execute();
            /*
            intent=new Intent(Intent.ACTION_SEND);
            intent.setData(Uri.parse("mailto:"));
            String[] to={"dnsjadhav@gmail.com"};
            intent.putExtra(Intent.EXTRA_EMAIL, to);
            intent.putExtra(Intent.EXTRA_SUBJECT, "Need repairing");
            intent.putExtra(Intent.EXTRA_TEXT, "\n "+edittext1.getText().toString()+"\n "+edittext2.getText().toString()+"\n "+"http://maps.googleapis.com/maps/api/geocode/json?latlng=" + s1 + ","+ s2 + "&sensor=true");
            intent.setType("message/rfc822");
            chooser=Intent.createChooser(intent, "Send Email");
            startActivity(chooser);*/
        }
    }

}
