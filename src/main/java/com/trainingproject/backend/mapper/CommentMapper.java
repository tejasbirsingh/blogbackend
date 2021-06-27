package com.trainingproject.backend.mapper;

import static com.trainingproject.backend.model.VoteType.DOWNVOTE;
import static com.trainingproject.backend.model.VoteType.UPVOTE;

import java.util.Optional;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import com.trainingproject.backend.dto.CommentRequest;
import com.trainingproject.backend.dto.CommentResponse;
import com.trainingproject.backend.model.Comment;
import com.trainingproject.backend.model.CommentVote;
import com.trainingproject.backend.model.Post;
import com.trainingproject.backend.model.User;
import com.trainingproject.backend.model.VoteType;
import com.trainingproject.backend.repository.CommentRepository;
import com.trainingproject.backend.repository.CommentVoteRepository;
import com.trainingproject.backend.service.AuthService;

@Mapper(componentModel = "spring")
public abstract class CommentMapper {

	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private CommentVoteRepository voteRepository;
	@Autowired
	private AuthService authService;

	@Mappings({ @Mapping(target = "id", ignore = true), @Mapping(target = "text", source = "commentsDto.text"),
			@Mapping(target = "createdDate", expression = "java(java.time.Instant.now())"),
			@Mapping(target = "post", source = "post"), @Mapping(target = "user", source = "user"),
			@Mapping(target = "voteCount", constant = "0") })

	public abstract Comment map(CommentRequest commentsDto, Post post, User user);

	@Mappings({ @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())"),
			@Mapping(target = "userName", expression = "java(comment.getUser().getUsername())"),
			@Mapping(target = "upVote", expression = "java(isCommentUpVoted(comment))"),
			@Mapping(target = "downVote", expression = "java(isCommentDownVoted(comment))") })
	public abstract CommentResponse mapToDto(Comment comment);

	boolean isCommentUpVoted(Comment post) {
		return checkVoteType(post, UPVOTE);
	}

	boolean isCommentDownVoted(Comment post) {
		return checkVoteType(post, DOWNVOTE);
	}

	private boolean checkVoteType(Comment comment, VoteType voteType) {
		if (authService.isLoggedIn()) {
			Optional<CommentVote> voteForPostByUser = voteRepository.findTopByCommentAndUserOrderByVoteIdDesc(comment,
					authService.getCurrentUser());
			return voteForPostByUser.filter(vote -> vote.getVoteType().equals(voteType)).isPresent();
		}
		return false;
	}
}