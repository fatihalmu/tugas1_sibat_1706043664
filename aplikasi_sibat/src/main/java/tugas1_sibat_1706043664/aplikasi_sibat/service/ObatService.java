package tugas1_sibat_1706043664.aplikasi_sibat.service;

import tugas1_sibat_1706043664.aplikasi_sibat.model.ObatModel;

import java.util.Date;
import java.util.List;

public interface ObatService {
    void tambahObat(ObatModel obat);
    ObatModel ubahObat (ObatModel obat);
    List<ObatModel> getListObat();
    ObatModel getObatByNomor();
    void hapusObat(Long id);
    ObatModel findbynoreg(String nomorRegistrasi);

    ObatModel findById(Long id);
}
