package tugas1_sibat_1706043664.aplikasi_sibat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tugas1_sibat_1706043664.aplikasi_sibat.model.GudangModel;
import tugas1_sibat_1706043664.aplikasi_sibat.model.ObatModel;
import tugas1_sibat_1706043664.aplikasi_sibat.service.GudangService;
import tugas1_sibat_1706043664.aplikasi_sibat.service.ObatService;

import java.util.List;

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
}
