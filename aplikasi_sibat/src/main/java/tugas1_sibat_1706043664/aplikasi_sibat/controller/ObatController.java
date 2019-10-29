package tugas1_sibat_1706043664.aplikasi_sibat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tugas1_sibat_1706043664.aplikasi_sibat.model.*;
import tugas1_sibat_1706043664.aplikasi_sibat.service.JenisService;
import tugas1_sibat_1706043664.aplikasi_sibat.service.ObatService;
import tugas1_sibat_1706043664.aplikasi_sibat.service.Obat_SupplierService;
import tugas1_sibat_1706043664.aplikasi_sibat.service.SupplierService;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

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
    // addrow  suppliersubmit tambah obat
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
    //fitur2
    // submit tambahobat
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
    @RequestMapping(value = "/obat/tambah", method = RequestMethod.POST)
    public String tambah_obat_submit( @ModelAttribute ObatModel objekdummy, Model model){
        System.out.println("ini id setelah objek di lempar kembali ke method tambah" + objekdummy.getId());

        String idjenis = String.valueOf(objekdummy.getJenis().getId());
        String bentuk = objekdummy.getBentuk();
        String tahun = String.valueOf(objekdummy.getDibuat().getYear()+1900);
        String tahuntambah5 = String.valueOf(objekdummy.getTanggalTerbit().getYear()+1905);
        String random = getAlphaNumericString().toUpperCase();
        objekdummy.setKode(idjenis+bentuk+tahun+tahuntambah5+random);
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



}
