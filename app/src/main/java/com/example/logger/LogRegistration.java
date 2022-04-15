package com.example.logger;

import android.content.ContentValues;
import android.content.Context;

import com.sonyericsson.extras.liveware.aef.registration.Registration;
import com.sonyericsson.extras.liveware.aef.sensor.Sensor;
import com.sonyericsson.extras.liveware.extension.util.ExtensionUtils;
import com.sonyericsson.extras.liveware.extension.util.sensor.AccessorySensor;

public class LogRegistration extends com.sonyericsson.extras.liveware.extension.util.registration.RegistrationInformation {

    private final Context mContext;

    private static final String LOG_TAG = "SmartBandLogger";
    private final String CLASS = getClass().getSimpleName();

    protected LogRegistration(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context == null");
        }
        mContext = context;
    }

    @Override
    public int getRequiredControlApiVersion() { return 1; }

    @Override
    public int getRequiredSensorApiVersion() { return 1; }

    @Override
    public int getRequiredNotificationApiVersion() {
        return com.sonyericsson.extras.liveware.extension.util.registration.RegistrationInformation.API_NOT_REQUIRED;
    }

    @Override
    public int getRequiredWidgetApiVersion() {
        return com.sonyericsson.extras.liveware.extension.util.registration.RegistrationInformation.API_NOT_REQUIRED;
    }

    /**
     * Get the extension registration information.
     *
     * @return The registration configuration.
     */

    @Override
    public ContentValues getExtensionRegistrationConfiguration() {
        //String iconHostapp = ExtensionUtils.getUriString(mContext, R.drawable.icon);
        //String iconExtension = ExtensionUtils.getUriString(mContext, R.drawable.icon);

        ContentValues values = new ContentValues();

        return null;
    }
}
