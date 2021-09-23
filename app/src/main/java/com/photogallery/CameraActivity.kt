package com.photogallery
import android.Manifest
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.photogallery.data.DatabaseBuilder
import com.photogallery.data.DatabaseHelperImplementation
import com.photogallery.data.Entity.PhotoEntity
import com.photogallery.databinding.ActivityCameraBinding
import com.photogallery.presenter.CameraActivityPresenter
import com.photogallery.presenter.MainActivityPresenter
import com.photogallery.view.CameraActivityView
import kotlinx.coroutines.*
import java.io.File

class CameraActivity : MvpAppCompatActivity(), CameraActivityView {

    var CAMERA_PERMISSION = Manifest.permission.CAMERA
    var COORDINATES_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION
    var WRITE_EXTERNAR = Manifest.permission.WRITE_EXTERNAL_STORAGE
    var READ_EXTERNAL = Manifest.permission.READ_EXTERNAL_STORAGE
    var ACCES_COARSE = Manifest.permission.ACCESS_COARSE_LOCATION

    var RC_PERMISSION = 101

    private lateinit var binding: ActivityCameraBinding

    var locationString : String = ""

    var fusedLocationClient: FusedLocationProviderClient? = null

    var currentLongitude : Double = 0.0
    var currentLatitude : Double = 0.0

    @InjectPresenter
    lateinit var cameraPresenter : CameraActivityPresenter

    @ProvidePresenter
    fun provideCameraActivityPresenter() : CameraActivityPresenter {
        return CameraActivityPresenter(dbHelper = DatabaseHelperImplementation(DatabaseBuilder.getInstance(applicationContext)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val recordFiles = ContextCompat.getExternalFilesDirs(this, Environment.DIRECTORY_MOVIES)
        val storageDirectory = recordFiles[0]


        if (checkPermissions()) startCameraSession() else requestPermissions()

        binding.backButton.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })

        binding.captureImage.setOnClickListener {
            val name = System.currentTimeMillis().toString()
            val imageCaptureFilePath = "${storageDirectory.absoluteFile}/${name}" + "_image.jpg"
            captureImage(imageCaptureFilePath, name)
        }
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this, arrayOf(CAMERA_PERMISSION, COORDINATES_PERMISSION, WRITE_EXTERNAR, READ_EXTERNAL), RC_PERMISSION)
    }

    private fun checkPermissions(): Boolean {
        return ((ActivityCompat.checkSelfPermission(this, CAMERA_PERMISSION)) == PackageManager.PERMISSION_GRANTED
                && (ActivityCompat.checkSelfPermission(this, COORDINATES_PERMISSION)) == PackageManager.PERMISSION_GRANTED
                && (ActivityCompat.checkSelfPermission(this, READ_EXTERNAL)) == PackageManager.PERMISSION_GRANTED
                && (ActivityCompat.checkSelfPermission(this, WRITE_EXTERNAR)) == PackageManager.PERMISSION_GRANTED)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            RC_PERMISSION -> {

                var allPermissionsGranted = false
                for (result in grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {

                        allPermissionsGranted = false
                        break
                    } else {
                        allPermissionsGranted = true
                    }
                }
                if (allPermissionsGranted) startCameraSession() else permissionsNotGranted()
            }
        }
    }

    private fun startCameraSession() {
        fusedLocationClient?.lastLocation?.
        addOnSuccessListener(this,
            { location: Location? ->

                if (location == null) {

                } else location.apply {
                    currentLatitude = location.latitude
                    currentLongitude = location.longitude
                    Log.e("ololo", location.toString())
                }
            })
        binding.cameraView.bindToLifecycle(this)
    }

    private fun permissionsNotGranted() {
        AlertDialog.Builder(this).setTitle("Permissions required")
            .setMessage("These permissions are required to use this app. Please allow Camera and Audio permissions first")
            .setCancelable(false)
            .setPositiveButton("Grant") { dialog, which -> requestPermissions() }
            .show()
    }


    private fun captureImage(imageCaptureFilePath: String, name :String) {
        binding.cameraView.takePicture(File(imageCaptureFilePath), ContextCompat.getMainExecutor(this), object: ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                Toast.makeText(this@CameraActivity, "Image Captured", Toast.LENGTH_SHORT).show()
                cameraPresenter.addPhoto(PhotoEntity(name = name, created_time = name.toLong(), longitude = currentLongitude,
                    latitude = currentLatitude,
                    root = imageCaptureFilePath))


            }

            override fun onError(exception: ImageCaptureException) {
                Toast.makeText(this@CameraActivity, "Image Capture Failed", Toast.LENGTH_SHORT).show()

            }
        })
    }


    override fun addPhoto(model: PhotoEntity) {

    }


}