package tugas1_sibat_1706043664.aplikasi_sibat.service;

import tugas1_sibat_1706043664.aplikasi_sibat.model.JenisModel;


import java.util.List;
import java.util.Optional;


public interface JenisService {
    List<JenisModel> getListJenis();

    Optional<JenisModel> getListJenisById(Long idJenis);
}
