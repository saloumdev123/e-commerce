package sen.saloum.saloum_service.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Stocke {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer quantiteDisponible;

    @Column(nullable = false)
    private Integer seuilAlerte;

    @OneToOne
    @JoinColumn(name = "produit_id", nullable = false)
    private Product product;
}
