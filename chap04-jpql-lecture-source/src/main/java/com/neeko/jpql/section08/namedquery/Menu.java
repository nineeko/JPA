package com.neeko.jpql.section08.namedquery;

import jakarta.persistence.*;

@Entity(name="Section08Menu")
@Table(name="tbl_menu")
@NamedQueries({
        @NamedQuery(name = "Section08Menu.selectMenuList",
                query = "SELECT m FROM Section08Menu m")
})
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