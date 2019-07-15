package com.example.demostarprnt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.starmicronics.stario.PortInfo
import kotlinx.android.synthetic.main.item_search_port.view.*

/**
 * --------------------
 * Created by ThuanPx on 7/15/2019.
 * Screen name:
 * --------------------
 */
class SearchPortAdapter(private val itemClick: (PortInfo) -> Unit) : RecyclerView.Adapter<SearchPortAdapter.Companion.ItemViewHolder>() {
    private val listInFo = mutableListOf<PortInfo>()

    fun updateData(list: List<PortInfo>) {
        listInFo.clear()
        listInFo.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_port, parent, false)
        return ItemViewHolder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return listInFo.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.binData(listInFo[position])
    }

    companion object {
        class ItemViewHolder(view: View,private val itemClick: (PortInfo) -> Unit) : RecyclerView.ViewHolder(view) {

            fun binData(item: PortInfo) {
                with(itemView) {
                    modelNameTextView.text = item.modelName
                    portNameTextView.text = item.portName
                    setOnClickListener { itemClick.invoke(item) }
                }
            }

        }
    }
}