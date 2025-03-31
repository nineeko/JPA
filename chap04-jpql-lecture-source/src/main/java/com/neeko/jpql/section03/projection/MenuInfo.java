package com.neeko.jpql.section03.projection;

import jakarta.persistence.Embeddable;

@Embeddable
public class MenuInfo {
    private String menuName;
    private int menuPrice;

}
