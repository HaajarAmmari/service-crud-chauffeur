package ma.emsi.springsecurity.repositories;

import ma.emsi.springsecurity.entities.Chauffeur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChauffeurRepository extends JpaRepository<Chauffeur, Long> {
    Page<Chauffeur> findByNomContains(String kw, Pageable pageable);
    public static void main(String[] args) {
    }
}
