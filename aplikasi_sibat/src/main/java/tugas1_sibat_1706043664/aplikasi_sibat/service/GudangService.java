package tugas1_sibat_1706043664.aplikasi_sibat.service;


import tugas1_sibat_1706043664.aplikasi_sibat.model.GudangModel;

import java.util.List;

public interface GudangService {
    void tambahGudang(GudangModel gudangModel);
    GudangModel ubahGudang(GudangModel gudangModel);
    List<GudangModel> getListGudang();
    void hapusGudang(Long id);
}
