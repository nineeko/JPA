package com.neeko.springdatajpa.menu.service;

import com.neeko.springdatajpa.menu.dto.MenuDTO;
import com.neeko.springdatajpa.menu.entity.Menu;
import com.neeko.springdatajpa.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {
    /* DTO와 Entity의 분리
    * DTO(Data Transfer Object)는 프레젠테이션 계층과의 데이터 교환을 위한 객체로,
    * 도메인 로직을 담고 있는 Entity와 분리하여 사용함으로써 보안, 성능, 유지보수 측면의 이점을 얻을 수 있다.
    * 1. 보안: 민감 데이터/과도 데이터 노출 방치, 과도한 바인딩 공격 방지
    * 2. 성능: 데이터 전송 최적화, 지연 로딩 문제 완화, 쿼리 최적화
    * 3. 유지보수: 명확한 계층 분리(비즈니스 로직 수정이나 도메인 모델 변경이 클라이언트와의 API 계약에 영향 X)
    * 도메인 로직의 캡슐화(Entity 관련 비지니스 로직은 외부에 노출 X, 클라이언트는 DTO만 이용) */

    /* DTO - Entity의 변환 방법
    * 1. 수동 매핑 메서드 사용
    * 2. Spring BeanUtils.copyProperties()
    * 3. ModelMapper 라이브러리
    * 4. MapStruct 라이브러리
    * 5. Spring Data JPA Projection */
    /* 1. findById */
    private final MenuRepository menuRepository;
    private final ModelMapper modelMapper;
    public MenuDTO findMenuByMenuCode(int menuCode) {
        Menu menu = menuRepository.findById(menuCode)
                .orElseThrow(IllegalArgumentException::new);
        return modelMapper.map(menu, MenuDTO.class); // .map: 데이터 가공
    }

    /* 2. findAll(Sort) */
    public List<MenuDTO> findMenuList() {
        List<Menu> menuList = menuRepository.findAll(Sort.by("menuCode").descending());
        return menuList.stream()
                .map(menu -> modelMapper.map(menu, MenuDTO.class))
                .toList();
    }

    /* 3. findAll(Pageable) */
    public Page<MenuDTO> findMenuList(Pageable pageable) {
        /* page 0부터 시작하는 부분을 1로 보정
        * 정렬 기준은 전달 받지 않고 고정된 기준으로 수행 */
        pageable = PageRequest.of(
                pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
                pageable.getPageSize(),
                Sort.by("menuCode").descending()
        );
        Page<Menu> menuList = menuRepository.findAll(pageable);

        return menuList.map(menu -> modelMapper.map(menu, MenuDTO.class));
    }


    /* 4. 쿼리 메소드 활용*/
    public List<MenuDTO> findByMenuPrice(Integer menuPrice) {
        List<Menu> menuList = menuRepository.findByMenuPriceGreaterThan(menuPrice);
        return menuList.stream()
                .map(menu -> modelMapper.map(menu, MenuDTO.class))
                .toList();
    }
}
