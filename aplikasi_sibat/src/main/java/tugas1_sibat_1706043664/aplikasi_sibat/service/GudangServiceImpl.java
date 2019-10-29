package tugas1_sibat_1706043664.aplikasi_sibat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tugas1_sibat_1706043664.aplikasi_sibat.model.GudangModel;
import tugas1_sibat_1706043664.aplikasi_sibat.repository.GudangDB;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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
    public Optional<GudangModel> getListGudangById(Long idGudang) {
        return gudangDB.findById(idGudang);
    }

    @Override
    public void deleteGudangById(Long id) {
        GudangModel gudang = getListGudangById(id).get();
        if(gudang.getGudangObatModels().size()==0){
            gudangDB.deleteById(id);
        }else{
            UnsupportedOperationException unsupportedOperationException = new UnsupportedOperationException();
            throw unsupportedOperationException;
        }

    }
}
