# BitcoinTestApp

*Independent Test *


Features
========

*Shows MarketPrice for Bitcoins*

Building project
================

This chapter describes how to build APK with Gradle and prepare app for publishing.

You don't need to install Gradle on your system, because there is a [Gradle Wrapper](http://www.gradle.org/docs/current/userguide/gradle_wrapper.html). The wrapper is a batch script on Windows, and a shell script for other operating systems. When you start a Gradle build via the wrapper, Gradle will be automatically downloaded and used to run the build.

1. Clone this repository
2. Open configuration file _/app/src/main/java/com/number26/bitcointest/util/ConfigUtils.java_ and set constants as required (see below for more info)
3. Open main build script _/app/build.gradle_ and set constants and config fields as required (see below for more info)
4. Run `gradlew assemble` in console
5. APK should be available in _/app/build/outputs/apk_ directory

**Note:** You will also need a "local.properties" file to set the location of the SDK in the same way that the existing SDK requires, using the "sdk.dir" property. Example of "local.properties" on Windows: `sdk.dir=C:\\adt-bundle-windows\\sdk`. Alternatively, you can set an environment variable called "ANDROID\_HOME".

**Tip:** Command `gradlew assemble` builds both - debug and release APK. You can use `gradlew assembleDebug` to build debug APK. You can use `gradlew assembleRelease` to build release APK. Debug APK is signed by debug keystore. Release APK is signed by own keystore, stored in _/extras/keystore_ directory.

**Signing process:** Keystore passwords are automatically loaded from property file during building the release APK. Path to this file is defined in "keystore.properties" property in "gradle.properties" file. If this property or the file does not exist, user is asked for passwords explicitly.

ConfigUtils.java
------------------

This is the main configuration file and there are some important constants: addresses to API endpoints,  etc. Make sure that all constants are set up properly.


build.gradle
------------

This is the main build script and there are 4 important constants for defining version code and version name.

* VERSION\_MAJOR
* VERSION\_MINOR
* VERSION\_PATCH
* VERSION\_BUILD

See [Versioning Your Applications](http://developer.android.com/tools/publishing/versioning.html#appversioning) in Android documentation for more info.

There are also a build config fields in this script. Check "buildTypes" configuration and make sure that all fields are set up properly for debug and release. It is very important to correctly set these true/false switches before building the APK.

* LOGS - true for showing logs
* DEV\_API - true for development API endpoint

**Important:** Following configuration should be used for release build type, intended for publishing on Google Play:

```groovy
buildConfigField "boolean", "LOGS", "false"
buildConfigField "boolean", "DEV_API", "false"
```

Dependencies
============

* [Android Support Library v4](http://developer.android.com/tools/extras/support-library.html)
* [AppCompat](https://developer.android.com/reference/android/support/v7/appcompat/package-summary.html)
* [Android Support Design](http://android-developers.blogspot.cz/2015/05/android-design-support-library.html)
* [rxjava](https://github.com/ReactiveX/RxJava)
* [rxjava android](https://github.com/ReactiveX/RxAndroid)
* [Retrofit2](http://square.github.io/retrofit/)
* [stateful-layout](https://github.com/jakubkinst/Android-StatefulLayout)
* [mockito](http://mockito.org/)
* [Espresso](https://google.github.io/android-testing-support-library/docs/espresso/)


Testing
=======

* Test by runing in mock flavour the JUnit test on the class: /Users/olsisaqe/androidstudioworkspace/BitcoinTest/app/src/test/java/com/number26/bitcointest/BitcoinGraphPresenterTest.java
* Test UI for the moment not implemented
* Test app on different Android versions (handset, tablet)
* Test overdraws
* Test offline/empty/progress states


Publishing
==========

* Set proper versions in the main build script
* Check build config fields in the main build script
* Update text info in changelog/about/help


Developed by
============

[Olsi Saqe](mailto:olsi_saqe@yahoo.it)

