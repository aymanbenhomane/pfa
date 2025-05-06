package admin_user.service;


import admin_user.dto.UserSubmissionDTO;
import admin_user.model.UserSubmission;
import admin_user.repositories.UserSubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

@Service
public class UserSubmissionService {

    @Autowired
    private UserSubmissionRepository repository;

    private final String uploadDir = "uploads/";

    public void saveUserSubmission(UserSubmissionDTO dto) throws IOException {
        MultipartFile cv = dto.getCv();
        String fileName = System.currentTimeMillis() + "_" + cv.getOriginalFilename();
        Path filePath = Paths.get(uploadDir + fileName);

        // Crée le dossier s’il n’existe pas
        Files.createDirectories(filePath.getParent());
        Files.copy(cv.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        UserSubmission submission = new UserSubmission();
        submission.setNom(dto.getNom());
        submission.setPrenom(dto.getPrenom());
        submission.setSpecialite(dto.getSpecialite());
        submission.setCvFileName(fileName);

        repository.save(submission);
    }
}