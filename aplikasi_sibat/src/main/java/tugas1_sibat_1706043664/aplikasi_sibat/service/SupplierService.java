package tugas1_sibat_1706043664.aplikasi_sibat.service;

import tugas1_sibat_1706043664.aplikasi_sibat.model.SupplierModel;

import java.util.List;
import java.util.Optional;


public interface SupplierService {
    List<SupplierModel> getListSupplier();

    Optional<SupplierModel> getListSupplierById(Long idSupplier);
}
