package com.angointeam.mosaic.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Session;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Type;
import org.springframework.web.bind.annotation.SessionAttribute;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@Table(name = "scripts",indexes = @Index(columnList = "uuid"))
public class Script implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", updatable = false, nullable = false)
    private Long idx;

    @Column(name = "uuid", updatable = false, nullable = false,unique = true)
    private String uuid;

    @Column(name="content")
    private String content;

    @OneToOne
    @JoinColumn(name = "writerUuid",referencedColumnName = "uuid")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Mem writer;

    @Column(name = "imgUrls")
    @ElementCollection
    @CollectionTable(name = "scriptImgUrl", joinColumns = {@JoinColumn(name = "uuid")})
    private List<String> imgUrls;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false, updatable=false)
    @CreationTimestamp
    private Date created;

    @Column
    private String valid;


    @OneToOne
    @JoinColumn(name = "categoryUuid",referencedColumnName = "uuid")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Category category;

    @Formula("(select count(*) from categories c where c.name = '아르바이트')")
      private int replies;
//    @Formula("(select 1 from categories c where exists c.name = 'asdf')")
    @Transient
    private String memberUuid;

    public Script(){}
    public Script(String uuid ,String content, Category category, Mem writer, List<String> imgUrls){
        this.uuid = uuid;
        this.content = content;
        this.category = category;
        this.writer = writer;
        this.imgUrls = imgUrls;
    }

}



