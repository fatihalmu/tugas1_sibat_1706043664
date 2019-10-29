package tugas1_sibat_1706043664.aplikasi_sibat.service;


import tugas1_sibat_1706043664.aplikasi_sibat.model.GudangModel;

import java.util.List;
import java.util.Optional;

public interface GudangService {
    void tambahGudang(GudangModel gudangModel);
    GudangModel ubahGudang(GudangModel gudangModel);
    List<GudangModel> getListGudang();
    void hapusGudang(Long id);
    Optional<GudangModel> getListGudangById(Long idGudang);
}
