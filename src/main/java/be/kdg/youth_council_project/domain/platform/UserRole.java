package be.kdg.youth_council_project.domain.platform;

public enum UserRole {
    GENERAL_ADMINISTRATOR("ROLE_GENERAL_ADMINISTRATOR"),
    USER("ROLE_USER"),
    YOUTH_COUNCIL_MODERATOR("ROLE_YOUTH_COUNCIL_MODERATOR"),
    YOUTH_COUNCIL_ADMINISTRATOR("ROLE_YOUTH_COUNCIL_ADMINISTRATOR");

    private final String code;


    public String getCode() {
        return code;
    }

    UserRole(String code) {
        this.code = code;
    }
}
