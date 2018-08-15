package com.angointeam.mosaic.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Session;
import org.hibernate.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.SessionAttribute;


import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@Table(name = "scripts",indexes = @Index(columnList = "uuid"))
//@FilterDef(name="scrap", parameters={
//        @ParamDef( name="memberUuid", type="string" )
//        @ParamDef( name="scriptUuid", type="String" )
//})
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
    @CollectionTable(name = "scriptImgUrl", joinColumns = {@JoinColumn(name = "idx")})
    private List<String> imgUrls;

    @Column(name = "thumbnailUrls")
    @ElementCollection
    @CollectionTable(name = "thumbnailUrls", joinColumns = {@JoinColumn(name = "idx")})
    private List<String> thumbnailUrls;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false, updatable=false)
    @CreationTimestamp
    private Date created;

    @Column
    private String valid;

    @OneToOne
    @JoinColumn(name = "categoryUuid",referencedColumnName = "uuid")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler","idx","uuid"})
    private Category category;

    //Reply 갯수 넣을 부분
    @Filter(name="scrap")
    @Formula("(select count(*) from categories c where c.name = '아르바이트')")
    private int replies;

//    @OneToOne
//    @JoinTable(name="Scrap", joinColumns={@JoinColumn(name ="memberUuid")})
//    @FilterJoinTable(name="scrap", condition=":memberUuid = memberUuid")
    @Transient
    private boolean scrap;

    public Script(){}
    public Script(String uuid ,String content, Category category, Mem writer, List<String> imgUrls,List<String> thumbnailUrls){
        this.uuid = uuid;
        this.content = content;
        this.category = category;
        this.writer = writer;
        this.imgUrls = imgUrls;
        this.thumbnailUrls = thumbnailUrls;
    }

}



