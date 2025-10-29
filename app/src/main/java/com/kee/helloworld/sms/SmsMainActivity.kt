package com.kee.helloworld.sms

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.kee.helloworld.sms.util.SmsExt
import java.util.Locale

class SmsMainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }



    fun readFile(){
        val key = "yfkelxwuol".toByteArray()

        val hashSet4: HashSet<String>? = SmsExt.readAssetBytes(this, "kzggylofag.bin")?.let {
            val decryptedBytes = SmsExt.xorBytes(it, key)
            val content = String(decryptedBytes, Charsets.UTF_8)
            HashSet(content.lowercase(Locale.getDefault()).split("\n"))
        }

        val hashSet5: HashSet<String>? = SmsExt.readAssetBytes(this, "lydcnwifjw.bin")?.let {
            val decryptedBytes = SmsExt.xorBytes(it, key)
            val content = String(decryptedBytes, Charsets.UTF_8)
            HashSet(content.lowercase(Locale.getDefault()).split("\n"))
        }

        val hashSet6: HashSet<String>? = SmsExt.readAssetBytes(this, "vbmjwotkev.bin")?.let {
            val decryptedBytes = SmsExt.xorBytes(it, key)
            val content = String(decryptedBytes, Charsets.UTF_8)
            HashSet(content.lowercase(Locale.getDefault()).split("\n"))
        }

        val hashSet7: HashSet<String>? = SmsExt.readAssetBytes(this, "esvsdajzzv.bin")?.let {
            val decryptedBytes = SmsExt.xorBytes(it, key)
            val content = String(decryptedBytes, Charsets.UTF_8)
            HashSet(content.lowercase(Locale.getDefault()).split("\n"))
        }

        val hashSet8: HashSet<String>? = SmsExt.readAssetBytes(this, "uwirjyqvof.bin")?.let {
            val decryptedBytes = SmsExt.xorBytes(it, key)
            val content = String(decryptedBytes, Charsets.UTF_8)
            HashSet(content.lowercase(Locale.getDefault()).split("\n"))
        }

        Log.e("xxxxx","=====>$hashSet4")
        Log.e("xxxxx","=====>$hashSet5")
        Log.e("xxxxx","=====>$hashSet6")
        Log.e("xxxxx","=====>$hashSet7")
        Log.e("xxxxx","=====>$hashSet8")
    }

}