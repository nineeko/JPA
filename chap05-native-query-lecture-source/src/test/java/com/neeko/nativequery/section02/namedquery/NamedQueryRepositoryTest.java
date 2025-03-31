package com.neeko.nativequery.section02.namedquery;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NamedQueryRepositoryTest {

    @Autowired
    private NamedQueryRepository namedQueryRepository;

    @DisplayName("NamedNativeQuery를 이용한 조회 테스트")
    @Test
    void testSelectByNamedNativeQuery() {
        List<Object[]> categoryList = namedQueryRepository.selectByNamedNativeQuery();
        assertNotNull(categoryList);
        categoryList.forEach(row -> {
            for(Object column : row) System.out.print(column + " ");
            System.out.println();
        });
    }

}