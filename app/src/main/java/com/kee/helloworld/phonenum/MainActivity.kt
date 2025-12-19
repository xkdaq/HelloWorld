package com.kee.helloworld.phonenum

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.activity.ComponentActivity
//import com.google.android.gms.common.ConnectionResult
//import com.google.android.gms.common.GoogleApiAvailability


class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val apiAvailability = GoogleApiAvailability.getInstance()
//        val result = apiAvailability.isGooglePlayServicesAvailable(this)
//        if (result != ConnectionResult.SUCCESS) {
//            Log.e("MainActivity", "Google Play 服务不可用: " + result)
//        }


        Handler().postDelayed({
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        }, 3000)

    }
}