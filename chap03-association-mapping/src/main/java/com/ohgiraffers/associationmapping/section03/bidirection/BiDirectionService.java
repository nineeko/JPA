package com.ohgiraffers.associationmapping.section03.bidirection;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BiDirectionService {

    private BiDirectionRepository biDirectionRepository;

    public BiDirectionService(BiDirectionRepository biDirectionRepository) {
        this.biDirectionRepository = biDirectionRepository;
    }

    public Menu findMenu(int menuCode) {
        return biDirectionRepository.findMenu(menuCode);
    }

    @Transactional
    public Category findCategory(int categoryCode) {
        Category foundCategory
                = biDirectionRepository.findCategory(categoryCode);
        System.out.println("[ Category menuList ] "
                + foundCategory.getMenuList());
        /* 양방향 참조를 구현하면 양방향 그래프 탐색이 가능하다.
        * Category -> Menu -> Category -> Menu ...
        * 유의할 점은 StackOverflow가 발생하지 않도록 코드를 작성해야 한다.
        * ex) toString()에서 서로 객체를 참조하면 문제 발생
        * */
        List<Menu> menuList = foundCategory.getMenuList();
        for(Menu menu : menuList){
            System.out.println(menu.getCategory());
        }

        return foundCategory;
    }

    @Transactional
    public void registMenu(Menu menu) {
        biDirectionRepository.saveMenu(menu);
    }

    @Transactional
    public void registCategory(Category category) {
        biDirectionRepository.saveCategory(category);
    }
}