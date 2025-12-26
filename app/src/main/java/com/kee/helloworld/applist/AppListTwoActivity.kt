package com.kee.helloworld.applist

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.kee.helloworld.databinding.LayoutMainBinding
import org.json.simple.JSONArray
import org.json.simple.JSONObject

class AppListTwoActivity : AppCompatActivity() {

    val binding by lazy { LayoutMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        Handler().postDelayed({
            val tolTc = getTolTc(this)
            binding.text.text = getDeviceInfo("${tolTc.size}")
        }, 2000)

        binding.btn.isVisible = false
    }


    /**
     * 获取完整的设备信息
     */
    fun getDeviceInfo(size: String): String {
        val info = StringBuilder()

        info.append("getInstalledPackages获取\n")
        info.append("获取到applist数量：$size \n\n\n")

        // Android版本信息
        info.append("Android版本信息：\n")
        info.append("API级别: ").append(Build.VERSION.SDK_INT).append("\n")
        info.append("版本名称: ").append(Build.VERSION.RELEASE).append("\n")
        info.append("内部版本: ").append(Build.VERSION.INCREMENTAL).append("\n")
        info.append("版本代号: ").append(Build.VERSION.CODENAME).append("\n\n")


        // 设备型号信息
        info.append("设备信息：\n")
        info.append("制造商: ").append(Build.MANUFACTURER).append("\n")
        info.append("品牌: ").append(Build.BRAND).append("\n")
        info.append("型号: ").append(Build.MODEL).append("\n")
        info.append("设备: ").append(Build.DEVICE).append("\n")
        info.append("产品: ").append(Build.PRODUCT).append("\n")


        // 硬件信息
        info.append("\n硬件信息：\n")
        info.append("主板: ").append(Build.BOARD).append("\n")
        info.append("硬件: ").append(Build.HARDWARE).append("\n")


        // 序列号（需要权限）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                val serial = Build.getSerial()
                info.append("序列号: ").append(serial).append("\n")
            } catch (e: SecurityException) {
                info.append("序列号: 无权限访问\n")
            }
        } else {
            info.append("序列号: ").append(Build.SERIAL).append("\n")
        }

        return info.toString()
    }

    /**
     * 获取简洁的设备标识
     */
    fun getSimpleDeviceInfo(): String {
        return Build.MANUFACTURER + " " + Build.MODEL + " (Android " + Build.VERSION.RELEASE + ", API " + Build.VERSION.SDK_INT + ")"
    }

    /**
     * 获取Android版本号（如：12、13等）
     */
    fun getAndroidVersion(): String? {
        return Build.VERSION.RELEASE
    }

    /**
     * 获取设备型号
     */
    fun getDeviceModel(): String {
        return Build.MANUFACTURER + " " + Build.MODEL
    }


    fun getTolTc(context: Context): JSONArray {
        val jsonArray = JSONArray()
        try {
            val pm = context.packageManager

            val packageList: List<PackageInfo> =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    pm.getInstalledPackages(
                        PackageManager.PackageInfoFlags.of(0)
                    )
                } else {
                    @Suppress("DEPRECATION")
                    pm.getInstalledPackages(0)
                }

            for (packageInfo in packageList) {

                val jsonObject = JSONObject().apply {
                    put("version_name", packageInfo.versionName)
                    put("version_code", packageInfo.versionCode)
                    put("flags", packageInfo.applicationInfo?.flags)
                    put(
                        "label",
                        packageInfo.applicationInfo?.let {
                            pm.getApplicationLabel(it).toString()
                        } ?: ""
                    )
                    put("package", packageInfo.packageName)
                    put("in_time", packageInfo.firstInstallTime)
                    put("up_time", packageInfo.lastUpdateTime)

                    val appInfo = packageInfo.applicationInfo
                    val appType =
                        if (appInfo != null && (ApplicationInfo.FLAG_SYSTEM and appInfo.flags) != 0) {
                            1
                        } else {
                            0
                        }
                    put("app_type", appType)
                }

                jsonArray.add(jsonObject)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return jsonArray
    }


}