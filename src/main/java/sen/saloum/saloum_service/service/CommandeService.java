package sen.saloum.saloum_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sen.saloum.saloum_service.domain.*;
import sen.saloum.saloum_service.exception.ResourceNotFoundException;
import sen.saloum.saloum_service.models.dto.*;
import sen.saloum.saloum_service.repos.CommandeRepository;
import sen.saloum.saloum_service.repos.LigneCommandeRepository;
import sen.saloum.saloum_service.repos.UtilisateurRepository;
import sen.saloum.saloum_service.service.interfaces.ICommande;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommandeService implements ICommande {


        private final CommandeRepository commandeRepository;
        private final LigneCommandeRepository ligneCommandeRepository;
        private final UtilisateurRepository utilisateurRepository;

        @Override
        public CommandeDto createCommande(CommandeDto commandeDto) {
            Commande commande = new Commande();
            commande.setDateCommande(commandeDto.getDateCommande());
            commande.setMontantTotal(commandeDto.getMontantTotal());
            commande.setStatut(commandeDto.getStatut());

            if (commandeDto.getUtilisateurDto() != null) {
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setNom(commandeDto.getUtilisateurDto().getNom());
                utilisateur.setPrenom(commandeDto.getUtilisateurDto().getPrenom());
                utilisateur.setEmail(commandeDto.getUtilisateurDto().getEmail());
                utilisateur.setAdresse(commandeDto.getUtilisateurDto().getAdresse());
                utilisateur.setTelephone(commandeDto.getUtilisateurDto().getTelephone());
                utilisateur.setMotDePasse(commandeDto.getUtilisateurDto().getMotDePasse());
                utilisateurRepository.save(utilisateur);
                commande.setClient(utilisateur); // Set Utilisateur in Commande
            }

            List<LigneCommande> ligneCommandes = commandeDto.getLignes().stream()
                    .map(ligneDto -> {
                        LigneCommande ligneCommande = new LigneCommande();
                        ligneCommande.setQuantite(ligneDto.getQuantite());
                        ligneCommande.setPrixUnitaire(ligneDto.getPrixUnitaire());
                        ligneCommande.setSousTotal(ligneDto.getSousTotal());

                        // Convert ProductDto to Product and set it
                        Product product = new Product();
                        product.setNom(ligneDto.getProduct().getNom());
                        product.setDescription(ligneDto.getProduct().getDescription());
                        product.setPrix(ligneDto.getProduct().getPrix());
                        product.setQuantiteEnStock(ligneDto.getProduct().getQuantiteEnStock());
                        ligneCommande.setProduct(product);

                        return ligneCommande;
                    })
                    .collect(Collectors.toList());

            commande.setLignes(ligneCommandes);
            Commande savedCommande = commandeRepository.save(commande);

            return mapToDto(savedCommande);
        }

        @Override
        public CommandeDto getCommandeById(Long id) {
            Commande commande = commandeRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Commande not found with id: " + id));
            return mapToDto(commande);
        }

        @Override
        public List<CommandeDto> getAllCommandes() {
            List<Commande> commandes = commandeRepository.findAll();
            return commandes.stream()
                    .map(this::mapToDto)
                    .collect(Collectors.toList());
        }

        @Override
        public CommandeDto updateCommande(Long id, CommandeDto commandeDto) {
            Commande existingCommande = commandeRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Commande not found with id: " + id));

            existingCommande.setDateCommande(commandeDto.getDateCommande());
            existingCommande.setMontantTotal(commandeDto.getMontantTotal());
            existingCommande.setStatut(commandeDto.getStatut());

            if (commandeDto.getUtilisateurDto() != null) {
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setNom(commandeDto.getUtilisateurDto().getNom());
                utilisateur.setPrenom(commandeDto.getUtilisateurDto().getPrenom());
                utilisateur.setEmail(commandeDto.getUtilisateurDto().getEmail());
                utilisateur.setAdresse(commandeDto.getUtilisateurDto().getAdresse());
                utilisateur.setTelephone(commandeDto.getUtilisateurDto().getTelephone());
                utilisateur.setMotDePasse(commandeDto.getUtilisateurDto().getMotDePasse());
                utilisateurRepository.save(utilisateur);
                existingCommande.setClient(utilisateur); // Set the updated Utilisateur
            }

            List<LigneCommande> ligneCommandes = commandeDto.getLignes().stream()
                    .map(ligneDto -> {
                        LigneCommande ligneCommande = new LigneCommande();
                        ligneCommande.setQuantite(ligneDto.getQuantite());
                        ligneCommande.setPrixUnitaire(ligneDto.getPrixUnitaire());
                        ligneCommande.setSousTotal(ligneDto.getSousTotal());

                        // Convert ProductDto to Product and set it
                        Product product = new Product();
                        product.setNom(ligneDto.getProduct().getNom());
                        product.setDescription(ligneDto.getProduct().getDescription());
                        product.setPrix(ligneDto.getProduct().getPrix());
                        product.setQuantiteEnStock(ligneDto.getProduct().getQuantiteEnStock());
                        ligneCommande.setProduct(product);

                        return ligneCommande;
                    })
                    .collect(Collectors.toList());

            existingCommande.setLignes(ligneCommandes);

            // Save updated Commande
            Commande updatedCommande = commandeRepository.save(existingCommande);
            return mapToDto(updatedCommande);
        }

        @Override
        public void deleteCommande(Long id) {
            Commande commande = commandeRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Commande not found with id: " + id));
            commandeRepository.delete(commande);
        }

        private CommandeDto mapToDto(Commande entity) {
            CommandeDto dto = new CommandeDto();
            dto.setId(entity.getId());
            dto.setDateCommande(entity.getDateCommande());
            dto.setMontantTotal(entity.getMontantTotal());
            dto.setStatut(entity.getStatut());

            if (entity.getClient() != null) {
                UtilisateurDto utilisateurDto = new UtilisateurDto();
                utilisateurDto.setNom(entity.getClient().getNom());
                utilisateurDto.setPrenom(entity.getClient().getPrenom());
                utilisateurDto.setEmail(entity.getClient().getEmail());
                utilisateurDto.setAdresse(entity.getClient().getAdresse());
                utilisateurDto.setTelephone(entity.getClient().getTelephone());
                utilisateurDto.setMotDePasse(entity.getClient().getMotDePasse());
                dto.setUtilisateurDto(utilisateurDto); // Set UtilisateurDto
            }

            dto.setLignes(entity.getLignes().stream()
                    .map(ligne -> {
                        LigneCommandeDto ligneDto = new LigneCommandeDto();
                        ligneDto.setQuantite(ligne.getQuantite());
                        ligneDto.setPrixUnitaire(ligne.getPrixUnitaire());
                        ligneDto.setSousTotal(ligne.getSousTotal());

                        ProductDto productDto = new ProductDto();
                        productDto.setNom(ligne.getProduct().getNom());
                        productDto.setDescription(ligne.getProduct().getDescription());
                        productDto.setPrix(ligne.getProduct().getPrix());
                        productDto.setQuantiteEnStock(ligne.getProduct().getQuantiteEnStock());
                        ligneDto.setProduct(productDto);

                        return ligneDto;
                    })
                    .collect(Collectors.toList()));
            return dto;
        }


}
