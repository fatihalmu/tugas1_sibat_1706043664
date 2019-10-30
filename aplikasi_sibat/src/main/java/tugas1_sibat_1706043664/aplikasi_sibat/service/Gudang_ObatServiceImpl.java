package tugas1_sibat_1706043664.aplikasi_sibat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tugas1_sibat_1706043664.aplikasi_sibat.model.Gudang_ObatModel;
import tugas1_sibat_1706043664.aplikasi_sibat.model.Obat_SupplierModel;
import tugas1_sibat_1706043664.aplikasi_sibat.repository.Gudang_ObatDB;
import tugas1_sibat_1706043664.aplikasi_sibat.repository.Obat_SupplierDB;

import javax.transaction.Transactional;

@Service
@Transactional
public class Gudang_ObatServiceImpl implements Gudang_ObatService {
    @Autowired
    Gudang_ObatDB gudang_obatDB;


    @Override
    public void tambahgudangobat(Gudang_ObatModel gudang_obatModel) {
        gudang_obatDB.save(gudang_obatModel);

    }
}
