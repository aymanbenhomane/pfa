package admin_user.controller;

import admin_user.dto.UserSubmissionDTO;
import admin_user.service.UserSubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;
import java.io.IOException;

@Controller
@RequestMapping("/user")
public class UserSubmissionController {

    @Autowired
    private UserSubmissionService service;

    @PostMapping("/save")
    public String saveSubmission(@ModelAttribute UserSubmissionDTO submissionDTO, Model model) {
        try {
            service.saveUserSubmission(submissionDTO);
            model.addAttribute("message", "Soumission réussie !");
        } catch (IOException e) {
            model.addAttribute("message", "Erreur lors du téléchargement du fichier.");
        }
        return "redirect:/dashboard"; // Ou une vue de confirmation
    }
}