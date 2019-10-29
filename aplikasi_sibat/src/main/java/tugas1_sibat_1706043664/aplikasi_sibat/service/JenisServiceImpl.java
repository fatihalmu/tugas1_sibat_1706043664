package tugas1_sibat_1706043664.aplikasi_sibat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tugas1_sibat_1706043664.aplikasi_sibat.model.JenisModel;
import tugas1_sibat_1706043664.aplikasi_sibat.repository.JenisDB;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class JenisServiceImpl implements JenisService{
    @Autowired
    JenisDB jenisDB;

    @Override
    public List<JenisModel> getListJenis() {
        return jenisDB.findAll();
    }
}
