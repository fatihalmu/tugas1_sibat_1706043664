package tugas1_sibat_1706043664.aplikasi_sibat.controller;

import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tugas1_sibat_1706043664.aplikasi_sibat.model.*;
import tugas1_sibat_1706043664.aplikasi_sibat.service.*;

import java.awt.*;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@Controller
public class ObatController {
    @Qualifier("obatServiceImpl")
    @Autowired
    private ObatService obatService;

    @Autowired
    private JenisService jenisService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private Obat_SupplierService obatSupplierService;

    @Autowired
    private GudangService gudangService;


    //fitur 1 DONE
    //nampilin semua obat yang tersedia pada list
    //view all
    @RequestMapping("/")
    public String beranda(Model model){
        String navbartitle= "SIBAT";
        List<ObatModel> listAllObat =  obatService.getListObat();
        model.addAttribute("judul",navbartitle);
        model.addAttribute("listAllObat",listAllObat);
        return "home";
    }

    /**-----------------------------------------------------------*/

    //fitur 2 DONE
    //tambah obat form
    @RequestMapping(value = "/obat/tambah", method = RequestMethod.GET)
    public String tambah_obat_form(Model model){

        ObatModel objekdummy = new ObatModel();
        List<JenisModel> listAllJenis = jenisService.getListJenis();
        List<SupplierModel> listAllSupplier = supplierService.getListSupplier();
        Obat_SupplierModel obatSupplierModel = new Obat_SupplierModel();
        objekdummy.setObatSupplierModels(new ArrayList<Obat_SupplierModel>());
        objekdummy.getObatSupplierModels().add(obatSupplierModel);

        String navbartitle= "SIBAT";
        model.addAttribute("judul",navbartitle);
        model.addAttribute("objekdummy",objekdummy);
        model.addAttribute("listAllJenis",listAllJenis);
        model.addAttribute("listAllSupplier",listAllSupplier);
        return "form-tambah-obat";

    }

    // fitur 2 DONE
    // addrow  supplier  tambah obat
    @RequestMapping (value = "/obat/tambah",method = RequestMethod.POST,params ={"tambahSupplier"})
    public String tambah_obat_tambah_supplier(@ModelAttribute ObatModel objekdummy,Model model){
        Obat_SupplierModel obatSupplierModel = new Obat_SupplierModel();
        objekdummy.getObatSupplierModels().add(obatSupplierModel);
        List<JenisModel> listAllJenis = jenisService.getListJenis();
        List<SupplierModel> listAllSupplier = supplierService.getListSupplier();

        String navbartitle= "SIBAT";
        model.addAttribute("judul",navbartitle);
        model.addAttribute("objekdummy",objekdummy);
        model.addAttribute("listAllJenis",listAllJenis);
        model.addAttribute("listAllSupplier",listAllSupplier);
        return "form-tambah-obat";
    }

    //fitur2 DONE
    // submit tambahobat
    @RequestMapping(value = "/obat/tambah", method = RequestMethod.POST)
    public String tambah_obat_submit( @ModelAttribute ObatModel objekdummy, Model model){

        String navbartitle = "SIBAT";
        String buttonErr = "Lihat Data Obat";
        String buttonErr2 = "Tambah Obat";
        String linkbutton = "/obat/view?noReg="+objekdummy.getNomorRegistrasi();
        String linkbutton2 = "/obat/tambah";
        model.addAttribute("judul", navbartitle);
        model.addAttribute("objekdummy", objekdummy);
        model.addAttribute("buttonErr",buttonErr);
        model.addAttribute("linkbutton",linkbutton);
        model.addAttribute("buttonErr2",buttonErr2);
        model.addAttribute("linkbutton2",linkbutton2);
        try {
            System.out.println(objekdummy.getObatSupplierModels().get(0));
            objekdummy.setKode(obatService.buat_kode(objekdummy));
            //kalau dia ga masukkin supplier langsung tambah
            if(objekdummy.getObatSupplierModels().get(0).getSupplier() == null){
                //hapus objek yang tadi biar bisa ditambah
                objekdummy.getObatSupplierModels().remove(0);
                obatService.tambahObat(objekdummy);
                return "form-tambah-obat-notify";
            }

            // kalau dia masukin supplier
                for (int i = 0; i < objekdummy.getObatSupplierModels().size(); i++) {
                    objekdummy.getObatSupplierModels().get(i).setObat(objekdummy);
                }

            obatService.tambahObat(objekdummy);
            System.out.println("masuk sini");

                for (Obat_SupplierModel objek : objekdummy.getObatSupplierModels()) {
                    obatSupplierService.tambahobatsupplier(objek);
                }


            return "form-tambah-obat-notify";
        }catch (Exception SQLIntegrityConstraintViolationException ){
            System.out.println("masuk eror");
            buttonErr="Lihat Daftar Obat";
            linkbutton ="/";
            String errmess = "Data Yang Anda Masukkan Salah Atau Tidak Lengkap Atau Memiliki Kesamaan Dengan Data Yang Sudah Ada";
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

    //fitur 3 DONE
    // view detail restoran
    @RequestMapping(value = "obat/view",method = RequestMethod.GET)
    public String view_detail_obat(@RequestParam(value = "noReg") String nomor_registrasi , Model model){
        ObatModel objek = obatService.findbynoreg(nomor_registrasi);
        ArrayList<SupplierModel> listAllSupplier= new ArrayList<SupplierModel>();
        ArrayList<GudangModel> listAllGudang = new ArrayList<GudangModel>();

        //mengambil data supplier
        for (Obat_SupplierModel dataobatsupplier : objek.getObatSupplierModels()){
            listAllSupplier.add(dataobatsupplier.getSupplier());
        }
        for (Gudang_ObatModel dataobatgudang : objek.getGudangObatModels()){
            listAllGudang.add(dataobatgudang.getGudang());
        }
        String navbartitle= "SIBAT";
        model.addAttribute("judul",navbartitle);
        model.addAttribute("objek",objek);
        model.addAttribute("listgudang",listAllGudang);
        model.addAttribute("listsupplier",listAllSupplier);
        return "view-detail-obat";
    }

    /**-----------------------------------------------------------*/

    //fitur 10 : filter obat DONE
    @RequestMapping(value = "/obat/filter",method = RequestMethod.GET)
    public String filter (@RequestParam(value = "idGudang",required = false)Long idGudang,
                          @RequestParam(value = "idSupplier",required = false)Long idSupplier,
                          @RequestParam(value = "idJenis",required = false)Long idJenis, Model model){
        //list frontend untuk tampilin masing masing
        List<GudangModel> listAllGudang = gudangService.getListGudang();
        List<SupplierModel> listAllSupplier = supplierService.getListSupplier();
        List<JenisModel> listAllJenis = jenisService.getListJenis();
        //list back end untuk filter
        GudangModel objekgudang= new GudangModel();
        SupplierModel objeksupplier = new SupplierModel();
        JenisModel objekjenis = new JenisModel();
        // list backend obat di x
        ArrayList<ObatModel> obatDiGudang = new ArrayList<>();
        ArrayList<ObatModel> obatDiSupplier = new ArrayList<>();
        List<ObatModel> obatDiJenis = new ArrayList<>();
        //list semua obat
        List<ObatModel> obatFiltered= obatService.getListObat();

        //variabel untuk menentukan apa yang dipilih
        GudangModel gudangDipilih = new GudangModel();
        JenisModel jenisDipilih = new JenisModel();
        SupplierModel supplierDipilih = new SupplierModel();

        //list obat pada objek gudang
        if(idGudang != null) {
            objekgudang = gudangService.getListGudangById(idGudang).get();
            System.out.println("masuk gudang");
            List<Gudang_ObatModel> gudang_obatModels = objekgudang.getGudangObatModels();
            for (Gudang_ObatModel each : gudang_obatModels) {
                obatDiGudang.add(each.getObat());
            }
            obatFiltered.retainAll(obatDiGudang);
            gudangDipilih = objekgudang;
        }
        // list obat pada supplier
        if(idSupplier!=null) {
            objeksupplier = supplierService.getListSupplierById(idSupplier).get();
            System.out.println("masuk supplier");
            List<Obat_SupplierModel> obat_supplierModels = objeksupplier.getObatSupplierModels();
            for (Obat_SupplierModel each : obat_supplierModels) {
                obatDiSupplier.add(each.getObat());
            }
            obatFiltered.retainAll(obatDiSupplier);
            supplierDipilih = objeksupplier;
        }
        // list obat pada jenis
        if(idJenis!=null) {
            objekjenis = jenisService.getListJenisById(idJenis).get();
            System.out.println("masuk jenis");
            obatDiJenis=  objekjenis.getListObat();
            obatFiltered.retainAll(obatDiJenis);
            jenisDipilih = objekjenis;
        }

        String navbartitle= "SIBAT";
        model.addAttribute("judul",navbartitle);
        model.addAttribute("listAllJenis",listAllJenis);
        model.addAttribute("listAllSupplier",listAllSupplier);
        model.addAttribute("listAllGudang",listAllGudang);
        model.addAttribute("obatFiltered",obatFiltered);
        model.addAttribute("gudangDipilih",gudangDipilih);
        model.addAttribute("supplierDipilih",supplierDipilih);
        model.addAttribute("jenisDipilih",jenisDipilih);
        return "filter-form";

    }
    /**-----------------------------------------------------------*/

    //fitur 4 : mengubah obat DONE
    //form obat
    @RequestMapping(value = "/obat/ubah",method = RequestMethod.GET)
    public String ubah_obat_form(@RequestParam(value = "id")Long id,Model model){
        ObatModel objekObat = obatService.findById(id);
        String navbartitle= "SIBAT";
        model.addAttribute("judul",navbartitle);
        model.addAttribute("objekObat",objekObat);
        return "ubah-obat-form";
    }
    //fitur 4 :mengubah obat DONE
    //submit obat
    @RequestMapping(value = "/obat/ubah",method = RequestMethod.POST)
    public String ubah_obat_submit(@ModelAttribute ObatModel objekObat, Model model){
        String navbartitle= "SIBAT";
        model.addAttribute("judul",navbartitle);
        try {
            ObatModel objekBaru = obatService.ubahObat(objekObat);
            model.addAttribute("objekBaru",objekBaru);
            model.addAttribute("header","SUCCESS!");
            model.addAttribute("pesan","Selamat! Obat Berhasil Dirubah!");
            model.addAttribute("flag",true);
            return "ubah-obat-notify";
        }catch (UnsupportedOperationException e){
            model.addAttribute("objekBaru",objekObat);
            model.addAttribute("header","WARNING!");
            model.addAttribute("pesan","Tidak Ada Perubahan Pada Obat Ini");
            model.addAttribute("flag",false);
            return "ubah-obat-notify";

        }

    }

    /**-----------------------------------------------------------*/

    //Fitur13: Bonus Jumlah supplier DONE
    @RequestMapping (value = "/bonus",method = RequestMethod.GET)
    public String bonus(Model model){
        String navbartitle= "SIBAT";
        List<ObatModel> listAllObat =  obatService.getListObat();
        model.addAttribute("judul",navbartitle);
        model.addAttribute("listAllObat",listAllObat);
        return "bonus";
    }

    /**-----------------------------------------------------------*/
    //fitur tambahan hapus obat DONE
    @RequestMapping (value = "/obat/hapus/{id}",method = RequestMethod.GET)
    public String hapus_obat(@PathVariable("id") Long id,Model model){
        ObatModel objekobat = obatService.findById(id);
        String navbartitle = "SIBAT";
        String buttonErr = "Lihat Daftar Obat";
        String buttonErr2 = "Tambah Obat";
        String linkbutton = "/";
        String linkbutton2 = "/obat/tambah";
        try{
            obatService.hapusObat(objekobat);
            System.out.println("masuk");
        }catch (UnsupportedOperationException e){
            model.addAttribute("objekdummy",objekobat);
            model.addAttribute("judul", navbartitle);
            model.addAttribute("buttonErr",buttonErr);
            model.addAttribute("linkbutton",linkbutton);
            model.addAttribute("buttonErr2",buttonErr2);
            model.addAttribute("linkbutton2",linkbutton2);
            return "delete-obat-error";
        }
        model.addAttribute("objekdummy",objekobat);
        model.addAttribute("judul", navbartitle);
        model.addAttribute("buttonErr",buttonErr);
        model.addAttribute("linkbutton",linkbutton);
        model.addAttribute("buttonErr2",buttonErr2);
        model.addAttribute("linkbutton2",linkbutton2);
        return "delete-obat";
    }
}
