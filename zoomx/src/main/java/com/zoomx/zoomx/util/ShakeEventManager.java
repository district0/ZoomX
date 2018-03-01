package com.zoomx.zoomx.util;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.List;

/**
 * Created by Ahmed Fathallah on 2/6/2018.
 */

public class ShakeEventManager implements SensorEventListener {

    private SensorManager mSensorManager;
    private Context context;
    private Sensor mAccelerometer;
    private OnShakeEventListener mOnShakeEventListener;
    private long lastUpdateTime;

    public ShakeEventManager(Context context) {
        this.context = context;
    }

    public void listen(OnShakeEventListener onShakeEventListener) {
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if (!checkIfAccelerometerExist()) {
            throw new IllegalStateException("TYPE_ACCELEROMETER sensor is not available on this device");
        }
        this.mOnShakeEventListener = onShakeEventListener;
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        lastUpdateTime = System.currentTimeMillis();
    }

    private boolean checkIfAccelerometerExist() {
        List<Sensor> listOfSensorsOnDevice = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        for (int i = 0; i < listOfSensorsOnDevice.size(); i++) {
            if (listOfSensorsOnDevice.get(i).getType() == Sensor.TYPE_ACCELEROMETER) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float[] values = event.values;
            float x = values[0];
            float y = values[1];
            float z = values[2];
            float accelerationSqt = (x * x + y * y + z * z)
                    / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
            long actualTime = event.timestamp;
            if (accelerationSqt >= 2) {
                if (actualTime - lastUpdateTime < 200) {
                    return;
                }
                lastUpdateTime = actualTime;
                if (mOnShakeEventListener != null) {
                    mOnShakeEventListener.OnShakeDetected();
                }
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void release() {
        mOnShakeEventListener = null;
        mSensorManager.unregisterListener(this);
//        mSensorManager = null;
//        mAccelerometer = null;
    }

    public interface OnShakeEventListener {
        void OnShakeDetected();
    }
}
