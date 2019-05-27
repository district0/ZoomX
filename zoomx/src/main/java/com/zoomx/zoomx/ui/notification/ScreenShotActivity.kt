package com.zoomx.zoomx.ui.notification

import android.support.v7.app.AppCompatActivity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.PixelFormat
import android.graphics.Point
import android.hardware.display.DisplayManager
import android.hardware.display.VirtualDisplay
import android.media.Image
import android.media.ImageReader
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.os.*
import android.support.annotation.RequiresApi
import android.util.Log
import android.view.Display
import android.view.OrientationEventListener
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class ScreenShotActivity : AppCompatActivity() {

    object Constants {
        const val SCREEN_SHOT_PATH = "file_path"
    }


    private val TAG = ScreenShotActivity::class.java.name
    private val REQUEST_CODE = 100
    private var STORE_DIRECTORY: String? = null
    private var IMAGES_PRODUCED: Int = 0
    private val SCREENCAP_NAME = "screencap"
    private val VIRTUAL_DISPLAY_FLAGS = DisplayManager.VIRTUAL_DISPLAY_FLAG_OWN_CONTENT_ONLY or DisplayManager.VIRTUAL_DISPLAY_FLAG_PUBLIC
    private var sMediaProjection: MediaProjection? = null

    private var mProjectionManager: MediaProjectionManager? = null
    private var mImageReader: ImageReader? = null
    private var mHandler: Handler? = null
    private var mDisplay: Display? = null
    private var mVirtualDisplay: VirtualDisplay? = null
    private var mDensity: Int = 0
    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private var mRotation: Int = 0
    private var mOrientationChangeCallback: OrientationChangeCallback? = null
    var consumed = false
    var file: File? = null

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private inner class ImageAvailableListener : ImageReader.OnImageAvailableListener {
        @RequiresApi(Build.VERSION_CODES.KITKAT)
        override fun onImageAvailable(reader: ImageReader) {
            if (!consumed) {
                consumed = true
                var image: Image? = null
                var fos: FileOutputStream? = null
                var bitmap: Bitmap? = null

                try {
                    image = reader.acquireLatestImage()
                    if (image != null) {
                        val planes = image.planes
                        val buffer = planes[0].buffer
                        val pixelStride = planes[0].pixelStride
                        val rowStride = planes[0].rowStride
                        val rowPadding = rowStride - pixelStride * mWidth
                        // create bitmap
                        bitmap = Bitmap.createBitmap(mWidth + rowPadding / pixelStride, mHeight, Bitmap.Config.ARGB_8888)
                        bitmap!!.copyPixelsFromBuffer(buffer)
                        // write bitmap to a file
                        file = File("$STORE_DIRECTORY/myscreen_$IMAGES_PRODUCED ${Date().time}.png")
                        fos = FileOutputStream(file)
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                        IMAGES_PRODUCED++
                        Log.e(TAG, "captured image: $IMAGES_PRODUCED")
                        stopProjection()

                        var intent = Intent(this@ScreenShotActivity, ScreenshotViewerActivity::class.java)
                        intent.putExtra(Constants.SCREEN_SHOT_PATH, file?.path)
                        startActivity(intent)

                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    if (fos != null) {
                        try {
                            fos.close()
                        } catch (ioe: IOException) {
                            ioe.printStackTrace()
                        }

                    }
                    bitmap?.recycle()
                    image?.close()
                }
            }
        }
    }


    private inner class OrientationChangeCallback internal constructor(context: Context) : OrientationEventListener(context) {

        override fun onOrientationChanged(orientation: Int) {
            val rotation = mDisplay!!.rotation
            if (rotation != mRotation) {
                mRotation = rotation
                try {
                    // clean up
                    if (mVirtualDisplay != null) mVirtualDisplay!!.release()
                    if (mImageReader != null) mImageReader!!.setOnImageAvailableListener(null, null)

                    // re-create virtual display depending on device width / height
                    createVirtualDisplay()
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
    }

    private inner class MediaProjectionStopCallback : MediaProjection.Callback() {
        override fun onStop() {
            mHandler!!.post {
                if (mVirtualDisplay != null) mVirtualDisplay!!.release()
                if (mImageReader != null) mImageReader!!.setOnImageAvailableListener(null, null)
                if (mOrientationChangeCallback != null) mOrientationChangeCallback!!.disable()
                sMediaProjection!!.unregisterCallback(this@MediaProjectionStopCallback)
            }
        }
    }

    override fun onResume() {
        try {
            Thread.sleep(1000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        finish()
        super.onResume()
    }

    /****************************************** Activity Lifecycle methods  */

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //        setContentView(R.layout.activity_main);

        // call for the projection manager
        mProjectionManager = getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager

        startProjection()
        // start capture handling thread
        object : Thread() {
            override fun run() {
                Looper.prepare()
                mHandler = Handler()
                Looper.loop()
            }
        }.start()
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == REQUEST_CODE) {
            sMediaProjection = mProjectionManager!!.getMediaProjection(resultCode, data)

            if (sMediaProjection != null) {

                val externalFilesDir = getExternalFilesDir(null)
//                val externalFilesDir = Environment.getExternalStorageDirectory();

                if (externalFilesDir != null) {
                    STORE_DIRECTORY = externalFilesDir.absolutePath + "/screenshots/"
                    val storeDirectory = File(STORE_DIRECTORY!!)
                    if (!storeDirectory.exists()) {
                        val success = storeDirectory.mkdirs()
                        if (!success) {
                            return
                        }
                    }
                } else {
                    return
                }

                // display metrics
                val metrics = resources.displayMetrics
                mDensity = metrics.densityDpi
                mDisplay = windowManager.defaultDisplay

                // create virtual display depending on device width / height
                createVirtualDisplay()

                // register orientation change callback
                mOrientationChangeCallback = OrientationChangeCallback(this)
                if (mOrientationChangeCallback!!.canDetectOrientation()) {
                    mOrientationChangeCallback!!.enable()
                }

                // register media projection stop callback
                sMediaProjection!!.registerCallback(MediaProjectionStopCallback(), mHandler)
            }
        }
    }

    /****************************************** UI Widget Callbacks  */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun startProjection() {
        startActivityForResult(mProjectionManager!!.createScreenCaptureIntent(), REQUEST_CODE)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun stopProjection() {
        mHandler!!.post {
            if (sMediaProjection != null) {
                sMediaProjection!!.stop()
            }
        }
    }

    /****************************************** Factoring Virtual Display creation  */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun createVirtualDisplay() {
        // get width and height
        val size = Point()
        mDisplay!!.getSize(size)
        mWidth = size.x
        mHeight = size.y

        // start capture reader
        mImageReader = ImageReader.newInstance(mWidth, mHeight, PixelFormat.RGBA_8888, 2)
        mVirtualDisplay = sMediaProjection!!.createVirtualDisplay(SCREENCAP_NAME, mWidth, mHeight, mDensity, VIRTUAL_DISPLAY_FLAGS, mImageReader!!.surface, null, mHandler)
        mImageReader!!.setOnImageAvailableListener(ImageAvailableListener(), mHandler)

    }
}
