package tech.minthura.caresdk.model;

public class NotificationMessageEvent {

    private final String title;
    private final String message;

    public NotificationMessageEvent(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

}
