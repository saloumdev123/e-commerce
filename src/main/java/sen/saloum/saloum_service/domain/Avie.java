package sen.saloum.saloum_service.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Avie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String commentaire;

    @Column(nullable = false)
    private Integer note;

    @Column(nullable = false)
    private OffsetDateTime dateCreation;

    @ManyToOne
    @JoinColumn(name = "produit_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;
}
