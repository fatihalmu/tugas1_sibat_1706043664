package tugas1_sibat_1706043664.aplikasi_sibat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tugas1_sibat_1706043664.aplikasi_sibat.model.ObatModel;
import tugas1_sibat_1706043664.aplikasi_sibat.repository.ObatDB;

import javax.transaction.Transactional;
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ObatServiceImpl implements ObatService {
    @Autowired
    ObatDB obatDB;

    @Override
    public void tambahObat(ObatModel obat) {
        obatDB.save(obat);
    }

    @Override
    public ObatModel ubahObat(ObatModel obat) {
        return null;
    }

    @Override
    public List<ObatModel> getListObat() {
        return obatDB.findAll();
    }

    @Override
    public ObatModel getObatByNomor() {
        return null;
    }

    @Override
    public void hapusObat(Long id) {

    }
    @Override
    public ObatModel findbynoreg(String nomorRegistrasi) {
        return obatDB.findByNomorRegistrasi(nomorRegistrasi);
    }
}
