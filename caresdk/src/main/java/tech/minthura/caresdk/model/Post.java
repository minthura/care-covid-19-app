package tech.minthura.caresdk.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import me.myatminsoe.mdetect.MDetect;

public class Post {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("description")
    @Expose
    private String description;

    public String getTitle() {
        return MDetect.INSTANCE.getText(title);
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

}
