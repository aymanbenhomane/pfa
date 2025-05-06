package admin_user.repositories;

import admin_user.model.UserSubmission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSubmissionRepository extends JpaRepository<UserSubmission, Long> {
}