package com.angointeam.mosaic.domain;

import com.fasterxml.jackson.annotation.JacksonAnnotation;
import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.ManyToAny;


import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@Table(name = "scripts")
@IdClass(ScriptPK.class)
public class Script {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idx", updatable = false, nullable = false)
    private long idx;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "uuid", updatable = false, nullable = false)
    private UUID uuid;

    @Column
    private String content;


//    @Column(name= "category_uuid")
//    private String categoryUuid;

    @Column(name= "writer_uuid")
    private String writerUuid;

    @Column(name = "imgUrls")
    @ElementCollection
    @CollectionTable(name = "script_img_url", joinColumns = {@JoinColumn(name = "idx"), @JoinColumn(name = "uuid")})
    private List<String> imgUrls;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false, updatable=false)
    @CreationTimestamp
    private Date created;

    @Column
    private String valid;


//    @MapsId
//    @OneToOne
//    @JoinColumn(name = "category_uuid",referencedColumnName = "uuid",insertable = false, unique = true)
////    @OneToOne(fetch = FetchType.LAZY,
////        cascade =  CascadeType.ALL,
////        mappedBy = "uuid")
//    @OneToOne(cascade = CascadeType.ALL)


//    @JoinColumns( {
//            @JoinColumn(name = "category_id", referencedColumnName = "id"),
//            @JoinColumn(name = "category_uuid", referencedColumnName = "uuid")
//    })
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Category category;


    public Script(){}
    public Script(String content, Category category, String writerUuid, List<String> imgUrls){
        this.content = content;
//        this.categoryUuid = categoryUuid;
        this.category = category;
        this.writerUuid = writerUuid;
        this.imgUrls = imgUrls;
    }

}



