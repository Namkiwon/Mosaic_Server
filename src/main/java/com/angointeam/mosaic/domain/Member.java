package com.angointeam.mosaic.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "member")
@EntityListeners(AuditingEntityListener.class)
public class Member implements UserDetails {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "VARCHAR(100)", unique = true, nullable = false)
    private String uuid;

    @Column(columnDefinition = "VARCHAR(10)")
    private String nick;

    @JsonIgnore
    @Column(columnDefinition = "VARCHAR(100)", updatable = false, nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @Column(columnDefinition = "VARCHAR(100)", updatable = false, nullable = false)
    private String authKey;

    @JsonIgnore
    private boolean authenticated = false;

    @JsonIgnore
    @Column(columnDefinition = "VARCHAR(15)")
    private String emailKey;

    @ManyToOne
    private University university;

    @JsonIgnore
    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date created;

    @JsonIgnore
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updated;

    @Transient
    private Long createdAt;

    @Transient
    private Long updatedAt;

    public Long getCreatedAt() {
        return created.getTime();
    }

    public Long getUpdatedAt() {
        return updated.getTime();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }


    @JsonIgnore
    @Override
    public String getPassword() {
        return authKey;
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return uuid;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
