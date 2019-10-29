package tugas1_sibat_1706043664.aplikasi_sibat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tugas1_sibat_1706043664.aplikasi_sibat.model.Gudang_ObatModel;
import tugas1_sibat_1706043664.aplikasi_sibat.model.Obat_SupplierModel;

@Repository
public interface Gudang_ObatDB extends JpaRepository<Gudang_ObatModel, Long> {
}
