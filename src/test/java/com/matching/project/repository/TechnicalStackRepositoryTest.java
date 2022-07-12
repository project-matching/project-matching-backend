package com.matching.project.repository;

import com.matching.project.config.QuerydslConfiguration;
import com.matching.project.entity.TechnicalStack;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Import(QuerydslConfiguration.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class TechnicalStackRepositoryTest {
    @Autowired
    TechnicalStackRepository technicalStackRepository;

    @Test
    public void 기술_스택_IN_조회() {
        // given
        TechnicalStack technicalStack1 = TechnicalStack.builder()
                .name("testTechnicalStack1")
                .build();
        TechnicalStack technicalStack2 = TechnicalStack.builder()
                .name("testTechnicalStack2")
                .build();
        TechnicalStack technicalStack3 = TechnicalStack.builder()
                .name("testTechnicalStack3")
                .build();
        technicalStackRepository.save(technicalStack1);
        technicalStackRepository.save(technicalStack2);
        technicalStackRepository.save(technicalStack3);

        // when
        List<String> names = new ArrayList<>();
        names.add("testTechnicalStack1");
        names.add("testTechnicalStack2");
        names.add("testTechnicalStack3");
        List<TechnicalStack> findTechnicalStackList = technicalStackRepository.findByNameIn(names);

        // then
        assertEquals(findTechnicalStackList.size(), 3);

        assertEquals(findTechnicalStackList.get(0).getName(), technicalStack1.getName());
        assertEquals(findTechnicalStackList.get(1).getName(), technicalStack2.getName());
        assertEquals(findTechnicalStackList.get(2).getName(), technicalStack3.getName());
    }
}