package sen.saloum.saloum_service.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import sen.saloum.saloum_service.models.enums.VenteStatus;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private OffsetDateTime dateVente;

    @Column(nullable = false)
    private Double montantTotal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VenteStatus statut;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Utilisateur client;

    @OneToMany(mappedBy = "vente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LigneVente> lignes;

}
