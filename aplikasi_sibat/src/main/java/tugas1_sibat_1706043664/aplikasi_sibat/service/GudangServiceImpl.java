package tugas1_sibat_1706043664.aplikasi_sibat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tugas1_sibat_1706043664.aplikasi_sibat.model.GudangModel;
import tugas1_sibat_1706043664.aplikasi_sibat.model.ObatModel;
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
    public GudangModel ubahGudang(GudangModel objekYangDiubah) {
        Long id= objekYangDiubah.getId();
        GudangModel objekAsli = gudangDB.findById(id).get();
        //kalau gaaada yang diubah
        if((objekAsli.getNama().equals(objekYangDiubah.getNama()))
                && (objekAsli.getAlamat().equals(objekYangDiubah.getAlamat()))){
            UnsupportedOperationException unsupportedOperationException = new UnsupportedOperationException();
            throw unsupportedOperationException;
        }
        //kalau nama ga diubah
        if(objekAsli.getNama().equals(objekYangDiubah.getNama())) {
            //do nothing
            ;
        }else {
            objekAsli.setNama(objekYangDiubah.getNama());
        }
        //kalau harga ga diubah
        if(objekAsli.getAlamat().equals(objekYangDiubah.getAlamat())) {
            //do nothing
            ;
        }else {
            objekAsli.setAlamat(objekYangDiubah.getAlamat());
        }

        gudangDB.save(objekAsli);
        return objekAsli;
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
