package com.angointeam.mosaic.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "university")
public class University {

    @Id
    @GeneratedValue
    private long idx;

    private String name;

    private String domain;

    private String imgUrl;
}
