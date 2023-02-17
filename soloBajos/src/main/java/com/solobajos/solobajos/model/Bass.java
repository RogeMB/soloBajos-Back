package com.solobajos.solobajos.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.solobajos.solobajos.converter.EnumSetRolesConverter;
import com.solobajos.solobajos.converter.EnumSetStateConverter;
import com.solobajos.solobajos.converter.YearAttributeConverter;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity(name="Bass")
@Table(name="bass_entity")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Bass {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(
                            name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionFourStrategy"
                    )
            }
    )
    @Column(columnDefinition = "uuid")
    private UUID id;

    private String brand;

    private String model;

    private int frets;

    private String image;

    private String origin;

    @Column(
            name = "built_year",
            columnDefinition = "smallint"
    )
    @Convert(converter = YearAttributeConverter.class)
    private Year builtYear;

    private String discount;

    private double price;

    @Column(name = "is_available")
    private Boolean isAvailable;

   // @ElementCollection(fetch = FetchType.EAGER)
    @Convert(converter = EnumSetStateConverter.class)
    private Set<BassState> state;

    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "last_modified_date_at")
    @LastModifiedDate
    private LocalDateTime lastModifiedDateAt;
    @JsonIgnore
    public static String hiddenFields = "id,";


    @ManyToOne
    @JoinColumn(name = "categoria_id",
            foreignKey = @ForeignKey(name = "FK_BASS_CATEGORIA"))
    private Categoria categoria;


    @ManyToMany(mappedBy = "bassList", fetch = FetchType.LAZY)
    private List<User> userList;

    // Helper de la asociación con Categoria
    public void addToCategoria(Categoria c) {
        categoria = c;
        c.getBassList().add(this);
    }
    public void removeFromCategoria(Categoria c) {
        categoria = null;
        c.getBassList().remove(this);
    }
}
