package com.kee.helloworld.sms.util

import android.content.Context
import java.io.IOException

object SmsExt {


    @Throws(IOException::class)
    fun readAssetBytes(context: Context, fileName: String): ByteArray? {
        return try {
            context.assets.open(fileName).use { input ->
                val size = input.available()
                ByteArray(size).also { input.read(it) }
            }
        } catch (e: Exception) {
            null
        }
    }


    fun xorBytes(data: ByteArray, key: ByteArray): ByteArray {
        val result = ByteArray(data.size)
        for (i in data.indices) {
            result[i] = (data[i].toInt() xor key[i % key.size].toInt()).toByte()
        }
        return result
    }


}