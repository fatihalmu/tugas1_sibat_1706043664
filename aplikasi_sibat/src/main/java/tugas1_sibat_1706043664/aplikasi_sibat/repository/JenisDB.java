package tugas1_sibat_1706043664.aplikasi_sibat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tugas1_sibat_1706043664.aplikasi_sibat.model.JenisModel;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface JenisDB extends JpaRepository<JenisModel, Long> {
    @Override
    Optional<JenisModel> findById(Long id);
}
