package by.itacademy.user.core.enums;

public enum ActionForAudit {
    REGISTRATION("registrated"),
    VERIFICATION("verificated"),
    LOGIN("logged in"),
    UPDATE_USER("updated"),
    SAVE_USER("saved by admin"),
    INFO_ABOUT_ALL_USERS("information about all users"),
    INFO_ABOUT_USER_BY_ID("information about users by uuid"),
    INFO_ABOUT_ME("information about me");

    private final String action;

    ActionForAudit(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}
