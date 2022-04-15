package com.example.logger;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class MainActivity extends AppCompatActivity {

    TextView txt_currentAccel, txt_prevAccel, txt_acceleration;
    ProgressBar prog_shakeMeter;

    // Definiendo el sensor
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private double accelerationCurrentValue;
    private double accelerationPreviousValue;

    private int pointsPlotted = 5;
    private int graphInternalCounter = 0;
    private Viewport viewport;


    LineGraphSeries<DataPoint> xgraph = new LineGraphSeries<DataPoint>(new DataPoint[]{});
    LineGraphSeries<DataPoint> ygraph = new LineGraphSeries<DataPoint>(new DataPoint[]{});
    LineGraphSeries<DataPoint> zgraph = new LineGraphSeries<DataPoint>(new DataPoint[]{});

    private final SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];
            accelerationCurrentValue = Math.sqrt((x * x + y * y + z * z));
            //diferencia entre aceleraciones para saber cuánto se movió
            double changeInAcceleration = Math.abs(accelerationCurrentValue - accelerationPreviousValue);
            accelerationPreviousValue = accelerationCurrentValue;

            //Update text views para ver si son los sensores están funcionando
            txt_currentAccel.setText("Current = " + (int) accelerationCurrentValue);
            txt_prevAccel.setText("Prev = " + (int) accelerationPreviousValue);
            txt_acceleration.setText(" Acceleration Change = " + (int) changeInAcceleration);

            prog_shakeMeter.setProgress((int) changeInAcceleration);

            //change colors based on amount of shaking
            if (changeInAcceleration > 10) {
                txt_acceleration.setBackgroundColor(Color.RED);
            } else if (changeInAcceleration > 5) {
                txt_acceleration.setBackgroundColor(Color.parseColor("#fcad03")); //colore en hex
            } else if (changeInAcceleration > 2) {
                txt_acceleration.setBackgroundColor(Color.YELLOW);
            } else {
                txt_acceleration.setBackgroundColor(getResources().getColor(com.google.android.material.R.color.design_default_color_background));
            }

            //Update Graph
            pointsPlotted++;

            if (pointsPlotted > 1000) {
                pointsPlotted = 1;
                xgraph.resetData(new DataPoint[]{new DataPoint(1, 0)});
                ygraph.resetData(new DataPoint[]{new DataPoint(1, 0)});
                zgraph.resetData(new DataPoint[]{new DataPoint(1, 0)});
            }

            xgraph.appendData(new DataPoint(pointsPlotted, x), true, pointsPlotted);
            ygraph.appendData(new DataPoint(pointsPlotted, y), true, pointsPlotted);
            zgraph.appendData(new DataPoint(pointsPlotted, z), true, pointsPlotted);

            viewport.setMaxX(pointsPlotted);
            viewport.setMinX(pointsPlotted - 100);


        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Conectando las cajas de texto con las variables creadas
        txt_acceleration = findViewById(R.id.txt_accel);
        txt_currentAccel = findViewById(R.id.txt_currentAccel);
        txt_prevAccel = findViewById(R.id.txt_prevAccel);
        prog_shakeMeter = findViewById(R.id.prog_shakeMeter);

        //Inicializando sensor objects
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        GraphView graph = (GraphView) findViewById(R.id.graph);
        viewport = graph.getViewport();
        viewport.setScrollable(true);
        viewport.setXAxisBoundsManual(true);
        xgraph.setColor(Color.RED);
        ygraph.setColor(Color.GREEN);
        zgraph.setColor(Color.BLUE);

        graph.addSeries(xgraph);
        graph.addSeries(ygraph);
        graph.addSeries(zgraph);

    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(sensorEventListener, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(sensorEventListener);
    }
}