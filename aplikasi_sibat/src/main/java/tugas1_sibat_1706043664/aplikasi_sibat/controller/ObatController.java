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


    //fitur 1
    //nampilin semua obat yang tersedia pada list
    //view all
    @RequestMapping("/")
    public String beranda(Model model){
        String navbartitle= "BERANDA";
        List<ObatModel> listAllObat =  obatService.getListObat();
        model.addAttribute("judul",navbartitle);
        model.addAttribute("listAllObat",listAllObat);
        return "home";
    }
    //fitur 2
    //tambah obat form
    @RequestMapping(value = "/obat/tambah", method = RequestMethod.GET)
    public String tambah_obat_form(Model model){
        ObatModel objekdummy = new ObatModel();
        Obat_SupplierModel obatSupplierModel = new Obat_SupplierModel();
        objekdummy.setObatSupplierModels(new ArrayList<Obat_SupplierModel>());
        objekdummy.getObatSupplierModels().add(obatSupplierModel);
        System.out.println("ini id setelah di inisiasi" + objekdummy.getId());

        List<JenisModel> listAllJenis = jenisService.getListJenis();
        List<SupplierModel> listAllSupplier = supplierService.getListSupplier();
        String navbartitle= "SIBAT";
        model.addAttribute("judul",navbartitle);
        model.addAttribute("objekdummy",objekdummy);

        System.out.println("ini id setelah objek di lempar" + objekdummy.getId());

        model.addAttribute("listAllJenis",listAllJenis);
        model.addAttribute("listAllSupplier",listAllSupplier);
        return "form-tambah-obat";

    }
    // fitur 2
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
    //fitur2
    // submit tambahobat
    @RequestMapping(value = "/obat/tambah", method = RequestMethod.POST)
    public String tambah_obat_submit( @ModelAttribute ObatModel objekdummy, Model model){

        objekdummy.setKode(buat_kode(objekdummy));
        System.out.println(objekdummy.getKode());

        for (int i= 0 ;  i < objekdummy.getObatSupplierModels().size();i++){
            objekdummy.getObatSupplierModels().get(i).setObat(objekdummy);
        }

        obatService.tambahObat(objekdummy);

        for (Obat_SupplierModel objek : objekdummy.getObatSupplierModels()){
            obatSupplierService.tambahobatsupplier(objek);
        }

        System.out.println("TANGGAL TERBIT : " + objekdummy.getTanggalTerbit());
        String navbartitle= "SIBAT";
        model.addAttribute("judul",navbartitle);
        model.addAttribute("objekdummy",objekdummy);
        return "form-tambah-obat-notify";

    }

    //method untuk buat kode
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

    //fitur 3
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

    //fitur 10 : filter obat
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

    //fitur 4 : mengubah obat
    //form obat
    @RequestMapping(value = "/obat/ubah",method = RequestMethod.GET)
    public String ubah_obat_form(@RequestParam(value = "id")Long id,Model model){
        ObatModel objekObat = obatService.findById(id);
        String navbartitle= "SIBAT";
        model.addAttribute("judul",navbartitle);
        model.addAttribute("objekObat",objekObat);
        return "ubah-obat-form";
    }
    //fitur 4 :mengubah obat
    //submit obat
    @RequestMapping(value = "/obat/ubah",method = RequestMethod.POST)
    public String ubah_obat_submit(@ModelAttribute ObatModel objekObat, Model model){
       ObatModel objekBaru = obatService.ubahObat(objekObat);
        String navbartitle= "SIBAT";
        model.addAttribute("judul",navbartitle);
        model.addAttribute("objekBaru",objekBaru);
        return "ubah-obat-notify";
    }

    //Fitur13: Bonus Jumlah supplier
    @RequestMapping (value = "/bonus",method = RequestMethod.GET)
    public String bonus(Model model){
        String navbartitle= "BONUS";
        List<ObatModel> listAllObat =  obatService.getListObat();
        model.addAttribute("judul",navbartitle);
        model.addAttribute("listAllObat",listAllObat);


        return "bonus";
    }
}
