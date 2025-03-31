package com.neeko.jpql.section04.paging;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PagingRepositoryTest {

    @Autowired
    private PagingRepository pagingRepository;
    
    @DisplayName("페이징 api를 이용한 조회 테스트")
    @Test
    void testUsingPagingAPI(){
        int offset = 5;
        int limit = 5;

        List<Menu> menuList = pagingRepository.usingPagingAPI(offset, limit);
        assertNotNull(menuList);
        assertTrue(menuList.size() <= limit);
        menuList.forEach(System.out::println);
    }
}