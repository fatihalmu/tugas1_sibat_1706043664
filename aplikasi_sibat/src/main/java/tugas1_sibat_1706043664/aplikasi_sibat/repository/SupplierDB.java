package tugas1_sibat_1706043664.aplikasi_sibat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tugas1_sibat_1706043664.aplikasi_sibat.model.JenisModel;
import tugas1_sibat_1706043664.aplikasi_sibat.model.SupplierModel;

import java.util.Optional;

@Repository
public interface SupplierDB extends JpaRepository<SupplierModel, Long> {
    @Override
    Optional<SupplierModel> findById(Long id);
}
