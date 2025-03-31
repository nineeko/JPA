package com.neeko.jpql.section06.join;

import jakarta.persistence.*;

@Entity(name="Section06Menu")
@Table(name="tbl_menu")
public class Menu {
    @Id // 반드시 있어야 함
    private int menuCode;
    private String menuName;
    private int menuPrice;

    // Menu가 더 많으니까
    @ManyToOne
    @JoinColumn(name = "categoryCode")
    private Category category;

    private String orderableStatus;

    public Menu() {}

}