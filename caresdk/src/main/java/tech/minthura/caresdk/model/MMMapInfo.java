package tech.minthura.caresdk.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MMMapInfo {

    @SerializedName("hospitals")
    @Expose
    private ArrayList<Hospital> hospitals;
    @SerializedName("mmInfo")
    @Expose
    private MMInfo mmInfo;

    public ArrayList<Hospital> getHospitals() {
        return hospitals;
    }

    public MMInfo getMmInfo() {
        return mmInfo;
    }

    public static class Hospital {

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

    public static class MMInfo {
        @SerializedName("total")
        @Expose
        private Integer total;
        @SerializedName("pui")
        @Expose
        private Integer pui;
        @SerializedName("suspected")
        @Expose
        private Integer suspected;
        @SerializedName("confirm")
        @Expose
        private Integer confirm;
        @SerializedName("lab_negative")
        @Expose
        private Integer labNegative;
        @SerializedName("lab_pending")
        @Expose
        private Integer labPending;
        @SerializedName("discharge")
        @Expose
        private Integer discharge;
        @SerializedName("deaths")
        @Expose
        private String deaths;
        @SerializedName("recovered")
        @Expose
        private String recovered;
        @SerializedName("quarantine")
        @Expose
        private String quarantine;
        @SerializedName("lastupdated")
        @Expose
        private String lastupdated;
        @SerializedName("dt")
        @Expose
        private String dt;

        public Integer getTotal() {
            return total;
        }

        public Integer getPui() {
            return pui;
        }

        public Integer getSuspected() {
            return suspected;
        }

        public Integer getConfirm() {
            return confirm;
        }

        public Integer getLabNegative() {
            return labNegative;
        }

        public Integer getLabPending() {
            return labPending;
        }

        public Integer getDischarge() {
            return discharge;
        }

        public String getDeaths() {
            return deaths;
        }

        public String getRecovered() {
            return recovered;
        }

        public String getQuarantine() {
            return quarantine;
        }

        public String getLastupdated() {
            return lastupdated;
        }

        public String getDt() {
            return dt;
        }
    }

}