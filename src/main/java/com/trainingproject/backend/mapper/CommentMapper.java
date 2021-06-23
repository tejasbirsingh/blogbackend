package com.trainingproject.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.trainingproject.backend.dto.CommentsDto;
import com.trainingproject.backend.model.Comment;
import com.trainingproject.backend.model.Post;
import com.trainingproject.backend.model.User;

@Mapper(componentModel = "spring")
public interface CommentMapper {
	@Mappings({ @Mapping(target = "id", ignore = true), @Mapping(target = "text", source = "commentsDto.text"),
			@Mapping(target = "createdDate", expression = "java(java.time.Instant.now())"),
			@Mapping(target = "post", source = "post"), @Mapping(target = "user", source = "user"), })

	Comment map(CommentsDto commentsDto, Post post, User user);

	@Mappings({ @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())"),
			@Mapping(target = "userName", expression = "java(comment.getUser().getUsername())") })
	CommentsDto mapToDto(Comment comment);
}