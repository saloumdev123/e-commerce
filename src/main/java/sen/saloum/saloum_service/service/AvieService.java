package sen.saloum.saloum_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sen.saloum.saloum_service.domain.Avie;
import sen.saloum.saloum_service.domain.Product;
import sen.saloum.saloum_service.domain.Utilisateur;
import sen.saloum.saloum_service.models.dto.AvieDto;
import sen.saloum.saloum_service.models.dto.ProductDto;
import sen.saloum.saloum_service.models.dto.UtilisateurDto;
import sen.saloum.saloum_service.repos.AvieRepository;
import sen.saloum.saloum_service.repos.ProductRepository;
import sen.saloum.saloum_service.repos.UtilisateurRepository;
import sen.saloum.saloum_service.service.interfaces.IAvie;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvieService implements IAvie {

    private final AvieRepository avieRepository;
    private final ProductRepository productRepository;
    private final UtilisateurRepository utilisateurRepository;

    @Override
    public List<AvieDto> allAvies() {
        List<Avie> avies = avieRepository.findAll();
        return avies.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public Optional<AvieDto> getById(Long id) {
        Optional<Avie> avie = avieRepository.findById(id);
        return avie.map(this::convertToDto);
    }

    @Override
    public AvieDto save(AvieDto avieDto) {
        Optional<Product> product = productRepository.findById(avieDto.getProduit().getId());
        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(avieDto.getUtilisateur().getId());

        if (product.isPresent() && utilisateur.isPresent()) {
            Avie avie = new Avie();
            avie.setCommentaire(avieDto.getCommentaire());
            avie.setNote(avieDto.getNote());
            avie.setDateCreation(OffsetDateTime.now());
            avie.setProduct(product.get());
            avie.setUtilisateur(utilisateur.get());

            Avie savedAvie = avieRepository.save(avie);
            return convertToDto(savedAvie);
        } else {
            throw new IllegalArgumentException("Product or Utilisateur not found");
        }
    }

    @Override
    public void delete(Long id) {
        avieRepository.deleteById(id);
    }

    @Override
    public AvieDto update(Long id, AvieDto avieDto) {
        Optional<Avie> existingAvie = avieRepository.findById(id);
        if (existingAvie.isPresent()) {
            Avie avie = existingAvie.get();
            avie.setCommentaire(avieDto.getCommentaire());
            avie.setNote(avieDto.getNote());
            avie.setDateCreation(avieDto.getDateCreation());

            // Update the associated Product and Utilisateur if needed
            if (avieDto.getProduit() != null) {
                Product product = productRepository.findById(avieDto.getProduit().getId()).orElseThrow(() -> new IllegalArgumentException("Product not found"));
                avie.setProduct(product);
            }
            if (avieDto.getUtilisateur() != null) {
                Utilisateur utilisateur = utilisateurRepository.findById(avieDto.getUtilisateur().getId()).orElseThrow(() -> new IllegalArgumentException("Utilisateur not found"));
                avie.setUtilisateur(utilisateur);
            }

            Avie updatedAvie = avieRepository.save(avie);
            return convertToDto(updatedAvie);
        } else {
            throw new IllegalArgumentException("Avie not found");
        }
    }
    private AvieDto convertToDto(Avie avie) {
        AvieDto avieDto = new AvieDto();
        avieDto.setId(avie.getId());
        avieDto.setCommentaire(avie.getCommentaire());
        avieDto.setNote(avie.getNote());
        avieDto.setDateCreation(avie.getDateCreation());
        avieDto.setProduit(new ProductDto(avie.getProduct().getId(), avie.getProduct().getNom())); // Assuming ProductDto has a constructor
        avieDto.setUtilisateur(new UtilisateurDto(avie.getUtilisateur().getId(), avie.getUtilisateur().getNom())); // Assuming UtilisateurDto has a constructor
        return avieDto;
    }
}
