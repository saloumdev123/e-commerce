package sen.saloum.saloum_service.domain;

import sen.saloum.saloum_service.models.ModePaiement;
import sen.saloum.saloum_service.models.StatutPaiement;

import java.time.LocalDateTime;


public class Payment {

    private Long id;
    private Long commandeId;
    private ModePaiement modePaiement;
    private StatutPaiement statut;
    private LocalDateTime datePaiement;
    private Double montant;
}
