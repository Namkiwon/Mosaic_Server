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

    @Column(columnDefinition = "VARCHAR(191)")
    private String name;

    @Column(columnDefinition = "VARCHAR(191)")
    private String domain;

    @Column(columnDefinition = "VARCHAR(191)")
    private String imgUrl;
}
