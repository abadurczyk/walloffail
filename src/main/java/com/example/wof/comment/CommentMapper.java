package com.example.wof.comment;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    CommentDto commentEntityToComment(CommentEntity comment);
    CommentEntity commentDtoToCommentEntity(CommentDto commentDto);
}
