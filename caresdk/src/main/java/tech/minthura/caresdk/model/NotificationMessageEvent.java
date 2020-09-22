package tech.minthura.caresdk.model;

public class NotificationMessageEvent {

    private final String title;
    private final String message;
    private final String type;
    public enum NotificationType { GENERIC, NEW_CASE }

    public NotificationMessageEvent(String title, String message, String type) {
        this.title = title;
        this.message = message;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public NotificationType getType() {
        try {
            return NotificationType.valueOf(type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return NotificationType.GENERIC;
    }

}
