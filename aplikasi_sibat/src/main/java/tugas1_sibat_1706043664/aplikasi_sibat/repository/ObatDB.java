package tugas1_sibat_1706043664.aplikasi_sibat.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tugas1_sibat_1706043664.aplikasi_sibat.model.ObatModel;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Repository
public interface ObatDB extends JpaRepository<ObatModel, Long> {
    @Override
    List<ObatModel> findAll(Sort sort);
    List<ObatModel> findByJenisId(Long Id);
    ObatModel findByNomorRegistrasi(String nomorRegistrasi);
}
