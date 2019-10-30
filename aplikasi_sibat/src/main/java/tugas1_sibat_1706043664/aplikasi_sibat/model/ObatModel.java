package tugas1_sibat_1706043664.aplikasi_sibat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.format.annotation.DateTimeFormat;
import tugas1_sibat_1706043664.aplikasi_sibat.service.SupplierService;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigInteger;
import java.util.*;

@Entity
@Table(name = "obat")
public class ObatModel {
    private Date dibuat= new Date();
    public Date getDibuat() {
        return dibuat;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "nama",nullable = false)
    private String nama;

    @NotNull
    @Size(max = 255)
    @Column(name = "kode",nullable = false,unique = true)
    private String kode;

    @NotNull
    @Column(name = "harga",nullable = false,unique = true)
    private Double harga;

    @NotNull
    @Size(max = 255)
    @Column(name = "nomorRegistrasi",nullable = false)
    private String nomorRegistrasi;

    @NotNull
    @Size(max = 255)
    @Column(name = "bentuk",nullable = false)
    private String bentuk;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "tanggalTerbit",nullable = false)
    private java.util.Date tanggalTerbit;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idJenis",referencedColumnName = "id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private JenisModel jenis;

    @OneToMany(mappedBy = "obat",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Obat_SupplierModel> obatSupplierModels = new ArrayList<>(1);

    @OneToMany(mappedBy = "obat",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
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

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public Double getHarga() {
        return harga;
    }

    public void setHarga(Double harga) {
        this.harga = harga;
    }

    public String getNomorRegistrasi() {
        return nomorRegistrasi;
    }

    public void setNomorRegistrasi(String nomorRegistrasi) {
        this.nomorRegistrasi = nomorRegistrasi;
    }

    public String getBentuk() {
        return bentuk;
    }

    public void setBentuk(String bentuk) {
        this.bentuk = bentuk;
    }

    public Date getTanggalTerbit() {
        return tanggalTerbit;
    }

    public void setTanggalTerbit(Date tanggalTerbit) {
        this.tanggalTerbit = tanggalTerbit;
    }

    public JenisModel getJenis() {
        return jenis;
    }

    public void setJenis(JenisModel jenis) {
        this.jenis = jenis;
    }

    public List<Obat_SupplierModel> getObatSupplierModels() {
        return obatSupplierModels;
    }

    public void setObatSupplierModels(List<Obat_SupplierModel> obatSupplierModels) {
        this.obatSupplierModels = obatSupplierModels;
    }

    public List<Gudang_ObatModel> getGudangObatModels() {
        return gudangObatModels;
    }

    public void setGudangObatModels(List<Gudang_ObatModel> gudangObatModels) {
        this.gudangObatModels = gudangObatModels;
    }
}
