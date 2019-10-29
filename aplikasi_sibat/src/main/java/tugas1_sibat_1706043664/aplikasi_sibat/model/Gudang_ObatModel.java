package tugas1_sibat_1706043664.aplikasi_sibat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "gudang_obat")
public class Gudang_ObatModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Size(max = 20)
    @Column(name = "id",nullable = false)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "idObat",referencedColumnName = "id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    ObatModel obat;

    @ManyToOne
    @JoinColumn(name = "idGudang",referencedColumnName = "id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    GudangModel gudang;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ObatModel getObat() {
        return obat;
    }

    public void setObat(ObatModel obat) {
        this.obat = obat;
    }

    public GudangModel getGudang() {
        return gudang;
    }

    public void setGudang(GudangModel gudang) {
        this.gudang = gudang;
    }
}
