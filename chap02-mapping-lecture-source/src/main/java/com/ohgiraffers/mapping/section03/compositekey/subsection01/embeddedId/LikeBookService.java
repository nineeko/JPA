package com.ohgiraffers.mapping.section03.compositekey.subsection01.embeddedId;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeBookService {

    private LikeRepository likeRepository;

    public LikeBookService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    @Transactional
    public void generateLikeBook(LikeDTO likeDTO) {

        Like like = new Like(
                new LikeCompositeKey(
                        likeDTO.getLikedMemberNo(),
                        likeDTO.getLikedBookNo()
                )
        );

        likeRepository.save(like);
    }
}

