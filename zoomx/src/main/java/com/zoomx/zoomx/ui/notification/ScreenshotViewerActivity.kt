package com.zoomx.zoomx.ui.notification

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.graphics.BitmapFactory
import android.os.Environment
import android.widget.Toast
import com.zoomx.zoomx.R
import com.zoomx.zoomx.util.copyFile
import java.io.*
import java.util.*
import java.nio.channels.FileChannel


class ScreenshotViewerActivity : AppCompatActivity() {
    lateinit var saveButton: Button
    lateinit var screenshotViewer: ImageView
    lateinit var filePath: String
    lateinit var bitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screenshot_viewer)
        initUi()
        loadScreenShot()
    }

    private fun deleteScreenShotAfterOpeningIt() {
        var file = File(filePath)
        file.delete()
    }

    private fun loadScreenShot() {
        filePath = intent.getStringExtra(ScreenShotActivity.Constants.SCREEN_SHOT_PATH)
        val options = BitmapFactory.Options()
        options.inPreferredConfig = Bitmap.Config.ARGB_8888
        bitmap = BitmapFactory.decodeFile(filePath, options)
        screenshotViewer.setImageBitmap(bitmap)

    }


    private fun initUi() {
        saveButton = findViewById(R.id.zoomx_save_image_button)
        screenshotViewer = findViewById(R.id.screenshot_viewer_imageview)
        saveButton.setOnClickListener { v -> saveImage() }
    }


    private fun saveImage() {
        val root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/ZoomX")
        val myDir = File(root.toString())
        myDir.mkdirs()
        val fname = "myscreen_${Date().time}.png"
        val file = File(myDir, fname)
        copyFile(File(filePath), file)
        Toast.makeText(this@ScreenshotViewerActivity, "screenshot saved at ${file.toString()}" , Toast.LENGTH_LONG).show()
        deleteScreenShotAfterOpeningIt()
        finish()
    }




}