package com.zoomx.zoomx.util

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

/**
 * Created by Ahmed Fathallah on 2/6/2018.
 */

class ShakeEventManager(private val context: Context) : SensorEventListener {

    private var mSensorManager: SensorManager? = null
    private var mAccelerometer: Sensor? = null
    private var mOnShakeEventListener: OnShakeEventListener? = null
    private var lastUpdateTime: Long = 0

    fun listen(onShakeEventListener: OnShakeEventListener) {
        mSensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        if (!checkIfAccelerometerExist()) {
            throw IllegalStateException("TYPE_ACCELEROMETER sensor is not available on this device")
        }
        this.mOnShakeEventListener = onShakeEventListener
        mAccelerometer = mSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        mSensorManager!!.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        lastUpdateTime = System.currentTimeMillis()
    }

    private fun checkIfAccelerometerExist(): Boolean {
        val listOfSensorsOnDevice = mSensorManager!!.getSensorList(Sensor.TYPE_ALL)
        for (i in listOfSensorsOnDevice.indices) {
            if (listOfSensorsOnDevice[i].type == Sensor.TYPE_ACCELEROMETER) {
                return true
            }
        }
        return false
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            val values = event.values
            val x = values[0]
            val y = values[1]
            val z = values[2]
            val accelerationSqt = (x * x + y * y + z * z) / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH)
            val actualTime = event.timestamp
            if (accelerationSqt >= 2) {
                if (actualTime - lastUpdateTime < 200) {
                    return
                }
                lastUpdateTime = actualTime
                if (mOnShakeEventListener != null) {
                    mOnShakeEventListener!!.OnShakeDetected()
                }
            }
        }

    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {

    }

    fun release() {
        mOnShakeEventListener = null
        mSensorManager!!.unregisterListener(this)
        //        mSensorManager = null;
        //        mAccelerometer = null;
    }

    interface OnShakeEventListener {
        fun OnShakeDetected()
    }
}
