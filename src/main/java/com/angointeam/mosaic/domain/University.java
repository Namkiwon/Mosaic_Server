package com.angointeam.mosaic.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "university")
@EntityListeners(AuditingEntityListener.class)
public class University {

    @Id
    @GeneratedValue
    private long idx;

    private String name;

    private String domain;

    private String imgUrl;
}
