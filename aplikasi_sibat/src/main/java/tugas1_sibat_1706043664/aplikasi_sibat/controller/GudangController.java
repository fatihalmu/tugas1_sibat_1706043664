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
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
public class GudangController {
    @Qualifier("gudangServiceImpl")
    @Autowired
    private GudangService gudangService;

    @Autowired
    private  ObatService obatService;

    @Autowired
    private Gudang_ObatService gudang_obatService;
    //fitur 5 : menampilkan daftar gudang
    @RequestMapping(value = "/gudang",method = RequestMethod.GET)
    public String view_Daftar_gudang(Model model){
        String navbartitle= "SIBAT";
        List<GudangModel> listAllGudang =  gudangService.getListGudang();
        model.addAttribute("judul",navbartitle);
        model.addAttribute("listAllGudang",listAllGudang);
        return "view-daftar-gudang";
    }

    //fitur 6 : menampilkan detail gudang
    @RequestMapping(value = "/gudang/view",method = RequestMethod.GET)
    public String view_detail_gudang(@RequestParam(value = "idGudang") String idGudang,Model model){
        GudangModel objekGudang = gudangService.getListGudangById(Long.valueOf(idGudang)).get();
        List<Gudang_ObatModel> gudang_obatModel= objekGudang.getGudangObatModels();
        System.out.println("masuk yang pertama");

        Gudang_ObatModel dummyObatDitambah = new Gudang_ObatModel();
        dummyObatDitambah.setGudang(objekGudang);
        System.out.println("setelah buat dummy");



        ArrayList<ObatModel> obatDiGudang = new ArrayList<ObatModel>();
        List<ObatModel> listAllObat = obatService.getListObat();

        //masukkan obat yang dimiliki kedalam obatDiGudang
        for(Gudang_ObatModel eachobject:gudang_obatModel){
            obatDiGudang.add(eachobject.getObat());
        }
        for(int i =0 ; i < listAllObat.size();i++){
            ObatModel objekObat = listAllObat.get(i);
            if(obatDiGudang.contains(objekObat)){
                listAllObat.remove(objekObat);
            }
        }

        String navbartitle= "SIBAT";
        model.addAttribute("judul",navbartitle);
        model.addAttribute("objekGudang",objekGudang);
        model.addAttribute("obatDiGudang",obatDiGudang);
        model.addAttribute("obatTidakDiGudang",listAllObat);
        model.addAttribute("dummyObatDitambah",dummyObatDitambah);
        System.out.println("ini gudang :"+dummyObatDitambah.getGudang());
        return "view-detail-gudang";
    }

    //fitur 7 :tambah gudang form
    @RequestMapping(value = "/gudang/tambah",method = RequestMethod.GET)
    public String tambah_gudang_form(Model model){
        GudangModel objekdummy = new GudangModel();
        String navbartitle= "SIBAT";
        model.addAttribute("judul",navbartitle);
        model.addAttribute("objekdummy",objekdummy);
        return "form-tambah-gudang";
    }

    //fitur 7 :tambah gudang submit
    @RequestMapping(value = "/gudang/tambah",method = RequestMethod.POST)
    public String tambah_gudang_submit(@ModelAttribute GudangModel objekdummy, Model model){
        System.out.println("nama "+objekdummy.getNama());
        System.out.println("alamat "+objekdummy.getAlamat());
        System.out.println("id "+objekdummy.getId());
        gudangService.tambahGudang(objekdummy);
        String navbartitle= "SIBAT";
        model.addAttribute("judul",navbartitle);
        model.addAttribute("objekdummy",objekdummy);
        return "form-tambah-gudang-notify";
    }

    //fitur 8 : hapus gudang by id
    @RequestMapping (value = "/gudang/hapus/{id}",method = RequestMethod.GET)
    public String hapus_gudang(@PathVariable("id") Long id,Model model){
        GudangModel objekgudang = gudangService.getListGudangById(id).get();
        try{
            gudangService.deleteGudangById(id);
        }catch (UnsupportedOperationException e){
            model.addAttribute("errorMessage", "Tidak berhasil dihapus karena gudang memiliki obat!");
            return "delete-gudang-error";
        }
        model.addAttribute("objek",objekgudang);
        return "delete-gudang";
    }

    //fitur 9
    @RequestMapping(value = "/gudang/tambah-obat",method = RequestMethod.POST)
    public String  tambah_obat_di_gudang(@ModelAttribute("dummyObatDitambah") @Valid Gudang_ObatModel dummyObatDitambah,
                                      BindingResult result, Model model){
        if(result.hasErrors()){
            System.out.println("masuk karena error");
            System.out.println(result.getAllErrors());
            System.out.println(result.getTarget());
            System.out.println(result.getSuppressedFields());
            return "tes";
        }
        System.out.println("masuk kedua");
        System.out.println("obatnya :" + dummyObatDitambah.getObat() +" gudangnya: "+ dummyObatDitambah.getGudang());
        //dummyObatDitambah.setGudang();
        gudang_obatService.tambahgudangobat(dummyObatDitambah);
        //untuk ambil id
        GudangModel objekGudang = dummyObatDitambah.getGudang();
        view_detail_gudang(String.valueOf(objekGudang.getId()),model);
        model.addAttribute("dummyObatDitambah",dummyObatDitambah);

        return "add-obat-to-gudang-notify";

    }


}
