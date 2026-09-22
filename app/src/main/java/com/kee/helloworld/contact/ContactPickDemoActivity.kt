package com.kee.helloworld.contact

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.kee.helloworld.R

class ContactPickDemoActivity : AppCompatActivity() {

    private val contactType = 1000 + 100 + 200

    private lateinit var envView: TextView
    private lateinit var resultView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_pick_demo)
        envView = findViewById(R.id.demo_env)
        resultView = findViewById(R.id.demo_result)
        findViewById<Button>(R.id.demo_pick).setOnClickListener { pickContact() }
        envView.text = buildEnvText()
    }

    private fun pickContact() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
        try {
            startActivityForResult(intent, contactType)
            resultView.text =
                "已发出 ACTION_PICK。\n若被系统拦截，这里不会有联系人数据，只会看到系统 toast。"
        } catch (e: Exception) {
            resultView.text = "startActivity 失败：${e.javaClass.simpleName}\n${e.message}"
        }
    }

    @Deprecated("Match production startActivityForResult")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode != contactType) {
            return
        }
        if (data?.data == null) {
            resultView.text = "回调空数据。resultCode=$resultCode\n系统拦截或用户取消时常见这个结果。"
            return
        }
        val cursor = contentResolver.query(
            data.data!!, arrayOf(
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
            ), null, null, null
        )
        var nickname = ""
        var number = ""
        cursor?.use {
            if (it.moveToFirst()) {
                number = it.getString(0).orEmpty()
                nickname = it.getString(1).orEmpty()
            }
        }
        resultView.text = "选择成功\nuri=${data.data}\n姓名=$nickname\n号码=$number"
    }

    private fun buildEnvText(): String {
        val declared = packageManager.getPackageInfo(
            packageName, PackageManager.GET_PERMISSIONS
        ).requestedPermissions?.contains(Manifest.permission.READ_CONTACTS) == true
        val granted = ContextCompat.checkSelfPermission(
            this, Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED
        val pickIntent = Intent(Intent.ACTION_PICK).apply {
            addCategory(Intent.CATEGORY_DEFAULT)
            type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
        }
        val resolved = packageManager.resolveActivity(pickIntent, 0)?.activityInfo
        val systemResolved = packageManager.resolveActivity(
            pickIntent, PackageManager.MATCH_SYSTEM_ONLY
        )?.activityInfo
        return buildString {
            appendLine("brand=${Build.BRAND}")
            appendLine("model=${Build.MODEL}")
            appendLine("release=${Build.VERSION.RELEASE}")
            appendLine("sdk=${Build.VERSION.SDK_INT}")
            appendLine("READ_CONTACTS 已声明=$declared")
            appendLine("READ_CONTACTS 已授权=$granted")
            appendLine("resolveActivity=${resolved?.packageName}/${resolved?.name}")
            appendLine("MATCH_SYSTEM_ONLY=${systemResolved?.packageName}/${systemResolved?.name}")
        }
    }
}
