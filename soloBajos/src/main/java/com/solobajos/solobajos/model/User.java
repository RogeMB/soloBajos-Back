package com.solobajos.solobajos.model;

import com.solobajos.solobajos.converter.EnumSetRolesConverter;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.annotation.CreatedDate;
import org.hibernate.annotations.Parameter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
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
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id;
    @NaturalId
    @Column(unique = true, updatable = false)
    private String username;

    @Column(name = "full_name")
    private String fullName;

    private String email;
    private String password;

    @Builder.Default
    private String avatar = "userDefault.png";
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
    private EnumSet<UserRole> roles;

    @CreatedDate
    @Column(name = "created_at")
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "last_modified_date_at")
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDateTime lastModifiedDateAt;

    @Builder.Default
    @Column(name = "last_password_change_at")
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDateTime lastPasswordChangeAt = LocalDateTime.now();

    @ManyToMany(fetch = FetchType.LAZY)
    @Builder.Default
    @JoinTable(joinColumns = @JoinColumn(name = "user_id",
            foreignKey = @ForeignKey(name="FK_BASS_USER_ENTITY_USER")),
            inverseJoinColumns = @JoinColumn(name = "bass_id",
                    foreignKey = @ForeignKey(name="FK_BASS_USER_ENTITY_BASS")),
            name = "BASS_USER_ENTITY"
    )
    private List<Bass> bassList = new ArrayList<>();

    // Helper de la asociación con Bass
    public void addToBassFavList(Bass b) {
        b.getUserList().add(this);
        this.getBassList().add(b);
    }
    public void removeFromBassListFav(Bass b) {
        b.getUserList().remove(this);
        this.getBassList().remove(b);
    }

    @PreRemove
    public void preRemoveFav(){
        this.getBassList().forEach(bass -> {
            bass.getUserList().remove(this);
        });
    }

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
