package com.kee.helloworld

import android.app.PendingIntent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.identity.GetPhoneNumberHintIntentRequest
import com.google.android.gms.auth.api.identity.Identity

//https://developer.android.com/identity/phone-number-hint?hl=zh-cn
//自动获取手机号
class LoginActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val request = GetPhoneNumberHintIntentRequest.builder().build()

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
            .addOnFailureListener {
                Log.e("MainActivity", "Phone Number Hint failed1")
            }


    }
}