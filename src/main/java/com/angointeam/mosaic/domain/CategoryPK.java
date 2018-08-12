package com.angointeam.mosaic.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

public class CategoryPK implements Serializable {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "id", updatable = false, nullable = false)
//    private long id;
//    //
//    @Id
//    @Type(type="uuid-char")
//    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(
//            name = "UUID",
//            strategy = "org.hibernate.id.UUIDGenerator"
//    )
//    @Column(name = "uuid",insertable = false, updatable = false, nullable = false,unique = true)
//    private UUID uuid;

    private long id;
    private UUID uuid;
}
