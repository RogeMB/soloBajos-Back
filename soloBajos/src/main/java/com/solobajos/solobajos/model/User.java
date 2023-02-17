package com.solobajos.solobajos.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.solobajos.solobajos.converter.EnumSetRolesConverter;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.annotation.CreatedDate;
import org.hibernate.annotations.Parameter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity(name = "User")
@Table(name="user_entity")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @Parameter(
                            name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionFourStrategy"
                    )
            }
    )
    @Column(columnDefinition = "uuid")
    private UUID id;
    @NaturalId
    @Column(unique = true, updatable = false)
    private String username;

    private String fullName;

    private String email;
    private String password;

    private String avatar;

    @Column(name = "birth_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate birthDate;
    @Builder.Default
    @Column(name = "account_non_expired")
    private boolean accountNonExpired = true;
    @Builder.Default
    @Column(name = "account_non_locked")
    private boolean accountNonLocked = true;
    @Builder.Default
    @Column(name = "credentials_non_expired")
    private boolean credentialsNonExpired = true;
    @Builder.Default
    private boolean enabled = true;
   // @ElementCollection(fetch = FetchType.EAGER)
    @Convert(converter = EnumSetRolesConverter.class)
    private Set<UserRole> roles;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "last_modified_date_at")
    private LocalDateTime lastModifiedDateAt;

    @Builder.Default
    private LocalDateTime lastPasswordChangeAt = LocalDateTime.now();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(joinColumns = @JoinColumn(name = "user_id",
            foreignKey = @ForeignKey(name="FK_BASS_USER")),
            inverseJoinColumns = @JoinColumn(name = "bass_id",
                    foreignKey = @ForeignKey(name="FK_USER_BASS")),
            name = "bajos"
    )
    private List<Bass> bassList = new ArrayList<>();



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> "ROLE_" + role)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

}
