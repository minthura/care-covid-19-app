package tech.minthura.caresdk.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateCheck {

    @SerializedName("force")
    @Expose
    private boolean force;
    @SerializedName("changeLog")
    @Expose
    private String changeLog;
    @SerializedName("downloadUrl")
    @Expose
    private String downloadUrl;

    public boolean isForce() {
        return force;
    }

    public String getChangeLog() {
        return changeLog;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }
}
