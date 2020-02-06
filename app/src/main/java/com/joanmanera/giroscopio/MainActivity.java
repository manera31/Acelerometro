package com.joanmanera.giroscopio;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView tvAc, tvRGB;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvAc = findViewById(R.id.tvValorAcelerometro);
        tvRGB = findViewById(R.id.tvValorRGB);
        iv = findViewById(R.id.imageView);

        SensorManager sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        Sensor giroscopio = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, giroscopio, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onSensorChanged(SensorEvent event) {

        int r = (int) (event.values[0] * 25.6);
        int g = (int) (event.values[1] * 25.6);
        int b = (int) (event.values[2] * 25.6);
        tvAc.setText(event.values[0] + " - " + event.values[1] + " - " + event.values[2]);
        tvRGB.setText(r + " - " + g + " - " + b);
        iv.setBackgroundColor(Color.rgb(Math.abs(r), Math.abs(g),Math.abs(b)));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
