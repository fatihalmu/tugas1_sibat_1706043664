package tugas1_sibat_1706043664.aplikasi_sibat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tugas1_sibat_1706043664.aplikasi_sibat.model.ObatModel;
import tugas1_sibat_1706043664.aplikasi_sibat.model.Obat_SupplierModel;
import tugas1_sibat_1706043664.aplikasi_sibat.model.SupplierModel;
import tugas1_sibat_1706043664.aplikasi_sibat.repository.ObatDB;
import tugas1_sibat_1706043664.aplikasi_sibat.repository.Obat_SupplierDB;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class Obat_SupplierServiceImpl implements Obat_SupplierService {
    @Autowired
    Obat_SupplierDB obatSupplierDB;

    @Override
    public void tambahobatsupplier(Obat_SupplierModel obatSupplierModel) {
        obatSupplierDB.save(obatSupplierModel);

    }
}
