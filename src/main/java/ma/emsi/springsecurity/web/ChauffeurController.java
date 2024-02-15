package ma.emsi.springsecurity.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ma.emsi.springsecurity.entities.Chauffeur;
import ma.emsi.springsecurity.repositories.ChauffeurRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
@Controller
@AllArgsConstructor
public class ChauffeurController {
    private ChauffeurRepository chauffeurRepository;

    @GetMapping(path="/user/index")
    public String chauffeurs(Model model,
                           @RequestParam(name = "page",defaultValue = "0") int page,
                           @RequestParam(name = "size",defaultValue = "5") int size,
                           @RequestParam(name = "keyword",defaultValue = "") String keyword
    ){
        Page<Chauffeur> pagehChauffeurs= chauffeurRepository.findByNomContains(keyword,PageRequest.of(page,size));
        model.addAttribute("listChauffeurs",pagehChauffeurs.getContent());
        model.addAttribute("pages",new int[pagehChauffeurs.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",keyword);
        return "Chauffeur";
    }

    @GetMapping("/admin/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(Long id, String keyword, int page) {

        chauffeurRepository.deleteById(id);
        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/user/index";
    }

    @GetMapping("/user/chauffeurs")
    @ResponseBody
    public List<Chauffeur> lisChauffeurs(){
        return chauffeurRepository.findAll();
    }

    @GetMapping("/admin/formChauffeurs")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String formChauffeurs(Model model){
        model.addAttribute("chauffeur",new Chauffeur());
        return "formChauffeur";
    }

    @GetMapping("/admin/editChauffeurs")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editChauffeurs(Model model,Long id , String keyword, int page){
        Chauffeur chauffeur = chauffeurRepository.findById(id).orElse(null);
        if(chauffeur ==null) throw new RuntimeException("Chauffeur introuvable");
        model.addAttribute("chauffeur", chauffeur);
        model.addAttribute("page", page);
        model.addAttribute("keyword",keyword);
        return "editChauffeur";
    }

    @PostMapping(path="/admin/save")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String save(Model model,
                       @Valid Chauffeur chauffeur,
                       BindingResult bindingResult,
                       @RequestParam(defaultValue = "0") int page ,
                       @RequestParam(defaultValue = "") String keyword) {

        if(bindingResult.hasErrors())
            return "formChauffeur";

        chauffeurRepository.save(chauffeur);
        return "redirect:/user/index";
    }
}