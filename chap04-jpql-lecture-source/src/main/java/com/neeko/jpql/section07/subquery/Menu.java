package com.neeko.jpql.section07.subquery;

import jakarta.persistence.*;

@Entity(name="Section07Menu")
@Table(name="tbl_menu")
public class Menu {
    @Id // 반드시 있어야 함
    private int menuCode;
    private String menuName;
    private int menuPrice;
    private int categoryCode;

    private String orderableStatus;

    public Menu() {}

    @Override
    public String toString() {
        return "Menu{" +
                "menuCode=" + menuCode +
                ", menuName='" + menuName + '\'' +
                ", menuPrice=" + menuPrice +
                ", categoryCode=" + categoryCode +
                ", orderableStatus='" + orderableStatus + '\'' +
                '}';
    }
}