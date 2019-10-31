package tugas1_sibat_1706043664.aplikasi_sibat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tugas1_sibat_1706043664.aplikasi_sibat.model.ObatModel;
import tugas1_sibat_1706043664.aplikasi_sibat.repository.ObatDB;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class ObatServiceImpl implements ObatService {
    @Autowired
    ObatDB obatDB;

    @Override
    public void tambahObat(ObatModel obat) {
        obatDB.save(obat);
    }

    @Override
    public ObatModel ubahObat(ObatModel objekYangDiubah) {
        Long id_obat = objekYangDiubah.getId();
        ObatModel objekAsli = obatDB.findById(id_obat).get();

        //kalau gaaada yang diubah
        if((objekAsli.getNama().equals(objekYangDiubah.getNama()))
                && (objekAsli.getHarga().equals(objekYangDiubah.getHarga()))
                && ((objekAsli.getTanggalTerbit().compareTo(objekYangDiubah.getTanggalTerbit()) == 0) && objekAsli.getBentuk().equals(objekYangDiubah.getBentuk()))){
            UnsupportedOperationException unsupportedOperationException = new UnsupportedOperationException();
            throw unsupportedOperationException;
        }
        //kalau nama ga diubah
        if(objekAsli.getNama().equals(objekYangDiubah.getNama())) {
            //do nothing
            ;
        }else {
            objekAsli.setNama(objekYangDiubah.getNama());
        }
        //kalau harga ga diubah
        if(objekAsli.getHarga().equals(objekYangDiubah.getHarga())) {
            //do nothing
            ;
        }else {
            objekAsli.setHarga(objekYangDiubah.getHarga());
        }
        //kalau tanggal dan bentuk tidak diubah
        if((objekAsli.getTanggalTerbit().compareTo(objekYangDiubah.getTanggalTerbit()) == 0) && objekAsli.getBentuk().equals(objekYangDiubah.getBentuk())) {
            //donothing
            ;
        } else {
            objekAsli.setTanggalTerbit(objekYangDiubah.getTanggalTerbit());
            objekAsli.setBentuk(objekYangDiubah.getBentuk());
            // karena jenis objek yang diubah null maka kita
            //tambahkan jenis objek asli ke objekyangdiubah
            // kenapa bisa? karena logikanya jenis tidak ada diubah sama sekali jadi tidakmungkin berubah
            objekYangDiubah.setJenis(objekAsli.getJenis());
            objekAsli.setKode(buat_kode(objekYangDiubah));
        }
        obatDB.save(objekAsli);
        return objekAsli;
    }

    @Override
    public List<ObatModel> getListObat() {
        return obatDB.findAll();
    }

    @Override
    public ObatModel getObatByNomor() {
        return null;
    }

    @Override
    public void hapusObat(ObatModel obat) {
        if(obat.getObatSupplierModels().size() == 0 && obat.getGudangObatModels().size() == 0){
            obatDB.deleteById(obat.getId());
        }else{
            UnsupportedOperationException unsupportedOperationException = new UnsupportedOperationException();
            throw unsupportedOperationException;
        }

    }
    @Override
    public ObatModel findbynoreg(String nomorRegistrasi) {
        return obatDB.findByNomorRegistrasi(nomorRegistrasi);
    }

    @Override
    public ObatModel findById(Long id) {
        return obatDB.findById(id).get();
    }

    /**-----------------------------------------------------------*/
    //untuk random kode
    static String getAlphaNumericString()

    {

        // lower limit for LowerCase Letters
        int lowerLimit = 97;

        // lower limit for LowerCase Letters
        int upperLimit = 122;

        Random random = new Random();

        // Create a StringBuffer to store the result
        StringBuffer r = new StringBuffer(2);

        for (int i = 0; i < 2; i++) {

            // take a random value between 97 and 122
            int nextRandomChar = lowerLimit
                    + (int)(random.nextFloat()
                    * (upperLimit - lowerLimit + 1));

            // append a character at the end of bs
            r.append((char)nextRandomChar);
        }

        // return the resultant string
        return r.toString();
    }
    //method untuk buat kode
    @Override
    public String buat_kode(ObatModel objekObat){
        //pembuatan kode
        String idjenis = String.valueOf(objekObat.getJenis().getId());
        String bentuk = new String();
        System.out.println("bentuk obat dari html :"+objekObat.getBentuk());
        if(objekObat.getBentuk().equals("Cairan")) {
            bentuk += "01";
        }else if(objekObat.getBentuk().equals("Kapsul")){
            bentuk += "02";
        }else if(objekObat.getBentuk().equals("Tablet")){
            bentuk+="03";
            System.out.println("masuk");
        }
        System.out.println("bentuk obat : " + bentuk);
        String tahun = String.valueOf(objekObat.getDibuat().getYear()+1900);
        String tahuntambah5 = String.valueOf(objekObat.getTanggalTerbit().getYear()+1905);
        String random = getAlphaNumericString().toUpperCase();
        String Kode = idjenis+ bentuk +tahun+tahuntambah5+random;
        return Kode;
    }
}
