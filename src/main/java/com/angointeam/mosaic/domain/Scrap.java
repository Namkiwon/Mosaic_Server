package com.angointeam.mosaic.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Member;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@Table(name = "scrap",uniqueConstraints=@UniqueConstraint(columnNames={"scriptUuid","memberUuid"}),indexes = @Index( columnList="scriptUuid,memberUuid"))
public class Scrap implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", updatable = false, nullable = false)
    private Long idx;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(columnDefinition = "VARCHAR(191)",name = "scriptUuid",referencedColumnName = "uuid")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Script script;

    @Column(columnDefinition = "VARCHAR(191)",name = "memberUuid")
    private String memberUuid = "";

    public Scrap(){}
    public Scrap(Script script, String memberUuid){
        this.script = script;
        this.memberUuid = memberUuid;
    }

}
