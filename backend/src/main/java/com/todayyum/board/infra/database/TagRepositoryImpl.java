package com.todayyum.board.infra.database;

import com.todayyum.board.application.repository.TagRepository;
import com.todayyum.board.domain.Tag;
import com.todayyum.board.infra.entity.TagEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class TagRepositoryImpl implements TagRepository {
    private final JpaTagRepository jpaTagRepository;

    @Override
    public Tag findByContent(String content) {
        return jpaTagRepository.findByContent(content)
                .map(Tag::createTag)
                .orElse(null);
    }

    @Override
    public List<Tag> findByBoardId(Long boardId) {
        return jpaTagRepository.findByBoardId(boardId).stream()
                .map(Tag::createTag).collect(Collectors.toList());
    }

    @Override
    public Tag save(String content) {
        TagEntity tagEntity = TagEntity.builder()
                .content(content)
                .build();

        return Tag.createTag(jpaTagRepository.save(tagEntity));
    }
}
