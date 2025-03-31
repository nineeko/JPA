package com.neeko.jpql.section03.projection;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_menu")
public class EmbeddedMenu {

    @Id
    private int menuCode;
    @Embedded
    private MenuInfo menuInfo;
    private int categoryCode;
    private String orderableStatus;

}
