package com.neeko.section02.crud;

import com.neeko.section01.entitymanager.EntityManagerGenerator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class EntityManagerCRUD {

    private EntityManager entityManager;
    /* 1. 특정 메뉴 코드로 메뉴를 조회하는 기능 */
    public Menu findMenuByMenuCode(int menuCode) {
        entityManager = EntityManagerGenerator.getInstance();

        return entityManager.find(Menu.class, menuCode);
    }
    /* 2. 새로운 메뉴 저장하는 기능*/
    public Long saveAndReturnAllCount(Menu newMenu){
        entityManager = EntityManagerGenerator.getInstance();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        entityManager.persist(newMenu);
        entityTransaction.commit();

        return getCount(entityManager);

    }
    private Long getCount(EntityManager entityManager) {
        // JPQL
        return entityManager.createQuery("SELECT COUNT(*) FROM Section02Menu ", Long.class).getSingleResult();

    }


    /* 3. 메뉴 이름 수정 가능 */
    public Menu modifyMenuname(int menuCode, String menuName) {
        entityManager = EntityManagerGenerator.getInstance();
        Menu foundMenu = entityManager.find(Menu.class, menuCode);
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        foundMenu.setMenuName(menuName);
        entityTransaction.commit();
        return foundMenu;
    }

    /* 특정 메뉴 코드로 메뉴 삭제하는 기능*/
    public Long removeAndReturnAllCount(int menuCode) {
        entityManager = EntityManagerGenerator.getInstance();
        Menu foundMenu = entityManager.find(Menu.class, menuCode);

        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        entityManager.remove(foundMenu);

        entityTransaction.commit();

        return getCount(entityManager);


    }
}
