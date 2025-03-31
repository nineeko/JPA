package com.neeko.jpql.section03.projection;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectionService {

    private final ProjectionRepository projectionRepository;
    public ProjectionService(ProjectionRepository projectionRepository) {
        this.projectionRepository = projectionRepository;

    }
    @Transactional
    public List<Menu> singleEntityProjection() {
        List<Menu> menus = projectionRepository.singleEntityProjection();
        menus.get(0).setMenuName("세상에서 제일 맛있는 유니콘 고기");
        return menus;
    }

}
