package com.matching.project.repository;

import com.matching.project.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position, Long> {
    Optional<List<Position>> findByNoIn(Collection<Long> noList);
    Optional<Position> findAllByName(String position);
}
