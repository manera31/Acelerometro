package com.joanmanera.giroscopio;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity implements LocalizacionAdapter.ILocalizacionListener {

    public static final int REQUEST_ACCESS_COURSE_LOCATION = 1;
    public static final int REQUEST_ACCESS_FINE_LOCATION = 2;
    private FusedLocationProviderClient fusedLocationClient;
    private EditText edNombreNuevaUbicacion;
    private RecyclerView recyclerView;
    private LocalizacionAdapter adapter;
    private ArrayList<Localizacion> localizaciones;
    private Button bAdd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT > 16) {
            getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_map);

        boolean permiso = true;

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            permiso = false;
            ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  },
                    REQUEST_ACCESS_COURSE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            permiso = false;
            ActivityCompat.requestPermissions( this, new String[] {  Manifest.permission.ACCESS_FINE_LOCATION  },
                    REQUEST_ACCESS_FINE_LOCATION);
        }
        if (permiso){

            localizaciones = new ArrayList<>();
            localizaciones.add(new Localizacion("Mi casa Callosa",  -0.121785,38.653444));
            localizaciones.add(new Localizacion("Mi casa Javea",  0.191587,38.769988));
            localizaciones.add(new Localizacion("Instituto",  0.178541,38.788688));

            recyclerView = findViewById(R.id.recyclerView);
            adapter = new LocalizacionAdapter(localizaciones, this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

            bAdd = findViewById(R.id.bGuardarUbi);
            edNombreNuevaUbicacion = findViewById(R.id.etNombreUbicacion);
            bAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null && !edNombreNuevaUbicacion.getText().equals("")){
                                localizaciones.add(new Localizacion(edNombreNuevaUbicacion.getText().toString(), location.getLongitude(), location.getLatitude()));
                                edNombreNuevaUbicacion.setText("");
                                adapter.setLocalizaciones(localizaciones);
                            }
                        }
                    });
                }
            });
        }
    }

    @Override
    public void onItemSelected(final int pos) {
        fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null && !edNombreNuevaUbicacion.getText().equals("")){
                    showDirections(localizaciones.get(pos).getLatitud(), localizaciones.get(pos).getLongitud(), location.getLatitude() ,location.getLongitude());
                }
            }
        });

    }

    public void showDirections(double lat, double lng, double lat1, double lng1) {

        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?" +
                "saddr=" + lat + "," + lng + "&daddr=" + lat1 + "," +
                lng1));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        startActivity(intent);

    }
}
