package sen.saloum.saloum_service.domain;

import sen.saloum.saloum_service.models.enums.ModePaiement;
import sen.saloum.saloum_service.models.enums.StatutPaiement;

import java.time.LocalDateTime;


public class Payment {

    private Long id;
    private Long commandeId;
    private ModePaiement modePaiement;
    private StatutPaiement statut;
    private LocalDateTime datePaiement;
    private Double montant;
}
