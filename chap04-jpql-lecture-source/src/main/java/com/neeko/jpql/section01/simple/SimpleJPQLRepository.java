package com.neeko.jpql.section01.simple;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SimpleJPQLRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public String selectSingleMenuByTypedQuery(){
        // 별칭은 필수 as는 생략 가능
        String jpql = "SELECT m.menuName From Section01Menu as m WHERE m.menuCode = 8";

        TypedQuery<String> query = entityManager.createQuery(jpql, String.class);
        String resultMenuName = query.getSingleResult();
        return resultMenuName;
    }
    public Object selectSingleMenuByQuery(){
        // 별칭은 필수 as는 생략 가능
        String jpql = "SELECT m.menuName From Section01Menu as m WHERE m.menuCode = 8";

        Query query = entityManager.createQuery(jpql);
        Object resultMenuName = query.getSingleResult();
        return resultMenuName;
    }

    public Menu selectSingleRowByTypeQuery(){
        // 별칭은 필수 as는 생략 가능
        String jpql = "SELECT m From Section01Menu as m WHERE m.menuCode = 8";

        TypedQuery<Menu> query = entityManager.createQuery(jpql, Menu.class);
        Menu resultMenuName = query.getSingleResult();
        return resultMenuName;
    }

    public List<Menu> selectMultiRowByTypeQuery(){
        // 별칭은 필수 as는 생략 가능
        String jpql = "SELECT m From Section01Menu as m";

        TypedQuery<Menu> query = entityManager.createQuery(jpql, Menu.class);
        List<Menu> resultMenuList = query.getResultList();
        return resultMenuList;
    }

    /* tbl_menu의 categoryCode 중복없이 조회 */
    public List<Integer> selectUsingDistinct(){
        String jpql = "SELECT DISTINCT(m.categoryCode) From Section01Menu as m";
        TypedQuery<Integer> query = entityManager.createQuery(jpql, Integer.class);
        List<Integer> resultCategoryCode = query.getResultList();
        return resultCategoryCode;
    }

    /* tbl_menu의 11, 12 카테고리 코드를 가진 메뉴 목록 조회 */
    public List<Menu> selectUsingIn(){
        String jpql = "SELECT m From Section01Menu as m where m.menuCode in (11, 12)";

        TypedQuery<Menu> query = entityManager.createQuery(jpql, Menu.class);
        List<Menu> resultMenuList = query.getResultList();
        return resultMenuList;

    }

    /* tbl_menu의 "마늘"이 메뉴명에 포함된 메뉴 목록 조회 */
    public List<Menu> selectUsingLike(){
        String jpql = "SELECT m From Section01Menu as m where m.menuName like '%마늘%'";

        TypedQuery<Menu> query = entityManager.createQuery(jpql, Menu.class);
        List<Menu> resultMenuList = query.getResultList();
        return resultMenuList;
    }

}
