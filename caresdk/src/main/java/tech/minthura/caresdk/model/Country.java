package tech.minthura.caresdk.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Country {

    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("countryInfo")
    @Expose
    private CountryInfo countryInfo;
    @SerializedName("cases")
    @Expose
    private Integer cases;
    @SerializedName("todayCases")
    @Expose
    private Integer todayCases;
    @SerializedName("deaths")
    @Expose
    private Integer deaths;
    @SerializedName("todayDeaths")
    @Expose
    private Integer todayDeaths;
    @SerializedName("recovered")
    @Expose
    private Integer recovered;
    @SerializedName("active")
    @Expose
    private Integer active;
    @SerializedName("critical")
    @Expose
    private Integer critical;
    @SerializedName("casesPerOneMillion")
    @Expose
    private Double casesPerOneMillion;
    @SerializedName("deathsPerOneMillion")
    @Expose
    private Double deathsPerOneMillion;

    public String getCountry() {
        return country;
    }

    public CountryInfo getCountryInfo() {
        return countryInfo;
    }

    public Integer getCases() {
        return cases;
    }

    public Integer getTodayCases() {
        return todayCases;
    }

    public Integer getDeaths() {
        return deaths;
    }

    public Integer getTodayDeaths() {
        return todayDeaths;
    }

    public Integer getRecovered() {
        return recovered;
    }

    public Integer getActive() {
        return active;
    }

    public Integer getCritical() {
        return critical;
    }

    public Double getCasesPerOneMillion() {
        return casesPerOneMillion;
    }

    public Double getDeathsPerOneMillion() {
        return deathsPerOneMillion;
    }

    public static class CountryInfo {

        @SerializedName("iso2")
        @Expose
        private String iso2;
        @SerializedName("iso3")
        @Expose
        private String iso3;
        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("lat")
        @Expose
        private Double lat;
        @SerializedName("long")
        @Expose
        private Double _long;
        @SerializedName("flag")
        @Expose
        private String flag;

        public String getIso2() {
            return iso2;
        }

        public String getIso3() {
            return iso3;
        }

        public String getId() {
            return id;
        }

        public Double getLat() {
            return lat;
        }

        public Double get_long() {
            return _long;
        }

        public String getFlag() {
            return flag;
        }
    }

}
