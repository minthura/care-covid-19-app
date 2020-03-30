package tech.minthura.caresdk.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TotalStats {

    @SerializedName("cases")
    @Expose
    private Integer cases;
    @SerializedName("deaths")
    @Expose
    private Integer deaths;
    @SerializedName("recovered")
    @Expose
    private Integer recovered;
    @SerializedName("updated")
    @Expose
    private Long updated;

    public Integer getCases() {
        return cases;
    }

    public Integer getDeaths() {
        return deaths;
    }

    public Integer getRecovered() {
        return recovered;
    }

    public Long getUpdated() {
        return updated;
    }
}
