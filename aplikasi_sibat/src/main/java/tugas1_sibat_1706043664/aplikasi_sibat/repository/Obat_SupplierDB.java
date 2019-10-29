package tugas1_sibat_1706043664.aplikasi_sibat.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tugas1_sibat_1706043664.aplikasi_sibat.model.ObatModel;
import tugas1_sibat_1706043664.aplikasi_sibat.model.Obat_SupplierModel;

import java.util.List;

@Repository
public interface Obat_SupplierDB extends JpaRepository<Obat_SupplierModel, Long> {
}
