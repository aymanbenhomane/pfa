package admin_user.dto;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserSubmissionDTO {
    private String nom;
    private String prenom;
    private String specialite;
    private MultipartFile cv;
}
