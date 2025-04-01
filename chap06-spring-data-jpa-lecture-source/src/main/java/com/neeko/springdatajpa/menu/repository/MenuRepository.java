package com.neeko.springdatajpa.menu.repository;

import com.neeko.springdatajpa.menu.dto.MenuProjection;
import com.neeko.springdatajpa.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Integer> {
    // 메뉴 코드를 기준으로 내림차순 정렬한 DTO 프로젝션 반환 메소드
    List<MenuProjection> findAllByOrderByMenuCodeDesc();

    // 전달 받은 가격을 초과하는 메뉴 목록 조회
    List<Menu> findByMenuPriceGreaterThan(Integer menuPrice);
}

