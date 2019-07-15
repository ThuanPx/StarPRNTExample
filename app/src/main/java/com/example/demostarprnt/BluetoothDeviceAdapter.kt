package com.example.demostarprnt

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_bluetooth_device.view.*

/**
 * --------------------
 * Created by ThuanPx on 7/24/2019.
 * Screen name:
 * --------------------
 */
class BluetoothDeviceAdapter(private val listener: (BluetoothDevice) -> Unit) : RecyclerView.Adapter<BluetoothDeviceAdapter.Companion.ItemViewHolder>() {

    private val list = mutableListOf<BluetoothDevice>()

    fun updateData(item: BluetoothDevice) {
        if (list.contains(item)) return
        list.add(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bluetooth_device, parent, false)
        return ItemViewHolder(view, listener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(list[position])
    }

    companion object {
        class ItemViewHolder(view: View, private val listener: (BluetoothDevice) -> Unit) : RecyclerView.ViewHolder(view) {
            @SuppressLint("SetTextI18n")
            fun bind(item: BluetoothDevice) {
                with(itemView) {
                    tvMac.text = item.name + "---" + item.address
                    setOnClickListener { listener.invoke(item) }
                }
            }
        }
    }
}