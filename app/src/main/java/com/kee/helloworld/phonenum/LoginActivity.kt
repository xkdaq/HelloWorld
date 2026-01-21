package com.kee.helloworld.phonenum

import android.app.PendingIntent
import android.content.Context
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.identity.GetPhoneNumberHintIntentRequest
import com.google.android.gms.auth.api.identity.Identity
import com.kee.helloworld.R
import java.util.Locale

//https://developer.android.com/identity/phone-number-hint?hl=zh-cn
//自动获取手机号
class LoginActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_email)

        findViewById<TextView>(R.id.textsss).setOnClickListener {
            startGoogleSignIn()
        }

        val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        // 获取 SIM 卡所属国家（ISO 3166-1 alpha-2 格式，例如 "cn"、"ph"）
        val simCountryIso = telephonyManager.simCountryIso?.lowercase() ?: ""
        // 转为国家名
        val locale = Locale("", simCountryIso)
        val countryName = locale.displayCountry
        Toast.makeText(
            this,
            ("CountrySIM国家码: $simCountryIso, 国家: $countryName"),
            Toast.LENGTH_SHORT
        ).show()

    }

    val phoneNumberHintIntentResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            try {
                val phoneNumber =
                    Identity.getSignInClient(this).getPhoneNumberFromIntent(result.data)
                Log.e("xxxx", "====>$phoneNumber")
            } catch (e: Exception) {
                Log.e("MainActivity", "Phone Number Hint failed2")
            }
        }

    private fun startGoogleSignIn() {
        val request = GetPhoneNumberHintIntentRequest.builder().build()

        Identity.getSignInClient(this)
            .getPhoneNumberHintIntent(request)
            .addOnSuccessListener { result: PendingIntent ->
                try {
                    phoneNumberHintIntentResultLauncher.launch(
                        IntentSenderRequest.Builder(result).build()
                    )
                } catch (e: Exception) {
                    Log.e("MainActivity", "Launching the PendingIntent failed")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("MainActivity", "Phone Number Hint failed1", exception)
            }
    }
}