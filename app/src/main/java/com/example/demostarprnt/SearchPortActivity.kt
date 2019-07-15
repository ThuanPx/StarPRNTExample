package com.example.demostarprnt

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.Formatter
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.crashlytics.android.Crashlytics
import com.example.demostarprnt.sharedprf.SharedPrefsApi
import com.example.demostarprnt.sharedprf.SharedPrefsImpl
import com.starmicronics.stario.PortInfo
import com.starmicronics.stario.StarIOPort
import com.starmicronics.stario.StarIOPortException
import io.fabric.sdk.android.Fabric
import kotlinx.android.synthetic.main.activity_search_point.*
import pub.devrel.easypermissions.EasyPermissions
import java.io.IOException
import java.util.ArrayList

class SearchPortActivity : AppCompatActivity() {

    private lateinit var adapter: SearchPortAdapter

    private lateinit var bluetooth: BluetoothAdapter

    private val REQUEST_ENABLE_BT = 201
    private var dialogFragment: BluetoothDeviceDialogFragment? = null
    private var bluetoothDevice: BluetoothDevice? = null

    private var bondingProgressDialog: ProgressDialog? = null

    private val task = SearchTask(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_point)
        Fabric.with(this, Crashlytics())
        methodRequiresTwoPermission()

        bluetooth = BluetoothAdapter.getDefaultAdapter()

        if (!bluetooth.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
        } else {
            val filter = IntentFilter()
            filter.addAction(BluetoothDevice.ACTION_FOUND)
            filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
            filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
            filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED)
            registerReceiver(receiver, filter)
        }

        val sharedPrefsApi: SharedPrefsApi = SharedPrefsImpl(this)
        val printerRepository: PrinterRepository = PrinterRepositoryImpl(sharedPrefsApi)

        adapter = SearchPortAdapter {
            printerRepository.savePrinterInfo(it)
            Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show()
        }

        rvList.layoutManager = LinearLayoutManager(this)
        rvList.adapter = this.adapter

        btSearchPort.setOnClickListener {
            loading.visibility = View.VISIBLE
            SearchTask(this).execute(Constants.IF_TYPE_USB)
        }

        btSearchPortBLE.setOnClickListener {
            if (bluetooth.isDiscovering)
                bluetooth.cancelDiscovery()
            loading.visibility = View.VISIBLE
            bluetooth.startDiscovery()

        }
    }

    // Create a BroadcastReceiver for ACTION_FOUND.
    private val receiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                BluetoothDevice.ACTION_FOUND -> {

                    // Discovery has found a device. Get the BluetoothDevice
                    // object and its info from the Intent.
                    val device: BluetoothDevice =
                            intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    val deviceName = device.name
                    val deviceHardwareAddress = device.address ?: "" // MAC address

                    if (deviceName.isNullOrBlank()) return

                    if (dialogFragment != null) {
                        dialogFragment?.updateData(device)
                        return
                    }
                    dialogFragment = BluetoothDeviceDialogFragment.newInstance(object : BluetoothDeviceDialogFragment.BluetoothDeviceDialogFragmentListener {
                        override fun onItemClick(item: BluetoothDevice) {
                            bluetoothDevice = item
//                            unregisterReceiver(receiver)
                            bluetooth.cancelDiscovery()

                            if (bluetooth.bondedDevices.contains(bluetoothDevice)) {
                                searchPortBluetooth()
                                return
                            }

                            val isSuccess = bluetoothDevice?.createBond()

                            if (isSuccess == true) {
                                // The pairing has started, shows a progress dialog.
                                Log.d(TAG, "Showing pairing dialog")
                                bondingProgressDialog = ProgressDialog.show(context, "", "Pairing with device ${item.name}...", true, false)
                            } else {
                                Log.d(TAG, "Error while pairing with device ${item.name}!")
                                Toast.makeText(context, "Error while pairing with device ${item.name}!", Toast.LENGTH_SHORT).show()
                            }


                        }
                    })
                    dialogFragment?.show(supportFragmentManager, BluetoothDeviceDialogFragment::class.java.simpleName)
                    dialogFragment?.updateData(device)
                }
                BluetoothDevice.ACTION_BOND_STATE_CHANGED -> {
                    // Pairing state has changed.
                    Log.d(TAG, "Bluetooth bonding state changed.")
                    // progress pair
                    if (bluetoothDevice != null) {
                        when (bluetoothDevice?.bondState) {
                            BluetoothDevice.BOND_BONDING -> {
                                // Still pairing, do nothing.
                            }
                            BluetoothDevice.BOND_BONDED -> {
                                // Successfully paired.
                                bondingProgressDialog?.dismiss()
                                searchPortBluetooth()
                            }
                            BluetoothDevice.BOND_NONE -> {
                                // Failed pairing.
                            }
                        }
                    }
                }
                BluetoothAdapter.ACTION_STATE_CHANGED -> {
                    // Discovery state changed.
                    Log.d(TAG, "Bluetooth state changed.")
                }
                BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
                    // Discovery has ended.
                    loading.visibility = View.INVISIBLE
                    Log.d(TAG, "Discovery ended.")
                }
            }
        }
    }

    private fun searchPortBluetooth() {
        loading.visibility = View.VISIBLE
        Toast.makeText(this, "This device is already paired!", Toast.LENGTH_SHORT).show()
        dialogFragment?.dismiss()
        SearchTask(this).execute(Constants.IF_TYPE_BLUETOOTH)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
            registerReceiver(receiver, filter)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }


    private fun methodRequiresTwoPermission() {
        val perms = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION)
        if (EasyPermissions.hasPermissions(this, *perms)) {
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(
                    this, "Request Permission",
                    123, Manifest.permission.ACCESS_COARSE_LOCATION
            )
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }


    /**
     * Printer search task.
     */
    @SuppressLint("StaticFieldLeak")
    private inner class SearchTask internal constructor(private val context: Context) :
            AsyncTask<String, Void, Void>() {
        private var mPortList: List<PortInfo>? = null

        override fun doInBackground(vararg interfaceType: String): Void? {
            mPortList = try {
                StarIOPort.searchPrinter(interfaceType[0], context)
            } catch (e: StarIOPortException) {
                ArrayList()
            }

            return null
        }

        override fun onPostExecute(doNotUse: Void?) {
            loading.visibility = View.GONE
            mPortList?.let {
                adapter.updateData(it)
            }
        }
    }

    companion object {
        private const val TAG = "SearchPortActivity"
    }


//    @SuppressLint("CheckResult")
//    private fun searchPort() {
//        searchPortSingle()
//            .doOnSubscribe { loading.visibility = View.VISIBLE }
//            .doAfterTerminate { loading.visibility = View.GONE }
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                adapter.updateData(it)
//                loading.visibility = View.GONE
//                Toast.makeText(this,it.toString(),Toast.LENGTH_SHORT).show()
//            }, {
//                Toast.makeText(this,"error search Port 2 "+  it.message,Toast.LENGTH_SHORT).show()
//                Log.i("error search Port 3", it.message)
//            })
//    }
//
//    private fun searchPortSingle(): Single<List<PortInfo>> {
//        return Single.create { emitter ->
//            try {
//                StarIOPort.searchPrinter(Constants.TYPE_USB, this)
//            } catch (e: StarIOPortException) {
//                e.printStackTrace()
//                emitter.onError(Throwable("search port fail 1 "))
//            }
//        }
//    }

}

