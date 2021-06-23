package com.trainingproject.backend.service;

import static com.trainingproject.backend.model.VoteType.UPVOTE;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trainingproject.backend.dto.VoteDto;
import com.trainingproject.backend.exceptions.PostNotFoundException;
import com.trainingproject.backend.exceptions.SpringRedditException;
import com.trainingproject.backend.model.Post;
import com.trainingproject.backend.model.Vote;
import com.trainingproject.backend.repository.PostRepository;
import com.trainingproject.backend.repository.VoteRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VoteService {

	private final VoteRepository voteRepository;
	private final PostRepository postRepository;
	private final AuthService authService;

	@Transactional
	public void vote(VoteDto voteDto) {
		Post post = postRepository.findById(voteDto.getPostId())
				.orElseThrow(() -> new PostNotFoundException("Post Not Found with ID - " + voteDto.getPostId()));
		Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,
				authService.getCurrentUser());
		if (voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())) {
			throw new SpringRedditException("You have already" + voteDto.getVoteType() + "'d for this post");
		}
		if (UPVOTE.equals(voteDto.getVoteType())) {
			post.setVoteCount(post.getVoteCount() + 1);
		} else {
			post.setVoteCount(post.getVoteCount() - 1);
		}
		voteRepository.save(mapToVote(voteDto, post));
		postRepository.save(post);
	}

	private Vote mapToVote(VoteDto voteDto, Post post) {
		return Vote.builder().voteType(voteDto.getVoteType()).post(post).user(authService.getCurrentUser()).build();
	}
}
