package com.neeko.springdatajpa.menu.controller;

import com.neeko.springdatajpa.common.Pagenation;
import com.neeko.springdatajpa.common.PagingButton;
import com.neeko.springdatajpa.menu.dto.CategoryDTO;
import com.neeko.springdatajpa.menu.dto.MenuDTO;
import com.neeko.springdatajpa.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/menu")
@RequiredArgsConstructor
@Slf4j // log 라는 변수명으로 Logger 객체 타입의 필드가 선언되고 생성된다.  Simple Logging Facade for Java
public class MenuController {
    private final MenuService menuService;

    @GetMapping("{menuCode}")
    public String findMenuByCode(@PathVariable int menuCode, Model model) {
        MenuDTO resultMenu = menuService.findMenuByMenuCode(menuCode);
        model.addAttribute("menu", resultMenu);
        return "menu/detail";
    }

    @GetMapping("/list")
    public String findMenuList(Model model, @PageableDefault Pageable pageable) {
        /* 페이징 처리 이전 정렬 처리만 반영된 목록 조회*/
//        List<MenuDTO> menuList = menuService.findMenuList();
//        model.addAttribute("menuList", menuList);

        /* System.out.print 계열의 메소드보다 효율적으로 로그 출력을 할 수 있다.
        * 로그 레벨에 맞춘 메소드를 통해 출력 처리한다.
        * {}를 통해 값이 입력 될 위치를 포매팅한다. */
        log.info("pageable : {}", pageable);
        /* 페이징 처리가 반영된 목록 조회 (Number, size, sort도 존재)*/
        Page<MenuDTO> menuList = menuService.findMenuList(pageable);
        PagingButton paging = Pagenation.getPagingButtonInfo(menuList);
        model.addAttribute("menuList", menuList);
        model.addAttribute("paging", paging);
        /* Number, size, sort도 존재 */
        log.info("getContent : {}", menuList.getContent());
        log.info("getTotalPages : {}", menuList.getTotalPages());
        log.info("getTotalElements : {}", menuList.getTotalElements());
        log.info("getNumberOfElements : {}", menuList.getNumberOfElements());
        log.info("isFirst : {}", menuList.isFirst());
        log.info("isLast : {}", menuList.isLast());


        return "menu/list";
    }

    @GetMapping("/querymethod")
    public void queryMethodPage(){

    }

    @GetMapping("/search")
    public String findByMenuPrice(@RequestParam Integer menuPrice, Model model){
        List<MenuDTO> menuList = menuService.findByMenuPrice(menuPrice);
        model.addAttribute("menuList", menuList);
        return "menu/searchResult";

    }

    @GetMapping("/regist")
    public void registPage(){}

    @GetMapping("/category")
    @ResponseBody // 응답 데이터에 body에 반환 값을 그대로 전달하겠다는 의미 (ViewResolver 사용 x)
    public List<CategoryDTO> findCategoryList(){
        return menuService.findAllCategory();
    }

    @PostMapping("/regist")
    public String registMenu(@ModelAttribute MenuDTO menuDTO){
        menuService.registMenu(menuDTO);
        return "redirect:/menu/list";
    }

    @GetMapping("/modify")
    public void modifyPage(){}

    @PostMapping("/modify")
    public String modifyMenu(@ModelAttribute MenuDTO menuDTO){
        menuService.modifyMenu(menuDTO);
        return "redirect:/menu/"+menuDTO.getMenuCode(); // /menu/7 7번 메뉴 조회
    }

    @GetMapping("/delete")
    public void deletePage(){}

    @PostMapping("/delete")
    public String deleteMenu(@RequestParam int menuCode){
        menuService.deleteMenu(menuCode);
        return "redirect:/menu/list";
    }


}
