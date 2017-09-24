


-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose

-keepattributes *Annotation*,Signature,InnerClasses,EnclosingMethod


-keep class com.vdprime.vdlib.utils.inject.**
-keep class com.vdprime.vdlib.views.**
-keep class com.vdprime.vdlib.utils.vdIDatabase

-keepclasseswithmembers class * extends com.vdprime.vdlib.utils.vdIDatabase { *; }

-keepclasseswithmembers class * extends com.vdprime.vdlib.views.BaseView {
	  void set*(***);
        *** get*();
}

-keepclasseswithmembers class * {
	@com.vdprime.vdlib.utils.inject.* **;
}