# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


# 设置优化处理的次数，通常无需修改，默认5次
-optimizationpasses 5

# 不使用大小写混合的类名，避免某些平台兼容性问题
-dontusemixedcaseclassnames

# 不跳过非public的库类，保证所有库类都被处理
-dontskipnonpubliclibraryclasses

# 不进行代码优化，防止优化带来不可预期的问题
-dontoptimize

# 不进行预校验，适用于某些Android平台
-dontpreverify

# 输出详细的ProGuard日志，方便调试
-verbose

# 禁用某些优化，防止因优化导致的功能异常
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
#-obfuscationdictionary dictionary.txt
#-classobfuscationdictionary dictionary.txt
#-packageobfuscationdictionary dictionary.txt

-keepattributes *Annotation*

# 保留所有继承自android.app.Fragment的public类，防止被混淆
-keep public class * extends android.app.Fragment
# 保留所有继承自android.app.Activity的public类，防止被混淆
-keep public class * extends android.app.Activity
# 保留所有继承自android.app.Application的public类，防止被混淆
-keep public class * extends android.app.Application
# 保留所有继承自android.app.Service的public类，防止被混淆
-keep public class * extends android.app.Service
# 保留所有继承自android.content.BroadcastReceiver的public类，防止被混淆
-keep public class * extends android.content.BroadcastReceiver
# 保留所有继承自android.content.ContentProvider的public类，防止被混淆
-keep public class * extends android.content.ContentProvider
# 保留所有继承自android.app.backup.BackupAgentHelper的public类，防止被混淆
-keep public class * extends android.app.backup.BackupAgentHelper
# 保留所有继承自android.preference.Preference的public类，防止被混淆
-keep public class * extends android.preference.Preference
# 保留Google Play授权服务类，防止被混淆
-keep public class com.android.vending.licensing.ILicensingService

-dontwarn android.support.v4.**
-keep class android.support.v4.** { *; }
-keep interface android.support.v4.** { *; }
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v4.app.Fragment

# 忽略所有警告，防止构建中断
-ignorewarnings

# 输出所有处理过的类名到class_files.txt
-dump class_files.txt

# 输出保留的入口类和成员到seeds.txt
-printseeds seeds.txt

# 输出未被使用的代码到unused.txt
-printusage unused.txt

# 输出混淆映射关系到mapping.txt
-printmapping mapping.txt


-keepattributes EnclosingMetho

-dontwarn com.parse.**
-keep class com.parse.** { *;}

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
}

-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

# 保留所有实现了Serializable接口的类名，防止序列化出错
-keepnames class * implements java.io.Serializable

# 保留所有实现了Serializable接口的类的序列化相关成员，防止序列化兼容性问题
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# 保留所有资源类（R$*），防止资源ID被混淆
-keep class **.R$* { *; }
-keepclassmembers class **.R$* {
    public static <fields>;
}

# 保留异常和内部类信息，便于调试和反射
-keepattributes Exceptions,InnerClasses
# 保留支付宝SDK相关类及其成员，防止支付功能异常
-keep public class com.alipay.android.app.** {
    public <fields>;
    public <methods>;
}


-keepclasseswithmembers,allowshrinking class * {
    native <methods>;
}

-keepclasseswithmembers,allowshrinking class * {
    public <init>(android.content.Context,android.util.AttributeSet);
}

-keepclasseswithmembers,allowshrinking class * {
    public <init>(android.content.Context,android.util.AttributeSet,int);
}

# 保留所有枚举类的values和valueOf方法，防止枚举反射异常
-keepclassmembers enum  * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# 保留所有实现Parcelable接口的类的CREATOR成员，防止序列化异常
-keep class * extends android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}


-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
# 保留泛型签名信息，防止Gson等库反射出错
-keepattributes Signature
-keepattributes Exceptions

-dontwarn sun.misc.**
# 保留RxJava内部队列相关成员，提升兼容性
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}
# 保留RxJava队列生产者节点成员
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
# 保留RxJava队列消费者节点成员
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}


# 不警告EventBus相关类缺失，防止编译警告
-dontwarn org.greenrobot.eventbus.**
# 保留EventBus相关类及其成员，保证事件总线功能正常
-keep class org.greenrobot.eventbus.** {*;}
-keepclassmembers class ** {
    public void onUser*(**);
}

# 不警告OkHttp相关类缺失，防止编译警告
-dontwarn com.squareup.okhttp.**
# 保留OkHttp相关类及其成员，保证网络请求功能
-keep class com.squareup.okhttp.** { *;}
-dontwarn okio.**

#EventBus
-keepclassmembers class ** {
    public void onEvent*(**);
    void onEvent*(**);
}

# 保留ButterKnife注解相关类，保证视图注入功能
-keep class butterknife.** { *; }
# 不警告ButterKnife内部类缺失，防止编译警告
-dontwarn butterknife.internal.**
# 保留ButterKnife生成的ViewInjector类
-keep class **$$ViewInjector { *; }
# 保留ButterKnife生成的ViewBinder类
-keep class **$$ViewBinder { *; }

# 保留带有InjectView注解的类名，防止视图注入出错
-keepnames class * { @butterknife.InjectView *;}

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

# 保留Gson相关类及其成员，保证JSON解析功能
-keep class com.google.gson.** { *; }

-keep class sun.misc.Unsafe { *; }
# 保留Gson流式解析相关类
-keep class com.google.gson.stream.* { *; }
# 保留Gson示例模型类
-keep class com.google.gson.examples.android.model.* { *; }
-keep class com.google.gson.* { *;}

# 保留Google Play服务相关SafeParcelable接口实现类，防止反射异常
-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
    public static final *** NULL;
}

# 保留所有实现Parcelable接口的类名，防止序列化/反序列化异常
-keepnames class * implements android.os.Parcelable
# 保留所有实现Parcelable接口的类的CREATOR成员，防止序列化异常
-keepclassmembers class * implements android.os.Parcelable {
  public static final *** CREATOR;
}

# 保留@Keep注解及其相关类，防止被混淆
-keep @interface android.support.annotation.Keep
# 保留带有@Keep注解的类，防止被混淆
-keep @android.support.annotation.Keep class *
-keepclasseswithmembers class * {
  @android.support.annotation.Keep <fields>;
}
-keepclasseswithmembers class * {
  @android.support.annotation.Keep <methods>;
}

# 保留Google KeepName注解及相关类
-keep @interface com.google.android.gms.common.annotation.KeepName
# 保留带有KeepName注解的类名
-keepnames @com.google.android.gms.common.annotation.KeepName class *
# 保留带有KeepName注解的成员名
-keepclassmembernames class * {
  @com.google.android.gms.common.annotation.KeepName *;
}

# 保留DynamiteApi注解及相关类，支持动态加载
-keep @interface com.google.android.gms.common.util.DynamiteApi
# 保留带有DynamiteApi注解的public类
-keep public @com.google.android.gms.common.util.DynamiteApi class * {
  public <fields>;
  public <methods>;
}

# 不警告Android网络安全策略类缺失
-dontwarn android.security.NetworkSecurityPolicy

# 不警告Android兼容性相关类缺失
-dontwarn android.net.compatibility.**
# 不警告Android HTTP相关类缺失
-dontwarn android.net.http.**
# 不警告Android内部HTTP multipart相关类缺失
-dontwarn com.android.internal.http.multipart.**
# 不警告Apache Commons相关类缺失
-dontwarn org.apache.commons.**
# 不警告Apache Http相关类缺失
-dontwarn org.apache.http.**
# 保留Apache Http相关类及其成员
-keep class org.apache.http.**{*;}
-keep class android.net.compatibility.**{*;}
-keep class android.net.http.**{*;}
-keep class com.android.internal.http.multipart.**{*;}
# 保留Apache Commons相关类及其成员
-keep class org.apache.commons.**{*;}

# 保留Retrofit 1.x相关类及其成员，保证网络请求兼容
-keep class retrofit.** { *; }
-keepclasseswithmembers class * {
    @retrofit.** *;
}
-keepclassmembers class * {
    @retrofit.** *;
}

-keepclassmembers class **.R$* {
  public static <fields>;
}

# 保留WebChromeClient扩展类的openFileChooser方法，防止WebView文件选择功能异常
-keepclassmembers class * extends android.webkit.WebChromeClient{
		public void openFileChooser(...);
}

# 不警告Android Widget相关类缺失
-dontwarn android.widget.**
# 保留Android Widget相关类及其成员
-keep class android.widget.** {*;}

# 不警告Android v7 Widget相关类缺失
-dontwarn android.support.v7.widget.**
# 保留Android v7 Widget相关类及其成员
-keep class android.support.v7.widget.**{*;}

# 不警告Android Design Widget相关类缺失
-dontwarn android.support.design.widget.**
# 保留Android Design Widget相关类及其成员
-keep class android.support.design.widget.**{*;}

# 保留AVLoadingIndicatorView动画库相关类
-keep class com.wang.avi.** { *; }
# 保留AVLoadingIndicatorView动画库指示器相关类
-keep class com.wang.avi.indicators.** { *; }

-keepattributes *Annotation*

# 保留淘宝SDK相关类及其成员
-keep class com.taobao.** {*;}
# 保留org.android包下所有类及其成员
-keep class org.android.** {*;}
# 保留阿里云通信相关类及其成员
-keep class anet.channel.** {*;}
# 保留友盟统计、推送等相关类及其成员
-keep class com.umeng.** {*;}
# 保留小米SDK相关类及其成员
-keep class com.xiaomi.** {*;}
# 保留华为SDK相关类及其成员
-keep class com.huawei.** {*;}
# 保留魅族SDK相关类及其成员
-keep class com.meizu.** {*;}
# 保留Thrift相关类及其成员
-keep class org.apache.thrift.** {*;}

# 保留阿里SDK相关类及其成员
-keep class com.alibaba.sdk.android.**{*;}
# 保留友盟UT相关类及其成员
-keep class com.ut.**{*;}
# 保留com.ta包下所有类及其成员
-keep class com.ta.**{*;}

# 保留所有public资源类的静态字段，防止资源访问出错
-keep public class **.R$*{
   public static final int *;
}

# 保留com.github.mmin18包下所有类及其成员
-keep class com.github.mmin18.** {*;}

# 不警告Android v8 renderscript相关类缺失
-dontwarn android.support.v8.renderscript.*
# 保留renderscript RenderScript类的native方法
-keepclassmembers class android.support.v8.renderscript.RenderScript {
  native *** rsn*(...);
  native *** n*(...);
}

# 不警告java.lang.invoke相关类缺失
-dontwarn java.lang.invoke.*
-keepattributes SourceFile,LineNumberTable
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

-dontwarn android.support.v4.**
-keep class android.support.v4.** { *; }
-keep interface android.support.v4.** { *; }
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v4.app.Fragment

-dontwarn retrofit2.**
-keep class retrofit2.** { *; }

-keep class com.google.gson.* { *;}

-dontwarn okhttp3.**
-keep class okhttp3.** { *; }
-dontwarn okio.**
-keep class okio.** { *; }

-dontwarn sun.misc.**
-keep class rx.** { *; }

# 保留平安AI相关业务库类
-keep class pingan.ai.paverify.vertify.** { *; }
# 保留平安AI人脸管理相关类
-keep class com.pingan.ai.face.manager.** { *; }
# 保留平安AI人脸实体相关类
-keep class com.pingan.ai.face.entity.** { *; }
# 保留平安AI常量类
-keep class com.pingan.ai.face.common.PaFaceConstants { *; }


# 保留平安AI相关所有类
-keep class com.pingan.ai.** {*;}
# 保留平安AI相关所有类（冗余配置）
-keep class pingan.ai.paverify.vertify.** {*;}

# 保留Appsflyer归因SDK相关类
-keep class com.appsflyer.** { *; }
-keep public class com.android.installreferrer.** { *; }
# 保留Kotlin内部实现类
-keep class kotlin.jvm.internal.** { *; }

# 保留AndroidX Camera核心库相关类
-keep class androidx.camera.core.** { *; }
# 保留AndroidX Camera核心库相关类的所有成员
-keepclassmembers class androidx.camera.core.** { *;}


#greendao
-keep class org.greenrobot.greendao.**{*;}
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
public static java.lang.String TABLENAME;
}
-keep class **$Properties
-keepclassmembers class **$Properties {*;}
-keep class * extends org.greenrobot.greendao.AbstractDao


-keepattributes *Annotation*
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# And if you use AsyncExecutor:
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

 -keep class com.gyf.immersionbar.* {*;}
 -dontwarn com.gyf.immersionbar.**

 -keepattributes JavascriptInterface
 -keepattributes *Annotation*

 -dontwarn com.razorpay.**
 -keep class com.razorpay.** {*;}

#hyperlibrary
-keep class com.hyper.hyperlibrary.CountType { *; }
-keep class com.hyper.hyperlibrary.HyperCardInter { *; }
-keep class com.hyper.hyperlibrary.HyperMatchInter { *; }
-keep class com.hyper.hyperlibrary.HyperUtils{
      public <methods>;#保持该类下所有的共有方法不被混淆
  }
-dontwarn  co.hyperverge.**
-keepclassmembers class * implements javax.net.ssl.SSLSocketFactory {
       private javax.net.ssl.SSLSocketFactory delegate;
  }
  ## GSON 2.2.4 specific rules ##
  # Gson uses generic type information stored in a class file when working with fields. Proguard
  # removes such information by default, so configure it to keep all of it.
  -keepattributes EnclosingMethod
  # Gson specific classes
  -keep class sun.misc.Unsafe { *; }
  -keep class com.google.** { *; }

-keep class io.reactivex.** {*;}
-keep class com.yinda.datasyc.bean.** { *; }
-keep class com.yinda.datasyc.http.SDKManage{
    public <methods>;
 }
-keepattributes EnclosingMethod
-keep class sun.misc.Unsafe { *; }
-keep class com.google.** { *; }

-keep public class com.adjust.sdk.** { *; }
-keep class com.google.android.gms.common.ConnectionResult {
    int SUCCESS;
}
-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient {
    com.google.android.gms.ads.identifier.AdvertisingIdClient$Info getAdvertisingIdInfo(android.content.Context);
}
-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient$Info {
    java.lang.String getId();
    boolean isLimitAdTrackingEnabled();
}
-keep public class com.android.installreferrer.** { *; }

-keep class org.litepal.** {
    *;
}

-keep class * extends org.litepal.crud.DataSupport {
    *;
}

-keep class * extends org.litepal.crud.LitePalSupport {
    *;
}

-keep class org.xmlpull.** {*;}
-keep public class * extends org.xmlpull.**
-keep interface org.xmlpull.** {*;}

#PictureSelector 2.0
-keep class com.luck.picture.lib.** { *; }

#Ucrop
-dontwarn com.yalantis.ucrop**
-keep class com.yalantis.ucrop** { *; }
-keep interface com.yalantis.ucrop** { *; }

#Okio
-dontwarn org.codehaus.mojo.animal_sniffer.*

-keep class com.successful.chai.remote.data.** { *; }

-keep public class com.google.android.material.bottomnavigation.BottomNavigationView { *; }
-keep public class com.google.android.material.bottomnavigation.BottomNavigationMenuView { *; }
-keep public class com.google.android.material.bottomnavigation.BottomNavigationPresenter { *; }
-keep public class com.google.android.material.bottomnavigation.BottomNavigationItemView { *; }

#glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

-dontwarn com.jeremyliao.liveeventbus.**
-keep class com.jeremyliao.liveeventbus.** { *; }
-keep class android.arch.lifecycle.** { *; }
-keep class android.arch.core.** { *; }

-dontwarn com.jeremyliao.liveeventbus.**
-keep class com.jeremyliao.liveeventbus.** { *; }
-keep class androidx.lifecycle.** { *; }
-keep class androidx.arch.core.** { *; }

#otto
-keepattributes *Annotation*
-keepclassmembers class ** {
    @com.squareup.otto.Subscribe public *;
    @com.squareup.otto.Produce public *;
}

#firebase
-dontwarn com.appsflyer.**
-keep public class com.google.firebase.messaging.FirebaseMessagingService {
    public *;
}


#af
-keep class com.appsflyer.** { *; }
-keep public class com.android.installreferrer.** { *; }
-keep class kotlin.jvm.internal.** { *; }
# androidx.core.view.accessibility
-keep class androidx.core.** { *; }
# camera
-keep class androidx.camera.core.** { *; }
-keepclassmembers class androidx.camera.core.** { *; }
# gms
-keep class com.google.android.gms.dynamite.** { *; }
-keepclassmembers class com.google.android.gms.dynamite.** { *; }
# AppCompat 相关规则
-keep class androidx.appcompat.app.** { *; }
-keepnames class androidx.appcompat.app.** { *; }
-assumenosideeffects class androidx.transition.Transition {
    *;
}