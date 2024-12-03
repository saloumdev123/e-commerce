package sen.saloum.saloum_service.domain;

import jakarta.persistence.*;
import sen.saloum.saloum_service.models.Roles;

import javax.management.relation.Role;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String motDePasse;

    private String adresse;

    private String telephone;

    @Enumerated(EnumType.STRING)
    private Roles role;

    private LocalDateTime dateCreation;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vente> ventes; // Liste des ventes effectuées par l'utilisateur

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Commande> commandes; // Liste des commandes effectuées par l'utilisateur


}
