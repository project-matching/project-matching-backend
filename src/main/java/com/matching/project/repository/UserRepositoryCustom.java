package com.matching.project.repository;

import com.matching.project.dto.user.UserFilterDto;
import com.matching.project.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryCustom {
    Optional<Slice<User>> findByNoOrderByNoDescUsingQueryDsl(Long userNo, UserFilterDto userFilterDto, Pageable pageable);
}
