package lk.ijse.gdse.weavy_test.entity;

import jakarta.persistence.*;
import lk.ijse.gdse.weavy_test.enums.Metadata;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity implements UserDetails {
    @Id
    private String uid;
    private String email;
    private String givenName;
    private String middleName;
    private String name;
    private String familyName;
    private String nickname;
    private String phoneNumber;
    private String comment;
    private String picture;
    private String directory;
    private Metadata metadata;

    @ElementCollection
    private List<String> tags;

    private boolean isSuspended;
    private boolean isBot;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !isSuspended;
    }
}
