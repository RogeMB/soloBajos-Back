package com.solobajos.solobajos.model;

import com.solobajos.solobajos.converter.YearAttributeConverter;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;
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

    private double price;

    @Builder.Default
    private double discount = 0.0;

    @Column(name = "is_available")
    @Builder.Default
    private Boolean isAvailable = true;

   //@ElementCollection(fetch = FetchType.EAGER)
    /*
    @Convert(converter = EnumSetStateConverter.class)
    private EnumSet<BassState> state;
    */

    @Enumerated(EnumType.STRING)
    private BassState state;

    @Column(name = "created_at")
    @CreatedDate
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime createdAt;

    @Column(name = "last_modified_date_at")
    @LastModifiedDate
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime lastModifiedDateAt;

    // @JsonIgnore
    // public static String hiddenFields = "id,";


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
    @PreRemove
    public void preDeleteBass (){
        categoria.getBassList().remove(this);
    }

}
