package tech.minthura.caresdk.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Hospital {

    @SerializedName("Hospital")
    @Expose
    private String hospital;
    @SerializedName("Township")
    @Expose
    private String township;
    @SerializedName("PUI")
    @Expose
    private Integer pui;
    @SerializedName("Suspected")
    @Expose
    private Integer suspected;
    @SerializedName("Confirmed")
    @Expose
    private Integer confirmed;
    @SerializedName("Death")
    @Expose
    private Integer death;
    @SerializedName("Recovered")
    @Expose
    private Integer recovered;
    @SerializedName("Pending")
    @Expose
    private Integer pending;
    @SerializedName("Latitude")
    @Expose
    private Double latitude;
    @SerializedName("Longitude")
    @Expose
    private Double longitude;

    public String getHospital() {
        return hospital;
    }

    public Integer getSuspected() {
        return suspected;
    }

    public Integer getConfirmed() {
        return confirmed;
    }

    public Integer getDeath() {
        return death;
    }

    public Integer getRecovered() {
        return recovered;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getTownship() {
        return township;
    }

    public Integer getPui() {
        return pui;
    }

    public Integer getPending() {
        return pending;
    }
}
