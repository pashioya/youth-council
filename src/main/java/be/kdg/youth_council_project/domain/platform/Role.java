package be.kdg.youth_council_project.domain.platform;

import lombok.Getter;
public enum Role {
    YOUTH_COUNCIL_ADMIN("ROLE_YOUTH_COUNCIL_ADMIN"), USER("ROLE_USER"), YOUTH_COUNCIL_MODERATOR("ROLE_YOUTH_COUNCIL_MODERATOR");
    private final String code;


    public String getCode() {
        return code;
    }

    Role(String code) {
        this.code = code;
    }
}
