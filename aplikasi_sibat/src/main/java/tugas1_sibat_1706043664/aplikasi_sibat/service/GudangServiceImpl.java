package tugas1_sibat_1706043664.aplikasi_sibat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tugas1_sibat_1706043664.aplikasi_sibat.model.GudangModel;
import tugas1_sibat_1706043664.aplikasi_sibat.model.ObatModel;
import tugas1_sibat_1706043664.aplikasi_sibat.repository.GudangDB;
import tugas1_sibat_1706043664.aplikasi_sibat.repository.ObatDB;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class GudangServiceImpl implements GudangService{
    @Autowired
    GudangDB gudangDB;

    @Override
    public void tambahGudang(GudangModel gudangModel) {
        gudangDB.save(gudangModel);
    }

    @Override
    public GudangModel ubahGudang(GudangModel gudangModel) {
        return null;
    }

    @Override
    public List<GudangModel> getListGudang() {
        return gudangDB.findAll();
    }

    @Override
    public void hapusGudang(Long id) {

    }
}
