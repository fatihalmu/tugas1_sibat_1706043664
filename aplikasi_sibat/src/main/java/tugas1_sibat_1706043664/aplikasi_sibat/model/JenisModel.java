package tugas1_sibat_1706043664.aplikasi_sibat.model;

import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.awt.*;
import java.math.BigInteger;
import java.util.List;

@Entity
@Table(name = "jenis")
public class JenisModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Size(max = 20)
    @Column(name = "id",nullable = false)
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "nama",nullable = false)
    private String nama;

    @NotNull
    @UniqueElements
    @Size(max = 255)
    @Column(name = "deskripsi",nullable = false)
    private String deskripsi;

    @OneToMany(mappedBy = "jenis",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<ObatModel> listObat;

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

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public List<ObatModel> getListObat() {
        return listObat;
    }

    public void setListObat(List<ObatModel> listObat) {
        this.listObat = listObat;
    }

}
