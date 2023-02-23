package com.solobajos.solobajos.model;


import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.hibernate.annotations.Parameter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = "Categoria")
@Table(name="categoria_entity")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Categoria {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @Parameter(
                            name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionFourStrategy"
                    )
            }
    )
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id;

    private String name;

    @Builder.Default
    private String image = "categoriaDefault.png";

    @Builder.Default
    @OneToMany(mappedBy = "categoria", fetch = FetchType.LAZY)
    private List<Bass> bassList = new ArrayList<>();

    @PreRemove
    public void preDeleteCategoria (){
        bassList.forEach(bajo -> bajo.setCategoria(null));
        setBassList(new ArrayList<>());
    }
}
