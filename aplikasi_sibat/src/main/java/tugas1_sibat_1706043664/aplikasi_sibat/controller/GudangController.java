package tugas1_sibat_1706043664.aplikasi_sibat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tugas1_sibat_1706043664.aplikasi_sibat.model.GudangModel;
import tugas1_sibat_1706043664.aplikasi_sibat.model.Gudang_ObatModel;
import tugas1_sibat_1706043664.aplikasi_sibat.model.ObatModel;
import tugas1_sibat_1706043664.aplikasi_sibat.service.GudangService;
import tugas1_sibat_1706043664.aplikasi_sibat.service.ObatService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
public class GudangController {
    @Qualifier("gudangServiceImpl")
    @Autowired
    private GudangService gudangService;
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
        ArrayList<ObatModel> obatDiGudang = new ArrayList<ObatModel>();

        //masukkan obat yang dimiliki kedalam obatDiGudang
        for(Gudang_ObatModel eachobject:gudang_obatModel){
            obatDiGudang.add(eachobject.getObat());
        }

        String navbartitle= "SIBAT";
        model.addAttribute("judul",navbartitle);
        model.addAttribute("objekGudang",objekGudang);
        model.addAttribute("obatDiGudang",obatDiGudang);

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

}
