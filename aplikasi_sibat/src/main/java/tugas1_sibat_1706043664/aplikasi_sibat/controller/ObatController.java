package tugas1_sibat_1706043664.aplikasi_sibat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tugas1_sibat_1706043664.aplikasi_sibat.model.*;
import tugas1_sibat_1706043664.aplikasi_sibat.service.*;

import java.awt.*;
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
        System.out.println("ini id setelah objek di lempar kembali ke method tambah" + objekdummy.getId());
        //pembuatan kode
        String idjenis = String.valueOf(objekdummy.getJenis().getId());
        String bentuk = new String();
        System.out.println("bentuk obat dari html :"+objekdummy.getBentuk());
        if(objekdummy.getBentuk().equals("Cairan")) {
             bentuk += "01";
        }else if(objekdummy.getBentuk().equals("Kapsul")){
             bentuk += "02";
        }else if(objekdummy.getBentuk().equals("Tablet")){
             bentuk+="03";
            System.out.println("masuk");
        }
        System.out.println("bentuk obat : " + bentuk);
        String tahun = String.valueOf(objekdummy.getDibuat().getYear()+1900);
        String tahuntambah5 = String.valueOf(objekdummy.getTanggalTerbit().getYear()+1905);
        String random = getAlphaNumericString().toUpperCase();
        objekdummy.setKode(idjenis+ bentuk +tahun+tahuntambah5+random);
        System.out.println(objekdummy.getKode());
        //System.out.println(objekdummy.getObatSupplierModels().get(0).getSupplier().getNama());

        for (int i= 0 ;  i < objekdummy.getObatSupplierModels().size();i++){
            objekdummy.getObatSupplierModels().get(i).setObat(objekdummy);
        }

        System.out.println("ini id saat objek ingin disave" + objekdummy.getId());
        obatService.tambahObat(objekdummy);
        System.out.println("ini id saat objek telah di save" + objekdummy.getId());

        for (Obat_SupplierModel objek : objekdummy.getObatSupplierModels()){
            obatSupplierService.tambahobatsupplier(objek);
        }
        String navbartitle= "SIBAT";
        model.addAttribute("judul",navbartitle);
        model.addAttribute("objekdummy",objekdummy);
        return "form-tambah-obat-notify";

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

    //fitur 4
    /**
     //fitur 10 nampilin form
     @RequestMapping(value = "obat/filter",method = RequestMethod.GET)
     public String filter_form(Model model){
     List<GudangModel> listAllGudang= gudangService.getListGudang();

     return "filter-form";
     }*/
    //fitur 10 ketika tekan search
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

        model.addAttribute("listAllJenis",listAllJenis);
        model.addAttribute("listAllSupplier",listAllSupplier);
        model.addAttribute("listAllGudang",listAllGudang);
        model.addAttribute("obatFiltered",obatFiltered);
        model.addAttribute("gudangDipilih",gudangDipilih);
        model.addAttribute("supplierDipilih",supplierDipilih);
        model.addAttribute("jenisDipilih",jenisDipilih);
        return "filter-form";

    }
}
