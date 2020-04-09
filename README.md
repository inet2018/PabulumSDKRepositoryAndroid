# Pabulum SDK Instructions - Android

[![](https://jitpack.io/v/inet2018/PabulumSDKRepositoryAndroid.svg)](https://jitpack.io/#inet2018/BodyFatScaleRepositoryAndroid)

[aar package download link](https://github.com/inet2018/PabulumSDKRepositoryAndroid/releases)

[中文文档](README_CN.md)

This document is to guide Android developers to integrate Pabulum-SDK-Android in Android 4.4 and above systems, mainly for some key use examples

## 1. Import SDK


```
repositories {
    flatDir {
        dirs 'libs'
    }
}


Step 1. Add the JitPack repository to your build file
Add it to the root build.gradle at the end of the repository:
allprojects {
repositories {
...
maven {url 'https://jitpack.io'}
}
}

Step 2. Add dependencies
dependencies {
implementation 'com.github.inet2018: PabulumSDKRepositoryAndroid: 1.2.6'
}





You can also use the aar package dependency, please download it yourself and put it in the project's libs



```

## 2, permission settings

```
<!-In most cases, you need to ensure that the device supports BLE .-->
<uses-feature
    android: name = "android.hardware.bluetooth_le"
    android: required = "true" />

<uses-permission android: name = "android.permission.BLUETOOTH" />
<uses-permission android: name = "android.permission.BLUETOOTH_ADMIN" />

<!-Android 6.0 and above. Bluetooth scanning requires one of the following two permissions. You need to apply at run time .-->
<uses-permission android: name = "android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android: name = "android.permission.ACCESS_FINE_LOCATION" />

<!-Optional. If your app need dfu function .-->
<uses-permission android: name = "android.permission.INTERNET" />
```

> 6.0 and above systems must locate permissions and need to manually obtain permissions

## 3, start integration

> Add under AndroidManifest.xml application tag
```
<application>
    ...

    <service android: name = "cn.net.aicare.pabulumlibrary.pabulum.PabulumService" />

</ application>

```


> Initialization, [key registration] (http://sdk.aicare.net.cn/register)
```
// It is recommended to initialize in Application
PabulumSDK.getInstance (). Init (this, "key", "secret");
```

You can directly make your own Activity class inherit BleProfileServiceReadyActivity

```
public class MyActivity extends BleProfileServiceReadyActivity
      @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        // Determine whether the mobile device supports Ble
        if (! ensureBLESupported ()) {
            T.showShort (this, R.string.not_support_ble);
            finish ();
        }
        // Determine whether there is positioning permission, this method does not encapsulate the specific code can be obtained in the demo, you can also call the request permission method in your own way
        initPermissions ();
        // Determine whether Bluetooth is turned on, if you need to change the style, you can implement it yourself
        if (! isBLEEnabled ()) {
            showBLEDialog ();
        }
    }


```

## 4, scan the device, stop scanning the device, check the scan status
The APIs related to scanning are as follows. For details, refer to the BleProfileServiceReadyActivity class. For specific use, refer to the demo

```
  // Call the startScan method to start scanning
  startScan ();
  // onLeScanCallback (BluetoothDevice device, int rssi) interface will callback to get the device that conforms to the protocol
    @Override
    protected void onLeScanCallback (BluetoothDevice device, int rssi) {
               // equipment conforming to the agreement


    }
// Call the stopScan method to stop scanning
 stopScan ();


```



## 5, connect the device, disconnect the device

The connection-related APIs are as follows. For details, refer to the BleProfileServiceReadyActivity class. For specific usage, refer to the demo.

```
// Call the connectDevice (BluetoothDevice device) method to connect to the device. You need to pass in the BluetoothDevice object, which can be obtained in the onLeScanCallback () callback method.

connectDevice (BluetoothDevice device)
// The connection state can be obtained in onStateChanged
@Override
    public void onStateChanged (String deviceAddress, int state) {
        super.onStateChanged (deviceAddress, state);
        // state specific state see class description
        // The connection is successful: (state = BleProfileService.STATE_CONNECTED)
        // Disconnect: (state = BleProfileService.STATE_DISCONNECTED)
        // Successful verification: (state = BleProfileService.STATE_INDICATION_SUCCESS)
    }
// Call the disconnect method in the PabulumService.PabulumBinder class to disconnect
 binder.disconnect ()
```

Use the `connectDevice` method to connect, use the` onStateChanged` method to monitor the status of the connection, and use the `onError` method to monitor exceptions during the connection process for additional processing and troubleshooting. Use the `isConnected` method to determine whether the connection has been established.

## 6 Connect successfully, accept the data returned by the scale
The following methods or interfaces can be automatically obtained directly after inheriting the BleProfileServiceReadyActivity class

```
// Get the instance of PabulumService.PabulumBinder in the onServiceBinded method
    @Override
    protected void onServiceBinded (BleProfileService.LocalBinder binder) {
        this.binder = (PabulumService.PabulumBinder) binder;

   }
    // The device returns data
    @Override
    protected void getFoodData (FoodData foodData) {
         // FoodData (String data, byte unit, byte deviceType, double weight);
         // data (weight, corresponding to current unit),
         // unit (unit, same as above unitType),
         // deviceType (0x04: no decimal point, 0x05: large range),
         // weight (weight in g)
    }
    // List of supported units
    @Override
    protected void getUnits (int [] units) {
       // List of units defined in PabulumBleConfig
       UNIT_G = 0x00; // g
       UNIT_ML = 0x01; // ml
       UNIT_LB = 0x02; // pound
       UNIT_OZ = 0x03; // oz
       UNIT_KG = 0x04; // kg
       UNIT_FG = 0x05; // jin = 500g
       UNIT_ML_MILK = 0x06; // milk ml
       UNIT_ML_WATER = 0x07; // water ml
       UNIT_FL_OZ_MILK = 0x08; // milk floz
       UNIT_FL_OZ_WATER = 0x09; // Water floz
    }
    // Current unit
    @Override
    protected void getUnit (byte unitType) {

    }
    // The timing operation instruction returned by the device
    @Override
    protected void getTimeStatus (int status) {
        // status status (on = 1, off = 2, reset = 3)
    }

    // Countdown start instruction returned by the device
   @Override
    protected void getCountdownStart (int time) {
        // time seconds
    }

    // Sync time instruction returned by the device
    @Override
    protected void getSynTime (byte cmdType, int timeS) {
        // timeS seconds
        // cmdType is the instruction type, range
        //PabulumBleConfig.SYN_TIME-> positive timing synchronization time
        //PabulumBleConfig.SYN_TIME_LESS-> Countdown synchronization time
        //PabulumBleConfig.TIMING_PAUSE-> Pause the synchronization time while timing
        //PabulumBleConfig.TIMING_PAUSE_LESS-> Countdown pauses synchronization time
    }

    // Error instruction
    @Override
    protected void getErrCodes (int [] ints) {
        // err [0] == 1 overload, 0 positiveNormal;
        // err [1] == 1 low power, 0 normal
    }
    // stop alarm instruction
    @Override
    protected void getStopAlarm () {

    }

    // Version information returned by the device
    @Override
    protected void getBleVersion (String version) {

    }

    // The signal strength of the device
    @Override
    public void onReadRssi (int rssi) {

    }

    // Transparent transmission data returned by the device (cannot start with 0xAC and 0xAD to avoid conflict with other SDK protocols):
    @Override
    protected void getPenetrateData (byte [] bytes) {

    }

```
> Note: Some of these interfaces or methods require APP to issue commands to body fat to return data.
## 7 Give instructions to the device
Get an instance of PabulumService.PabulumBinder in BleProfileServiceReadyActivity.onServiceBinded (PabulumService.PabulumBinder binder), call the method inside binder


```
Units supported by the scale: getUnits ();

Setting unit: setUnit (byte unitType); // The list supported by unitType is obtained from getUnits ()

Tare: netWeight ();

Shutdown: powerOff ();

Custom write data interface: writeValue (byte [] value); // value cannot be null, length cannot be greater than 20

// --------- Timing related functions of coffee scale

Start timing: startTime ();

Pause, timing: pauseTime (int time);

Pause, countdown: pauseTimeLess (int time);

Reset timing: resetTime ();

Start countdown: startTimeLess (int time); // time is seconds

Stop alarm command: stopAlarm ();

```


## 8 categories


#### 1.FoodData (weight data)
```
Type Parameter name Description
String data; // Weight, corresponding to the current unit
byte unit; // unit
byte deviceType; // Device type: 0x04 (no decimal point), 0x05 (large range)
double weight; // Weight, corresponding unit g
```
