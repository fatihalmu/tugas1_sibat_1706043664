package tugas1_sibat_1706043664.aplikasi_sibat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tugas1_sibat_1706043664.aplikasi_sibat.controller.ObatController;
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
    @Autowired
    ObatController obatController;

    @Override
    public void tambahObat(ObatModel obat) {
        obatDB.save(obat);
    }

    @Override
    public ObatModel ubahObat(ObatModel objekYangDiubah) {
        Long id_obat = objekYangDiubah.getId();
        ObatModel objekAsli = obatDB.findById(id_obat).get();
        if(objekAsli.getNama() != objekYangDiubah.getNama()){
            objekAsli.setNama(objekYangDiubah.getNama());
        }
        if(objekAsli.getHarga() != objekYangDiubah.getHarga()){
            objekAsli.setHarga(objekYangDiubah.getHarga());
        }
        if((objekAsli.getTanggalTerbit() != objekYangDiubah.getTanggalTerbit()) || (objekAsli.getBentuk() != objekYangDiubah.getBentuk())){
            objekAsli.setTanggalTerbit(objekYangDiubah.getTanggalTerbit());
            objekAsli.setBentuk(objekYangDiubah.getBentuk());
            // karena jenis objek yang diubah null maka kita
            //tambahkan jenis objek asli ke objekyangdiubah
            // kenapa bisa? karena logikanya jenis tidak ada diubah sama sekali jadi tidakmungkin berubah
            objekYangDiubah.setJenis(objekAsli.getJenis());
            objekAsli.setKode(obatController.buat_kode(objekYangDiubah));
        }
        obatDB.save(objekAsli);
        return objekAsli;
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

    @Override
    public ObatModel findById(Long id) {
        return obatDB.findById(id).get();
    }
}
