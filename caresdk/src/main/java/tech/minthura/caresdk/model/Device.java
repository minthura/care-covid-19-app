package tech.minthura.caresdk.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Device {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("fcmToken")
    @Expose
    private String fcmToken;
    @SerializedName("deviceType")
    @Expose
    private String deviceType;
    @SerializedName("manufacturer")
    @Expose
    private String manufacturer;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("appVersionCode")
    @Expose
    private int appVersionCode;

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setAppVersionCode(int appVersionCode) {
        this.appVersionCode = appVersionCode;
    }

    public String getId() {
        return id;
    }
}
