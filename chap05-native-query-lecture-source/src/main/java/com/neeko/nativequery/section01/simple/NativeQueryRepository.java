package com.neeko.nativequery.section01.simple;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NativeQueryRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public Menu nativeQueryByResultType(int menuCode){
        /* Native Query 수행 결과를 엔터티에 매핑시키기 위해서는 모든 커럶이 처리되어야만 매핑 가능*/
        String query = "SELECT menu_code, menu_name, menu_price, category_code, "+
                "orderable_status FROM tbl_menu WHERE menu_code = ?";
        Query nativeQuery = entityManager.createNativeQuery(query, Menu.class)
                .setParameter(1, menuCode);
        return (Menu) nativeQuery.getSingleResult();
    }

    public List<Object[]> nativeQueryByNoResultType(){
        /* Native Query 수행 결과를 엔터티에 매핑시키기 위해서는 모든 커럶이 처리되어야만 매핑 가능*/
        String query = "SELECT menu_name, menu_price FROM tbl_menu";
        Query nativeQuery = entityManager.createNativeQuery(query);
        return nativeQuery.getResultList();
    }

    public List<Object[]> nativeQueryByAutoMapping() {
        String query
                = "SELECT a.category_code, a.category_name, a.ref_category_code," +
                " COALESCE(v.menu_count, 0) menu_count" +
                " FROM tbl_category a" +
                " LEFT JOIN (SELECT COUNT(*) AS menu_count, b.category_code" +
                " FROM tbl_menu b" +
                " GROUP BY b.category_code) v ON (a.category_code = v.category_code)" +
                " ORDER BY 1";

        Query nativeQuery
                = entityManager.createNativeQuery(query, "categoryCountAutoMapping");
        return nativeQuery.getResultList();
    }
    public List<Object[]> nativeQueryByManualMapping() {
        String query
                = "SELECT a.category_code, a.category_name, a.ref_category_code," +
                " COALESCE(v.menu_count, 0) menu_count" +
                " FROM tbl_category a" +
                " LEFT JOIN (SELECT COUNT(*) AS menu_count, b.category_code" +
                " FROM tbl_menu b" +
                " GROUP BY b.category_code) v ON (a.category_code = v.category_code)" +
                " ORDER BY 1";

        Query nativeQuery
                = entityManager.createNativeQuery(query, "categoryCountAutoMapping");
        return nativeQuery.getResultList();
    }
}
