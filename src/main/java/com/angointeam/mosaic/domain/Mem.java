package com.angointeam.mosaic.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@ToString
@Entity
@Table(name = "member",indexes = @Index(columnList = "uuid"))
public class Mem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", updatable = false, nullable = false)
    private Long idx;


//    @Type(type="uuid-char")
    @Column(name = "uuid", updatable = false, nullable = false,unique = true)
    private String uuid;

    public Mem(){}
    public Mem(String uuid){
        this.uuid = uuid;
    }
}
