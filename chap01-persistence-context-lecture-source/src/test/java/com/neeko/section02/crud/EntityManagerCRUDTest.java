package com.neeko.section02.crud;

import com.neeko.section01.entitymanager.EntityManagerGenerator;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
class EntityManagerCRUDTest {
private EntityManagerCRUD entityManagerCRUD;

    @BeforeEach
    void init(){
        this.entityManagerCRUD = new EntityManagerCRUD();

    }
    @DisplayName("메뉴 코드로 메뉴 조회")
    @ParameterizedTest
    @CsvSource({"1,1","2,2","3,3"})
    void testFindMenuByMenuCode(int menuCode, int expected) {
        // when
        Menu foundMenu = entityManagerCRUD.findMenuByMenuCode(menuCode);
        // then
        assertEquals(expected, foundMenu.getMenuCode());
        System.out.println("foundMenu = " + foundMenu);
    }

    private static Stream<Arguments> newMenu(){
        return Stream.of(
                Arguments.of(
                        "신메뉴", 3500, 4, "Y"
                )
        );
    }

    @DisplayName("새로운 메뉴 추가")
    @ParameterizedTest
    @MethodSource("newMenu")
    void testRegist(String menuName, int menuPrice, int categoryCode,String orderableStatus) {
        // when
        Menu menu = new Menu(menuName,menuPrice,categoryCode,orderableStatus);
        Long count = entityManagerCRUD.saveAndReturnAllCount(menu);

        // then
        assertEquals(27, count); //expected는 현재 데이터베이스 menu 테이블의 행 수 +1
    }

    @DisplayName("메뉴 이름 수정 테스트")
    @ParameterizedTest
    @CsvSource("1. 변경된 이름")
    void testModifyMenuName(int menuCode, String menuName){
        // when
        Menu modifiedMenu = entityManagerCRUD.modifyMenuname(menuCode,menuName);
        // then
        assertEquals(menuName, modifiedMenu.getMenuName());
    }


    @DisplayName("메뉴 코드로 메뉴 삭제 테스트")
    @ParameterizedTest
    @ValueSource(ints = {21})
    void testRemoveMenu(int menuCode){
        // when
        Long count = entityManagerCRUD.removeAndReturnAllCount(menuCode);
        // then
        assertEquals(24, count);
    }


}