package com.matching.project.repository;

import com.matching.project.dto.bookmark.BookMarkDto;
import com.matching.project.entity.BookMark;
import com.matching.project.entity.Project;
import com.matching.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface BookMarkRepository extends JpaRepository<BookMark, Long> {

    @Query("select b from BookMark b join fetch b.user u join fetch b.project p where u.no = :userNo")
    public Optional<List<BookMark>> findByUserNo(@Param("userNo") Long userNo);

    @Query("select case when count(b) > 0 then true else false end" +
            " from BookMark b" +
            " inner join b.user u" +
            " inner join b.project p" +
            " where u = :user and p = :project")
    public boolean existBookMark(@Param("user") User user, @Param("project") Project project);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from BookMark b where b.project.no = :projectNo")
    public void deleteByProjectNo(@Param("projectNo") Long projectNo);
}
