package com.neeko.springdatajpa.menu.repository;

import com.neeko.springdatajpa.menu.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    /* JPQL or Native Query를 작성하는 방법 */

    @Query(
//            value = "SELECT c FROM Category c ORDER BY c.categoryCode"
            value = "SELECT category_code, category_name, ref_category_code "
            + "FROM tbl_category ORDER BY category_code",
            nativeQuery = true // JPQL일 경우 생략 (default false)
    )
    List<Category> findAllCategory();
}
