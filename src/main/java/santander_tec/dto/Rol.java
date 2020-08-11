package santander_tec.dto;

import org.springframework.security.core.GrantedAuthority;

public enum Rol implements GrantedAuthority {
    USER, ADMIN;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
