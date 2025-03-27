package com.neeko.section03.entity;

import com.neeko.section01.entitymanager.EntityManagerGenerator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class EntityLifeCycleTest {
    private EntityLifeCycle entityLifeCycle;
    @BeforeEach
    void init(){
        entityLifeCycle = new EntityLifeCycle();


    }

    @DisplayName("비영속 테스트")
    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    void testTransient(int menuCode){
        // when
        Menu foundMenu = entityLifeCycle.findMenuByMenuCode(menuCode);

        Menu newMenu = new Menu(
                foundMenu.getMenuCode(),
                foundMenu.getMenuName(),
                foundMenu.getMenuPrice(),
                foundMenu.getCategoryCode(),
                foundMenu.getOrderableStatus()
        );

        EntityManager entityManager = entityLifeCycle.getMangerInstance();

        // then
        assertTrue(entityManager.contains(foundMenu)); // 영속성 컨텍스트에서 관리되는 영속 상태
        assertFalse(entityManager.contains(newMenu));  // 영속성 컨텍스트에서 관리되지 안흔 비영속 상태


    }

    @DisplayName("다른 엔터티 매니저가 관리하는 엔티티의 영속성 테스트")
    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    void testManagedOtherEntityManager(int menuCode){

    // when
    Menu menu1 = entityLifeCycle.findMenuByMenuCode(menuCode);
    Menu menu2 = entityLifeCycle.findMenuByMenuCode(menuCode);
    // then
    assertNotEquals(menu1, menu2);
    }


    @DisplayName("같은 엔터티 매니저가 관리하는 엔티티의 영속성 테스트")
    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    void testManagedSameEntityManager(int menuCode){

        // when
        EntityManager entityManager =
                EntityManagerGenerator.getInstance();
        Menu menu1 = entityManager.find(Menu.class, menuCode);
        Menu menu2 = entityManager.find(Menu.class, menuCode);
        // then
        assertEquals(menu1, menu2);
    }

    @DisplayName("준영속화 detach 테스트")
    @ParameterizedTest
    @CsvSource({"11, 1000","12, 1000"})
    void testDetachedEntity(int menuCode,int menuPrice){

        // when
        EntityManager entityManager =
                EntityManagerGenerator.getInstance();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Menu foundMenu = entityManager.find(Menu.class, menuCode);
        // detach: 특정 엔터티만 준영속 상태(관리하던 객체를 관리하지 않는 상태로 변경)로 만듦
        entityManager.detach(foundMenu);
        foundMenu.setMenuPrice(menuPrice);
        // flush : 영속성 컨텍스트의 상태를 DB로 내보낸다. 단, commit하지 않았을 경우 rollback 가능
        entityManager.flush();

        // then
        assertNotEquals(menuPrice, entityManager.find(Menu.class, menuCode).getMenuPrice());
        entityTransaction.rollback();
    }

    @DisplayName("준영속화 detach 후 영속화 테스트")
    @ParameterizedTest
    @CsvSource({"11, 1000","12, 1000"})
    void testDetachAndMerge(int menuCode,int menuPrice){

        // when
        EntityManager entityManager =
                EntityManagerGenerator.getInstance();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Menu foundMenu = entityManager.find(Menu.class, menuCode);
        // detach: 특정 엔터티만 준영속 상태(관리하던 객체를 관리하지 않는 상태로 변경)로 만듦
        entityManager.detach(foundMenu);
        foundMenu.setMenuPrice(menuPrice);
        // merge : 파라미터로 넘어온 준영속 엔터티 객체의 식별자 값으로 1차 캐시에서 엔터티 객체를 조회한다.
        // (없으면 DB에서 조회하여 1차 캐시에 저장한다.)
        // 조회한 영속 엔터티 객체에 준영속 상태의 엔터티 객체의 값을 병합한 뒤 영속 엔터티 객체를 반환한다.
        // 혹은 조회할 수 없는 데이터라면 새로 생성해서 반환한다.
        entityManager.merge(foundMenu);
        entityManager.flush();

        // then
        assertEquals(menuPrice, entityManager.find(Menu.class, menuCode).getMenuPrice());
        entityTransaction.rollback();
    }
}