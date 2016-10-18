package com.opensettings;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class OpenSettings extends ReactContextBaseJavaModule {

    private ReactContext reactContext;

    public OpenSettings(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNOpenSettings";
    }

    @ReactMethod
    public void openAppPermissionsSettings() {
      final Intent i = new Intent();
      i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
      i.addCategory(Intent.CATEGORY_DEFAULT);
      i.setData(Uri.parse("package:" + reactContext.getPackageName()));
      i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
      i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
      reactContext.startActivity(i);
    }

    @ReactMethod
    public void openLocationSettings() {
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        reactContext.startActivity(i);
    }

    @ReactMethod
    public void isLocationEnabled(Promise promise) {
        try {
            boolean locationEnabled = this._isLocationEnabled();
            promise.resolve(locationEnabled);
        }
        catch (Exception e) {
            promise.reject(e);
        }
    }

    private boolean _isLocationEnabled() throws Settings.SettingNotFoundException {
      int locationMode = 0;
      String locationProviders;
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
         try {
           locationMode = Settings.Secure.getInt(this.reactContext.getContentResolver(), Settings.Secure.LOCATION_MODE);
          } catch (Settings.SettingNotFoundException e) {
           throw e;
         }
         return locationMode != Settings.Secure.LOCATION_MODE_OFF;
       } else{
         locationProviders = Settings.Secure.getString(this.reactContext.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
         return !TextUtils.isEmpty(locationProviders);
       }
    }
    //endregion
}
