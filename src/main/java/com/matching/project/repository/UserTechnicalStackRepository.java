package com.matching.project.repository;

import com.matching.project.entity.User;
import com.matching.project.entity.UserTechnicalStack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface UserTechnicalStackRepository extends JpaRepository<UserTechnicalStack, Long> {
    @Query("select ust from UserTechnicalStack ust join fetch ust.technicalStack where ust.user.no = ?1")
    Optional<List<UserTechnicalStack>> findUserTechnicalStacksByUser(Long UserNo);
    void deleteAllByUser(User user);
}
