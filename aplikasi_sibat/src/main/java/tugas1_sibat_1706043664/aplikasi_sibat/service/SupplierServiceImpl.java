package tugas1_sibat_1706043664.aplikasi_sibat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tugas1_sibat_1706043664.aplikasi_sibat.model.SupplierModel;
import tugas1_sibat_1706043664.aplikasi_sibat.repository.SupplierDB;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SupplierServiceImpl implements SupplierService{
    @Autowired
    SupplierDB supplierDB;

    @Override
    public List<SupplierModel> getListSupplier() {
        return supplierDB.findAll();
    }

    @Override
    public Optional<SupplierModel> getListSupplierById(Long idSupplier) {
        return supplierDB.findById(idSupplier);
    }
}
