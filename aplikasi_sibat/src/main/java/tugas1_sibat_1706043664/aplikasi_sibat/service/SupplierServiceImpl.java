package tugas1_sibat_1706043664.aplikasi_sibat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tugas1_sibat_1706043664.aplikasi_sibat.model.JenisModel;
import tugas1_sibat_1706043664.aplikasi_sibat.model.SupplierModel;
import tugas1_sibat_1706043664.aplikasi_sibat.repository.JenisDB;
import tugas1_sibat_1706043664.aplikasi_sibat.repository.SupplierDB;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class SupplierServiceImpl implements SupplierService{
    @Autowired
    SupplierDB supplierDB;

    @Override
    public List<SupplierModel> getListSupplier() {
        return supplierDB.findAll();
    }
}
