package com.neeko.jpql.section03.projection;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLOutput;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProjectionRepositoryTest {

    @Autowired
    private ProjectionRepository projectionRepository;

    @DisplayName("임베디드 타입 프로젝션")
    @Test
    void testEmbeddedTypeProjection() {
        List<MenuInfo> menuInfos = projectionRepository.embeddedTypeProjection();
        assertNotNull(menuInfos);
    }
    @DisplayName("스칼라 타입 프로젝션")
    @Test
    void testScalarTypeProjection() {
        List<Object[]> categoryInfos = projectionRepository.scalarTpyeProjection();
        assertNotNull(categoryInfos);
        categoryInfos.forEach(
                row -> {
                    for(Object column : row) {
                        System.out.print(column+" ");
                    }
                    System.out.println();
                }
        );

    }
    @DisplayName("new 명령어 프로젝션")
    @Test
    void testNewCommandProjection() {
        List<CategoryInfo> categoryInfos = projectionRepository.newCommandProjection();
        assertNotNull(categoryInfos);
        categoryInfos.forEach(System.out::println);

    }


}