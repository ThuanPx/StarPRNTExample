package com.example.demostarprnt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.crashlytics.android.Crashlytics
import com.example.demostarprnt.Constants.PAPER_SIZE_FOUR_INCH
import com.example.demostarprnt.sharedprf.SharedPrefsApi
import com.example.demostarprnt.sharedprf.SharedPrefsImpl
import com.starmicronics.stario.PortInfo
import com.starmicronics.starioextension.ICommandBuilder
import com.starmicronics.starioextension.StarIoExtManager
import io.fabric.sdk.android.Fabric
import kotlinx.android.synthetic.main.activity_main.*
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import android.os.StrictMode




class MainActivity : AppCompatActivity() {

    private lateinit var starIoExtManager: StarIoExtManager

    private val urltest = "https://homepages.cae.wisc.edu/~ece533/images/airplane.png"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        setContentView(R.layout.activity_main)

        val sharedPrefsApi: SharedPrefsApi = SharedPrefsImpl(this)
        val printerRepository: PrinterRepository = PrinterRepositoryImpl(sharedPrefsApi)
        val printer = printerRepository.getPrinterInfo()

        printer?.let {
            starIoExtManager = StarIoExtManager(StarIoExtManager.Type.Standard, printer.portName, "", 10000, this)
        }


        Fabric.with(this, Crashlytics())

        btSearchPort.setOnClickListener {
            val intent = Intent(this, SearchPortActivity::class.java)
            startActivity(intent)
        }

        btPrinter.setOnClickListener {
            printerRepository.getPrinterInfo()?.let {
                getBitmapFromURL(urltest)?.let {bitmap->
                    Log.i("test",it.toString())
                    print(it,bitmap)
                }
//                Glide.with(this)
//                    .asBitmap()
//                    .load("")
//                    .into(object : CustomTarget<Bitmap>() {
//                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                            Log.i("test",resource.toString())
//                            print(it,resource)
//                        }
//
//                        override fun onLoadCleared(placeholder: Drawable?) {
//                        }
//                    })
            }
        }

        btOpenCashDrawer.setOnClickListener {
            printerRepository.getPrinterInfo()?.let {
                openDrawer(it)
            }
        }
    }

    fun getBitmapFromURL(src: String): Bitmap? {
        return try {
            val url = URL(src)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            null
        }

    }

    private fun print(printerInfo: PortInfo, bitmap: Bitmap) {

        val data: ByteArray

        val emulation = ModelCapability.getEmulation(21)

        val localizeReceipts = ILocalizeReceipts.createLocalizeReceipts(1, PAPER_SIZE_FOUR_INCH)

        data = PrinterFunctions.createTextReceiptData(emulation, localizeReceipts, true, resources, bitmap)

        Communication.sendCommands(this, data, printerInfo.portName, "", 10000, this, mCallback)


    }

    private val mCallback = Communication.SendCallback { _, communicateResult ->
        val msg: String = when (communicateResult) {
            Communication.Result.Success -> "Success!"
            Communication.Result.ErrorOpenPort -> "Fail to openPort"
            Communication.Result.ErrorBeginCheckedBlock -> "Printer is offline (beginCheckedBlock)"
            Communication.Result.ErrorEndCheckedBlock -> "Printer is offline (endCheckedBlock)"
            Communication.Result.ErrorReadPort -> "Read port error (readPort)"
            Communication.Result.ErrorWritePort -> "Write port error (writePort)"
            else -> "Unknown error"
        }

        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    private fun openDrawer(printerInfo: PortInfo) {

        val data: ByteArray

        val emulation = ModelCapability.getEmulation(21)

        data = CashDrawerFunctions.createData(emulation, ICommandBuilder.PeripheralChannel.No1)

        Communication.sendCommands(this, data, printerInfo.portName, "", 10000, this, mCallback)

//        Communication.sendCommands(starIoExtManager, data, starIoExtManager.port, mCallback)
    }

}
