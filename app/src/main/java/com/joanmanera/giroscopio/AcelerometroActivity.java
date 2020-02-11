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
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class AcelerometroActivity extends AppCompatActivity implements SensorEventListener {

    private TextView tvAc, tvRGB;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT > 16) {
            getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_acelerometro);

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
        /*
            Calculo:
        El sensor devuleve 3 valores de -10 a 10 (x, y, z) en forma de float (diferencia de 20f).
        Un color se forma a partir de una mezcla de 3 colores (r, g, b) con valor máximo de 256 cada color.
        'x = r', 'y = g' y 'z = b'.

        Si divido el máximo valor de los colores '256' y la diferencia del valor del sensor '20', nos da 12.8.

        Formula = (valor del sensor + 10) * 12.8

        ¿Por que + 10?
        Como el valor mínimo es -10, si le sumamos 10 nos dara un valor positivo.

        Casos:
        -10 + 10 = 0 , 0 * 12.8 = 0
        0 + 10 = 10 , 10 * 12.8 = 128
        10 + 10 = 20 , 20 * 12.8 = 256

         */

        int r = (int) ((event.values[0] + 10) * 12.8);
        int g = (int) ((event.values[1] + 10) * 12.8);
        int b = (int) ((event.values[2] + 10) * 12.8);
        tvAc.setText(event.values[0] + " / " + event.values[1] + " / " + event.values[2]);
        tvRGB.setText(r + " / " + g + " / " + b);
        iv.setBackgroundColor(Color.rgb( r, g, b));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
