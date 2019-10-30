package tugas1_sibat_1706043664.aplikasi_sibat.model;

import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "supplier")
public class SupplierModel {
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
    @Size(max = 255)
    @Column(name = "alamat",nullable = false)
    private String alamat;

    @NotNull
    @Column(name = "nomorTelepon",nullable = false)
    private Long nomorTelepon;

    @OneToMany(mappedBy = "supplier",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Obat_SupplierModel> obatSupplierModels;

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

    public Long getNomorTelepon() {
        return nomorTelepon;
    }

    public void setNomorTelepon(Long nomorTelepon) {
        this.nomorTelepon = nomorTelepon;
    }

    public List<Obat_SupplierModel> getObatSupplierModels() {
        return obatSupplierModels;
    }

    public void setObatSupplierModels(List<Obat_SupplierModel> obatSupplierModels) {
        this.obatSupplierModels = obatSupplierModels;
    }
}