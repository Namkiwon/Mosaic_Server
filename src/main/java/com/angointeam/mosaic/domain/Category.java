package com.angointeam.mosaic.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@Table(name="categories")
//@IdClass(CategoryPK.class)
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
//
//    @Id
//    @Type(type="uuid-char")
//    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(
//            name = "UUID",
//            strategy = "org.hibernate.id.UUIDGenerator"
//    )
//    @Column(name = "uuid",insertable = false, updatable = false, nullable = false,unique = true)
//    private UUID uuid;



    @Column
    private String name;

    @Column
    private String emoji;

    public Category(){}

    public Category(String name, String emoji){
        this.name = name;
        this.emoji = emoji;
    }

}
