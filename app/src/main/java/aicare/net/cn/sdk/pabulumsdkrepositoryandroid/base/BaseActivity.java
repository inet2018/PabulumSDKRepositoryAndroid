package aicare.net.cn.sdk.pabulumsdkrepositoryandroid.base;

import android.bluetooth.BluetoothDevice;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import cn.net.aicare.pabulumlibrary.bleprofile.BleProfileService;
import cn.net.aicare.pabulumlibrary.bleprofile.BleProfileServiceReadyActivity;
import cn.net.aicare.pabulumlibrary.entity.FoodData;


public abstract class BaseActivity extends BleProfileServiceReadyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    protected void onStartScan() {

    }

    @Override
    protected void onServiceBinded(BleProfileService.LocalBinder binder) {

    }

    @Override
    public void onStateChanged(int state) {
        super.onStateChanged(state);
    }

    @Override
    public void onReadRssi(int rssi) {

    }

    @Override
    public void onError(String msg, int errorCode) {

    }

    @Override
    protected void getFoodData(FoodData foodData) {

    }

    @Override
    protected void getUnit(byte unitType) {

    }

    @Override
    protected void getBleVersion(String version) {

    }

    @Override
    protected void getTimeStatus(int status) {

    }

    @Override
    protected void getCountdownStart(int time) {

    }

    @Override
    protected void getSynTime(byte cmdType, int timeS) {

    }

    @Override
    protected void getBleDID(int did) {

    }

    @Override
    protected void getUnits(int[] ints) {

    }

    @Override
    protected void getErrCodes(int[] ints) {

    }

    @Override
    protected void getStopAlarm() {

    }

    @Override
    protected void getPenetrateData(byte[] bytes) {

    }

    @Override
    protected void onLeScanCallback(BluetoothDevice device, int rssi) {

    }

    @Override
    protected void onWriteSuccess(byte[] value) {

    }


}