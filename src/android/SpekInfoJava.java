package com.example.dvcinfo;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.lang.reflect.Method;

/**
 * This class echoes a string called from JavaScript.
 */
public class SpekInfoJava extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("serialNumber")) {
            //String message = args.getString(0);
            //this.coolMethod(message, callbackContext);
            String message = getSerialNumber();
            callbackContext.success(message);
            return true;
        } else if (action.equals("batterylevel")) {
            int level = getBatteryLevel();
            callbackContext.success(String.valueOf(level));
            return true;
        } else if (action.equals("modelname")) {
             String status = getModelName();
            callbackContext.success(status);
            return true;
        } else if (action.equals("osversion")) {
            String status = getOSVersion();
            callbackContext.success(status);
            return true;
        } else if (action.equals("devicename")) {
            String status = getDeviceName();
            callbackContext.success(status);
            return true;
        } else if (action.equals("manufacturer")) {
            String status = getManufacturer();
            callbackContext.success(status);
            return true;
        } else if (action.equals("buildnumber")) {
            String status = getBuildNumber();
            callbackContext.success(status);
            return true;
        } else if (action.equals("kernelversion")) {
            String status = getKernelVersion();
            callbackContext.success(status);
            return true;
        } else if (action.equals("wifistatus")) {
            String status = isWifiNetworkAvailable();
            callbackContext.success(status);
            return true;
        } else if (action.equals("mobilenetwork")) {
            String status = isMobileNetworkAvailable();
            callbackContext.success(status);
            return true;
        } else if (action.equals("bluetoothstatus")) {
            String status = checkBluetoothConnection();
            callbackContext.success(status);
            return true;
        }
        
        return false;
    }

    private void coolMethod(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
    
    public String isWifiNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) webView.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (wifiNetwork != null && wifiNetwork.isConnected()) {
            return "Connected";
        } else return "Disconnected";
    }

    public String isMobileNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) webView.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (mobileNetwork != null && mobileNetwork.isConnected()) {
            return "Connected";
        } else {
            return "Disconnected";
        }
    }
    
    private String checkBluetoothConnection () {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            return "Not supported";
        } else {
            if (!mBluetoothAdapter.isEnabled()) {
                return "Disabled";
            } else {
                return "Enabled";
            }
        }
    }
    
    public int getBatteryLevel(){
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = webView.getContext().registerReceiver(null, ifilter);

        return batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
    }
    
    private String getModelName(){
        return Build.MODEL;
    }
    private String getOSVersion() {
        return Build.VERSION.RELEASE;
    }
    private String getDeviceName(){
        return Build.PRODUCT;
    }
    private String getManufacturer(){
        return Build.MANUFACTURER;
    }
    private String getBuildNumber() {
        return Build.FINGERPRINT;
    }
    private String getKernelVersion(){
        return System.getProperty("os.version");
    }
    private String getImeiDefault(){
        TelephonyManager manager = (TelephonyManager) webView.getContext().getSystemService(Context.TELEPHONY_SERVICE);
        return manager.getDeviceId();
    }
    private String getImeiDualFirst(){
        TelephonyManager manager = (TelephonyManager) webView.getContext().getSystemService(Context.TELEPHONY_SERVICE);
        return manager.getDeviceId(0); // may null
    }
    // you can use this function to check dual sim, if return null then it's single
    private String getImeiDualSecond(){
        TelephonyManager manager = (TelephonyManager) webView.getContext().getSystemService(Context.TELEPHONY_SERVICE);
        return manager.getDeviceId(1);   // may null
    }    
    
    
    //    https://gist.github.com/flawyte/efd23dd520fc2320f94ba003b9aabfce
    public static String getSerialNumber(){
        String serialNumber;

        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);

            // (?) Lenovo Tab (https://stackoverflow.com/a/34819027/1276306)
            serialNumber = (String) get.invoke(c, "gsm.sn1");

            if (serialNumber.equals(""))
                // Samsung Galaxy S5 (SM-G900F) : 6.0.1
                // Samsung Galaxy S6 (SM-G920F) : 7.0
                // Samsung Galaxy Tab 4 (SM-T530) : 5.0.2
                // (?) Samsung Galaxy Tab 2 (https://gist.github.com/jgold6/f46b1c049a1ee94fdb52)
                serialNumber = (String) get.invoke(c, "ril.serialnumber");

            if (serialNumber.equals(""))
                // Archos 133 Oxygen : 6.0.1
                // Google Nexus 5 : 6.0.1
                // Hannspree HANNSPAD 13.3" TITAN 2 (HSG1351) : 5.1.1
                // Honor 5C (NEM-L51) : 7.0
                // Honor 5X (KIW-L21) : 6.0.1
                // Huawei M2 (M2-801w) : 5.1.1
                // (?) HTC Nexus One : 2.3.4 (https://gist.github.com/tetsu-koba/992373)
                serialNumber = (String) get.invoke(c, "ro.serialno");

            if (serialNumber.equals(""))
                // (?) Samsung Galaxy Tab 3 (https://stackoverflow.com/a/27274950/1276306)
                serialNumber = (String) get.invoke(c, "sys.serialnumber");

            if (serialNumber.equals(""))
                // Archos 133 Oxygen : 6.0.1
                // Hannspree HANNSPAD 13.3" TITAN 2 (HSG1351) : 5.1.1
                // Honor 9 Lite (LLD-L31) : 8.0
                // Xiaomi Mi 8 (M1803E1A) : 8.1.0
                serialNumber = android.os.Build.SERIAL;

            // If none of the methods above worked
            if (serialNumber.equals(""))
                serialNumber = "unknown";
        } catch (Exception e) {
            e.printStackTrace();
            serialNumber = "unknown";
        }

        return serialNumber;
    }
}
