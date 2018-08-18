package com.angointeam.mosaic.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "replies")
@EntityListeners(AuditingEntityListener.class)
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private long idx;

    @Column(updatable = false, nullable = false, unique = true)
    private String uuid;

    @OneToOne
    private Member writer;

    private int depth;

    @Transient
    private String scriptUuid;

    @Transient
    private String upperReplyUuid;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "scriptUuid", referencedColumnName = "uuid")
    private Script script;

    @ManyToOne
    @JsonIgnore
    private Reply upperReply;

    @OneToMany
    private List<Reply> childReplies;

    private String content;

    private String imgUrl;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;

    @Column
    private boolean valid;

    public String getScriptUuid() {
        if (script == null) return "";
        return script.getUuid();
    }

    public String getUpperReplyUuid() {
        if (upperReply == null) return "";
        return upperReply.getUuid();
    }

    public void setUpperReplyUuid(String upperReplyUuid) {
        this.upperReplyUuid = upperReplyUuid;
    }
}
