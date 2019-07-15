package com.example.demostarprnt

import com.example.demostarprnt.sharedprf.SharedPrefsApi
import com.example.demostarprnt.sharedprf.SharedPrefsKey
import com.google.gson.Gson
import com.starmicronics.stario.PortInfo

/**
 * --------------------
 * Created by ThuanPx on 7/16/2019.
 * Screen name:
 * --------------------
 */

interface PrinterRepository {
    fun savePrinterInfo(portInfo: PortInfo)

    fun getPrinterInfo(): PortInfo?

}

class PrinterRepositoryImpl(private val sharedPrefsApi: SharedPrefsApi) : PrinterRepository {
    override fun getPrinterInfo(): PortInfo? {
        return sharedPrefsApi.get(SharedPrefsKey.KEY_TOKEN, PortInfo::class.java)
    }

    override fun savePrinterInfo(portInfo: PortInfo) {
        val data = Gson().toJson(portInfo)
        sharedPrefsApi.put(SharedPrefsKey.KEY_TOKEN, data)
    }

}