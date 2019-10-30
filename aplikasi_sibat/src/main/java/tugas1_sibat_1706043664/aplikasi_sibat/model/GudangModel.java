package tugas1_sibat_1706043664.aplikasi_sibat.model;

import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "gudang")
public class GudangModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "nama",nullable = false)
    private String nama;

    @NotNull
    @Size(max = 255)
    @Column(name = "alamat",nullable = false)
    private String alamat;
    @OneToMany(mappedBy = "gudang",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Gudang_ObatModel> gudangObatModels;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public List<Gudang_ObatModel> getGudangObatModels() {
        return gudangObatModels;
    }

    public void setGudangObatModels(List<Gudang_ObatModel> gudangObatModels) {
        this.gudangObatModels = gudangObatModels;
    }
}
