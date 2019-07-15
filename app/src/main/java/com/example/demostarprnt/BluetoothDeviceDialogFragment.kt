package com.example.demostarprnt

import android.bluetooth.BluetoothDevice
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.dialog_fragment_bluetooth_device.*

/**
 * --------------------
 * Created by ThuanPx on 7/24/2019.
 * Screen name:
 * --------------------
 */
class BluetoothDeviceDialogFragment : DialogFragment() {

    private var listener: BluetoothDeviceDialogFragmentListener? = null

    private val adapter by lazy {
        BluetoothDeviceAdapter {
            listener?.onItemClick(it)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_fragment_bluetooth_device, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvList.layoutManager = LinearLayoutManager(context)
        rvList.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        val dlg = dialog
        dlg?.let {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dlg.window?.setLayout(width, height)
        }
    }

    fun updateData(item: BluetoothDevice) {
        adapter.updateData(item)
    }

    companion object {
        fun newInstance(listener: BluetoothDeviceDialogFragmentListener) = BluetoothDeviceDialogFragment().apply {
            this.listener = listener
        }
    }

    interface BluetoothDeviceDialogFragmentListener {
        fun onItemClick(item: BluetoothDevice)
    }
}