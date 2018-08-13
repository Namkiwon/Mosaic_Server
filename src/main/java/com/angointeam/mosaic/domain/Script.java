package com.angointeam.mosaic.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;


import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@Table(name = "scripts",indexes = @Index(columnList = "uuid"))
public class Script {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", updatable = false, nullable = false)
    private Long idx;

    @Type(type="uuid-char")
    @Column(name = "uuid", updatable = false, nullable = false,unique = true)
    private UUID uuid;

    @Column
    private String content;

    @Column(name= "writerUuid")
    private String writerUuid;

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


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_uuid",referencedColumnName = "uuid")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Category category;


    public Script(){}
    public Script(UUID uuid ,String content, Category category, String writerUuid, List<String> imgUrls){
        this.uuid = uuid;
        this.content = content;
        this.category = category;
        this.writerUuid = writerUuid;
        this.imgUrls = imgUrls;
    }

}



