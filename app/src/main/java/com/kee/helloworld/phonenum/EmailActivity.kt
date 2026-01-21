package com.kee.helloworld.phonenum

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.kee.helloworld.R

class EmailActivity : ComponentActivity() {

    private lateinit var credentialLauncher: ActivityResultLauncher<IntentSenderRequest>

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var signInLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_email)
        //方式一
        initGoogleAccountCredential()
        //方式二
        initGoogleSignIn()
        initSignInLauncher()
        findViewById<TextView>(R.id.textsss).setOnClickListener {
            //showGoogleAccountPicker()
            startGoogleSignIn()
        }
    }

    private fun initGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            // .requestIdToken(getString(R.string.server_client_id)) // 如果以后要 token
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }


    private fun initSignInLauncher() {
        signInLauncher =
            registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) { result ->
                if (result.resultCode == RESULT_OK) {
                    try {
                        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                        val account = task.getResult(ApiException::class.java)

                        val email = account.email
                        val displayName = account.displayName
                        val googleId = account.id

                        Log.d("GoogleSignIn", "email=$email")
                        Log.d("GoogleSignIn", "name=$displayName")
                        Log.d("GoogleSignIn", "id=$googleId")

                    } catch (e: ApiException) {
                        Log.e("GoogleSignIn", "登录失败 code=${e.statusCode}", e)
                    }

                } else {
                    Log.e("GoogleSignIn", "用户取消登录")
                }
            }
    }

    private fun startGoogleSignIn() {
        val intent = googleSignInClient.signInIntent
        signInLauncher.launch(intent)
    }


    //19.2.0版本Credentials API
    private fun initGoogleAccountCredential() {
//        credentialLauncher =
//            registerForActivityResult(
//                ActivityResultContracts.StartIntentSenderForResult()
//            ) { result ->
//                if (result.resultCode == RESULT_OK && result.data != null) {
//                    val credential = result.data?.extras?.get(Credential.EXTRA_KEY) as? Credential
//                    credential?.let {
//                        val email = it.id
//                        Log.e("GoogleEmail", "email = $email")
//                    } ?: run {
//                        Log.e("GoogleAccount", "未获取到有效账号信息")
//                    }
//
//                } else {
//                    Log.e("GoogleAccount", "用户取消或失败 resultCode=${result.resultCode}")
//                }
//            }
    }


    //19.2.0版本Credentials API
    private fun showGoogleAccountPicker() {
//        val hintRequest = HintRequest.Builder()
//            .setEmailAddressIdentifierSupported(true)
//            .setAccountTypes(IdentityProviders.GOOGLE)
//            .build()
//
//        val googleApiClient = GoogleApiClient.Builder(this)
//            .addApi(Auth.CREDENTIALS_API)
//            .build()
//
//        googleApiClient.connect()
//
//        val pendingIntent = Auth.CredentialsApi.getHintPickerIntent(
//            googleApiClient,
//            hintRequest
//        )
//
//        val request = IntentSenderRequest.Builder(pendingIntent.intentSender).build()
//
//        credentialLauncher.launch(request)
    }


}