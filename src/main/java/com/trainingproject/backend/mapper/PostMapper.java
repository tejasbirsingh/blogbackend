package com.trainingproject.backend.mapper;

import static com.trainingproject.backend.model.VoteType.DOWNVOTE;
import static com.trainingproject.backend.model.VoteType.UPVOTE;

import java.util.Optional;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.trainingproject.backend.dto.PostRequest;
import com.trainingproject.backend.dto.PostResponse;
import com.trainingproject.backend.model.Post;
import com.trainingproject.backend.model.Subreddit;
import com.trainingproject.backend.model.User;
import com.trainingproject.backend.model.Vote;
import com.trainingproject.backend.model.VoteType;
import com.trainingproject.backend.repository.CommentRepository;
import com.trainingproject.backend.repository.VoteRepository;
import com.trainingproject.backend.service.AuthService;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private VoteRepository voteRepository;
	@Autowired
	private AuthService authService;

	@Mappings({ @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())"),
			@Mapping(target = "description", source = "postRequest.description"),
			@Mapping(target = "subreddit", source = "subreddit"), @Mapping(target = "voteCount", constant = "0"),
			@Mapping(target = "user", source = "user"), })

	public abstract Post map(PostRequest postRequest, Subreddit subreddit, User user);

	@Mappings({ @Mapping(target = "id", source = "postId"),
			@Mapping(target = "subredditName", source = "subreddit.name"),
			@Mapping(target = "userName", source = "user.username"),
			@Mapping(target = "commentCount", expression = "java(commentCount(post))"),
			@Mapping(target = "duration", expression = "java(getDuration(post))"),
			@Mapping(target = "upVote", expression = "java(isPostUpVoted(post))"),
			@Mapping(target = "downVote", expression = "java(isPostDownVoted(post))") })

	public abstract PostResponse mapToDto(Post post);

	Integer commentCount(Post post) {
		return commentRepository.findByPost(post).size();
	}

	String getDuration(Post post) {
		return TimeAgo.using(post.getCreatedDate().toEpochMilli());
	}

	boolean isPostUpVoted(Post post) {
		return checkVoteType(post, UPVOTE);
	}

	boolean isPostDownVoted(Post post) {
		return checkVoteType(post, DOWNVOTE);
	}

	private boolean checkVoteType(Post post, VoteType voteType) {
		if (authService.isLoggedIn()) {
			Optional<Vote> voteForPostByUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,
					authService.getCurrentUser());
			return voteForPostByUser.filter(vote -> vote.getVoteType().equals(voteType)).isPresent();
		}
		return false;
	}

}