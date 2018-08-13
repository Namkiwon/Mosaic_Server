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
@Table(name="categories",indexes = @Index(columnList = "uuid"))

public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", updatable = false, nullable = false)
    private Long idx;

    @Type(type="uuid-char")
    @Column(name = "uuid", updatable = false, nullable = false,unique = true)
    private UUID uuid;

    @Column
    private String name;

    @Column
    private String emoji;

    public Category(){}

    public Category(UUID uuid, String name, String emoji){
        this.uuid = uuid;
        this.name = name;
        this.emoji = emoji;
    }

}
