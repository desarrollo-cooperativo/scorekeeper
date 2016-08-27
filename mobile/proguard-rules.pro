
-repackageclasses ''
-allowaccessmodification

-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable

##########################################
## Default Android Configuration

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.gms.common

-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
    *** get*();
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.content.Context {
   public void *(android.view.View);
   public void *(android.view.MenuItem);
}

-keepclassmembers class * implements android.os.Parcelable {
    static android.os.Parcelable$Creator CREATOR;
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

-keep class **.R$* {
	<fields>;
}

-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclassmembers enum * { *; }


##########################################
## ActionBar

# The support library contains references to newer platform versions.
# Don't warn about those in case this app is linking against an older
# platform version.  We know about them, and they are safe.
-dontwarn android.support.**

-keep class android.support.v4.** { *; }
-keep interface android.support.v4.** { *; }
-keep class android.support.v7.** { *; }
-keep interface android.support.v7.** { *; }

##########################################
## Action Provider

-keep public class * extends android.support.v4.view.ActionProvider {
  <init>(...);
}

## RETROFIT
-dontwarn rx.**
-dontwarn com.google.**
-dontwarn com.squareup.**
-dontwarn okio.**
-dontwarn retrofit.**
-keepattributes *Annotation*
-keepattributes *Signature*
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.** { *; }
-keep class com.google.inject.** { *; }
-keep class org.apache.http.** { *; }
-keep class javax.inject.** { *; }
-keep class retrofit.** { *; }
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }

-dontnote android.net.http.**
-dontnote org.apache.http.**

##ButterKnife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

##Stetho
-keep class com.facebook.stetho.** { *; }

##Okio
-keep class  java.nio.file.** { *; }
-keep class  org.codehaus.mojo.** { *; }

## Rx
-keep class  sun.misc.** { *; }

## Appication
-keep class com.transition.scorekeeper.** { *; }
-dontwarn java.lang.invoke**
-keep class com.google.vending.licensing.** { *; }
-keep class com.google.common.hash.** { *; }

##gms
-keep public class com.google.android.gms.* { *; }
-dontwarn com.google.android.gms.**

## okhttp3
-keep public class com.android.org.conscrypt.SSLParametersImpl.** { *; }
-keep public class org.apache.harmony.xnet.provider.jsse.SSLParametersImpl.** { *; }
-keep public class sun.security.ssl.SSLContextImpl.** { *; }

## jackson
-dontwarn org.w3c.dom.bootstrap.DOMImplementationRegistry

## greendao
-keepclassmembers class * extends de.greenrobot.dao.AbstractDao {
    public static java.lang.String TABLENAME;
}
-keep class **$Properties

-keepattributes *Annotation*
-dontwarn sun.misc.**

##Rx
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}

-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}

-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

##Firebase
-keep class com.firebase.** { *; }
-keep class org.apache.** { *; }
-keepnames class com.fasterxml.jackson.** { *; }
-keepnames class javax.servlet.** { *; }
-keepnames class org.ietf.jgss.** { *; }
-dontwarn org.w3c.dom.**
-dontwarn org.joda.time.**
-dontwarn org.shaded.apache.**
-dontwarn org.ietf.jgss.**
-keep class com.shaded.fasterxml.jackson.** { *; }