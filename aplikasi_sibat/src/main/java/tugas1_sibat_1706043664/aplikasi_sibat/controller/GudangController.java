package tugas1_sibat_1706043664.aplikasi_sibat.controller;

import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tugas1_sibat_1706043664.aplikasi_sibat.model.GudangModel;
import tugas1_sibat_1706043664.aplikasi_sibat.model.Gudang_ObatModel;
import tugas1_sibat_1706043664.aplikasi_sibat.model.ObatModel;
import tugas1_sibat_1706043664.aplikasi_sibat.model.SupplierModel;
import tugas1_sibat_1706043664.aplikasi_sibat.service.GudangService;
import tugas1_sibat_1706043664.aplikasi_sibat.service.Gudang_ObatService;
import tugas1_sibat_1706043664.aplikasi_sibat.service.ObatService;
import tugas1_sibat_1706043664.aplikasi_sibat.service.SupplierService;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class GudangController {
    @Qualifier("gudangServiceImpl")
    @Autowired
    private GudangService gudangService;

    @Autowired
    private  ObatService obatService;

    @Autowired
    private Gudang_ObatService gudang_obatService;
    //fitur 5 : menampilkan daftar gudang DONE
    @RequestMapping(value = "/gudang",method = RequestMethod.GET)
    public String view_Daftar_gudang(Model model){
        String navbartitle= "SIBAT";
        List<GudangModel> listAllGudang =  gudangService.getListGudang();
        model.addAttribute("judul",navbartitle);
        model.addAttribute("listAllGudang",listAllGudang);
        return "view-daftar-gudang";
    }
    /**-----------------------------------------------------------*/

    //fitur 6 : menampilkan detail gudang DONE
    @RequestMapping(value = "/gudang/view",method = RequestMethod.GET)
    public String view_detail_gudang(@RequestParam(value = "idGudang") String idGudang,Model model){
            String navbartitle = "SIBAT";
            GudangModel objekGudang = gudangService.getListGudangById(Long.valueOf(idGudang)).get();
            //list obat obat yang berhubungan sama gudang ini
            List<Gudang_ObatModel> gudang_obatModel = objekGudang.getGudangObatModels();

            Gudang_ObatModel dummyObatDitambah = new Gudang_ObatModel();
            dummyObatDitambah.setGudang(objekGudang);

            ArrayList<ObatModel> obatDiGudang = new ArrayList<ObatModel>();
            List<ObatModel> listAllObat = obatService.getListObat();
            ArrayList<ObatModel> obatUntukDipilih = new ArrayList<ObatModel>();


            //masukkan obat yang dimiliki kedalam obatDiGudang
            for (Gudang_ObatModel eachobject : gudang_obatModel) {
                obatDiGudang.add(eachobject.getObat());
            }

            for (int i = 0; i < listAllObat.size(); i++) {
                ObatModel objekObat = listAllObat.get(i);
                if (obatDiGudang.contains(objekObat)) {
                    continue;
                } else {
                    obatUntukDipilih.add(objekObat);
                }
            }

            model.addAttribute("judul", navbartitle);
            model.addAttribute("objekGudang", objekGudang);
            model.addAttribute("obatDiGudang", obatDiGudang);
            model.addAttribute("obatTidakDiGudang", obatUntukDipilih);
            model.addAttribute("dummyObatDitambah", dummyObatDitambah);
            System.out.println("ini gudang :" + dummyObatDitambah.getGudang());
            return "view-detail-gudang";
    }
    /**-----------------------------------------------------------*/

    //fitur 7 :tambah gudang form DONE
    @RequestMapping(value = "/gudang/tambah",method = RequestMethod.GET)
    public String tambah_gudang_form(Model model){
        GudangModel objekdummy = new GudangModel();
        String navbartitle= "SIBAT";
        model.addAttribute("judul",navbartitle);
        model.addAttribute("objekdummy",objekdummy);
        return "form-tambah-gudang";
    }

    //fitur 7 :tambah gudang submit DONE
    @RequestMapping(value = "/gudang/tambah",method = RequestMethod.POST)
    public String tambah_gudang_submit(@ModelAttribute GudangModel objekdummy, Model model){
        String navbartitle= "SIBAT";
        String buttonErr = "Lihat Daftar Gudang";
        String buttonErr2 = "Tambah Gudang";
        String linkbutton = "/gudang";
        String linkbutton2 = "/gudang/tambah";
        if(objekdummy.getNama().equals("") || objekdummy.getAlamat().equals("")){
            System.out.println("masuk eror");
            String errmess = "Data Yang Anda Masukkan Salah Atau Tidak Lengkap";
            model.addAttribute("judul", navbartitle);
            model.addAttribute("errmess",errmess);
            model.addAttribute("buttonErr",buttonErr);
            model.addAttribute("linkbutton",linkbutton);
            model.addAttribute("buttonErr2",buttonErr2);
            model.addAttribute("linkbutton2",linkbutton2);
            return "error";
        }
        gudangService.tambahGudang(objekdummy);
        String notify = "Gudang '"+objekdummy.getNama()+"' berhasil ditambahkan";
        model.addAttribute("notify",notify);
        model.addAttribute("judul",navbartitle);
        model.addAttribute("buttonErr",buttonErr);
        model.addAttribute("linkbutton",linkbutton);
        model.addAttribute("buttonErr2",buttonErr2);
        model.addAttribute("linkbutton2",linkbutton2);
        return "gudang-success-notify";
    }
    /**-----------------------------------------------------------*/

    //fitur 8 : hapus gudang by id DONE
    @RequestMapping (value = "/gudang/hapus/{id}",method = RequestMethod.GET)
    public String hapus_gudang(@PathVariable("id") Long id,Model model){
        GudangModel objekgudang = gudangService.getListGudangById(id).get();
        String navbartitle= "SIBAT";
        String buttonErr = "Lihat Daftar Gudang";
        String buttonErr2 = "Tambah Gudang";
        String linkbutton = "/gudang";
        String linkbutton2 = "/gudang/tambah";
        try{
            gudangService.deleteGudangById(id);
            model.addAttribute("objek",objekgudang);
            String notify = "Gudang '"+objekgudang.getNama()+"' berhasil dihapus";
            model.addAttribute("notify",notify);
            model.addAttribute("judul",navbartitle);
            model.addAttribute("buttonErr",buttonErr);
            model.addAttribute("linkbutton",linkbutton);
            model.addAttribute("buttonErr2",buttonErr2);
            model.addAttribute("linkbutton2",linkbutton2);
            return "gudang-success-notify";
        }catch (UnsupportedOperationException e){
            String errmess = "Gudang Tidak Bisa Dihapus Karena Memiliki Obat";
            model.addAttribute("judul", navbartitle);
            model.addAttribute("errmess",errmess);
            model.addAttribute("buttonErr",buttonErr);
            model.addAttribute("linkbutton",linkbutton);
            model.addAttribute("buttonErr2",buttonErr2);
            model.addAttribute("linkbutton2",linkbutton2);
            return "error";
        }

    }
    /**-----------------------------------------------------------*/
    //fitur 9 DONE
    @RequestMapping(value = "/gudang/tambah-obat",method = RequestMethod.POST)
    public String  tambah_obat_di_gudang(@ModelAttribute("dummyObatDitambah") @Valid Gudang_ObatModel dummyObatDitambah,
                                      BindingResult result, Model model){
        String navbartitle = "SIBAT";
        try {
            System.out.println("masuk kedua");
            System.out.println("obatnya :" + dummyObatDitambah.getObat() + " gudangnya: " + dummyObatDitambah.getGudang());
            //dummyObatDitambah.setGudang();
            gudang_obatService.tambahgudangobat(dummyObatDitambah);
            //untuk ambil id
            GudangModel objekGudang = dummyObatDitambah.getGudang();
            view_detail_gudang(String.valueOf(objekGudang.getId()), model);
            model.addAttribute("judul", navbartitle);
            model.addAttribute("dummyObatDitambah", dummyObatDitambah);
            return "add-obat-to-gudang-notify";
        }catch (Exception SQLIntegrityConstraintViolationException){
            String errmess = "Obat Sudah Habis Atau Anda Tidak Memilih Obat Sama Sekali";
            String buttonErr = "Lihat Data Gudang";
            String buttonErr2 = "Tambah Obat Lain";
            String linkbutton = "/gudang/view?idGudang="+ dummyObatDitambah.getGudang().getId();
            String linkbutton2 = "/obat/tambah";
            model.addAttribute("judul", navbartitle);
            model.addAttribute("errmess",errmess);
            model.addAttribute("buttonErr",buttonErr);
            model.addAttribute("linkbutton",linkbutton);
            model.addAttribute("buttonErr2",buttonErr2);
            model.addAttribute("linkbutton2",linkbutton2);
            return "error";

        }

    }
    /**-----------------------------------------------------------*/
    //fitur 11 menampilkan daftar obat pada suatu gudang yang telah lebih dari 5 tahun DONE

    @RequestMapping(value = "/gudang/expired-obat",method = RequestMethod.GET)
    public String obat_expired(@RequestParam(value = "idGudang",required = false) Long idGudang,Model model){
        //list all gudang untuk drop down
        List<GudangModel> listAllGudang = gudangService.getListGudang();
        //gudang objek untuk table
        GudangModel objekGudang = new GudangModel();
        List<ObatModel> obatDiGudang = new ArrayList<>();
        List<ObatModel> obatExpired = new ArrayList<>();

        Date date = new Date();
        if(idGudang != null){
            objekGudang = gudangService.getListGudangById(idGudang).get();
            List<Gudang_ObatModel> gudangObatModelList = objekGudang.getGudangObatModels();
            for (Gudang_ObatModel each : gudangObatModelList){
                obatDiGudang.add(each.getObat());
            }
            for(ObatModel each: obatDiGudang){
                if(date.getYear()-each.getTanggalTerbit().getYear() >= 5){
                    obatExpired.add(each);
                }
            }
        }
        String navbartitle = "SIBAT";
        model.addAttribute("judul",navbartitle);
        model.addAttribute("listAllGudang",listAllGudang);
        model.addAttribute("obatExpired",obatExpired);
        return "obat_expired";
    }

    /**-----------------------------------------------------------*/
    //Fitur tambahan ubah gudang DONE
    //form  UBAH gudang
    @RequestMapping(value = "/gudang/ubah/{id}",method = RequestMethod.GET)
    public String ubah_gudang_form(@PathVariable("id")Long id,Model model){
        GudangModel objekGudang = gudangService.getListGudangById(id).get();
        String navbartitle= "SIBAT";
        model.addAttribute("judul",navbartitle);
        model.addAttribute("objekGudang",objekGudang);
        return "ubah-gudang-form";
    }

    //submit UBAH gudang DONE
    @RequestMapping(value ="/gudang/ubah/{id}",method = RequestMethod.POST)
    public String ubah_Gudang_submit(@ModelAttribute GudangModel objekGudang, Model model){
        System.out.println("objek gudang "+objekGudang);
        System.out.println("nama gudang "+objekGudang.getNama() + " alamat " + objekGudang.getAlamat());
        String navbartitle= "SIBAT";
        model.addAttribute("judul",navbartitle);
        try {
            GudangModel objekBaru = gudangService.ubahGudang(objekGudang);
            model.addAttribute("objekBaru",objekBaru);
            model.addAttribute("header","SUCCESS!");
            model.addAttribute("pesan","Selamat! Gudang Berhasil Dirubah!");
            model.addAttribute("flag",true);
            return "ubah-gudang-notify";
        }catch (UnsupportedOperationException e){
            model.addAttribute("objekBaru",objekGudang);
            model.addAttribute("header","WARNING!");
            model.addAttribute("pesan","Tidak Ada Perubahan Pada Gudang Ini");
            model.addAttribute("flag",false);
            return "ubah-gudang-notify";

        }

    }


}
