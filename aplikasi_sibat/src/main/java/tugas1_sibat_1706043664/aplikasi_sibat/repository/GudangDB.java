package tugas1_sibat_1706043664.aplikasi_sibat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tugas1_sibat_1706043664.aplikasi_sibat.model.GudangModel;

import java.util.Optional;

@Repository
public interface GudangDB extends JpaRepository<GudangModel, Long> {
    Optional<GudangModel> findById(Long id);
    void deleteById(Long id);
}
