package com.zh.community.mapper;

import com.zh.community.model.Comment;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}