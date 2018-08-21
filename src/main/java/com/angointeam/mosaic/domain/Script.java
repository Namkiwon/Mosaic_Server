package com.angointeam.mosaic.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.*;
import org.springframework.data.jpa.repository.Modifying;


import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

    @Column(columnDefinition = "VARCHAR(191)",name = "uuid", updatable = false, nullable = false,unique = true)
    private String uuid;

    @Column(name="content")
    private String content;

    @OneToOne
    @JoinColumn(name = "writerUuid",referencedColumnName = "uuid")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler","authKey","authenticated","createdAt","updatedAt","enabled","authorities","accountNonExpired","accountNonLocked","credentialsNonExpired","password","email","myScriptCnt","myScrapCnt"})
    private Member writer;

    @Column(name = "imgUrls")
    @ElementCollection
    @CollectionTable(name = "scriptImgUrl", joinColumns = {@JoinColumn(name = "idx")})
    private List<String> imgUrls;

    @Column(name = "thumbnailUrls")
    @ElementCollection
    @CollectionTable(name = "thumbnailUrls", joinColumns = {@JoinColumn(name = "idx")})
    private List<String> thumbnailUrls;

    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false, updatable=false)
    @CreationTimestamp
    private Date created;

    @Column
    private boolean valid;

    @OneToOne
    @JoinColumn(name = "categoryUuid",referencedColumnName = "uuid")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler","idx","uuid","valid"})
    private Category category;

    //Reply 갯수 넣을 부분
    @Formula("(select count(*) from replies r where r.valid  = true and r.script_uuid = uuid)")
    private int replies;

    @Transient
    private boolean scrap;

    @Transient
    private Long createdAt;

    public Long getCreatedAt(){
        return created.getTime();
    }


    public Script(){}
    public Script(String uuid ,String content, Category category, Member writer, List<String> imgUrls,List<String> thumbnailUrls){
        this.uuid = uuid;
        this.content = content;
        this.category = category;
        this.writer = writer;
        this.imgUrls = imgUrls;
        this.thumbnailUrls = thumbnailUrls;
        this.valid = true;
    }

}



