package com.neeko.jpql.section06.join;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JoinRepositoryTest {

    @Autowired
    private JoinRepository joinRepository;

    @DisplayName("inner join 테스트")
    @Test
    void testSelectByInnerJoin(){
        List<Menu> menus = joinRepository.selectByInnerJoin();
        assertNotNull(menus);
    }

    @DisplayName("join fetch 테스트")
    @Test
    void testSelectByFetchJoin(){
        List<Menu> menus = joinRepository.selectByFetchJoin();
        assertNotNull(menus);
    }
}