package com.trainingproject.backend.service;

import static com.trainingproject.backend.model.VoteType.UPVOTE;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trainingproject.backend.dto.CommentVoteDto;
import com.trainingproject.backend.exceptions.PostNotFoundException;
import com.trainingproject.backend.exceptions.SpringRedditException;
import com.trainingproject.backend.model.Comment;
import com.trainingproject.backend.model.CommentVote;
import com.trainingproject.backend.repository.CommentRepository;
import com.trainingproject.backend.repository.CommentVoteRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommentVoteService {

	private final CommentVoteRepository commentVoteRepository;
	private final CommentRepository commentRepository;
	private final AuthService authService;

	@Transactional
	public void vote(CommentVoteDto commentvoteDto) {
//		System.err.println(commentvoteDto.getCommentId());
		Comment comment = commentRepository.findById(commentvoteDto.getCommentId()).orElseThrow(
				() -> new PostNotFoundException("Comment Not Found with ID - " + commentvoteDto.getCommentId()));

		Optional<CommentVote> voteByCommentAndUser = commentVoteRepository
				.findTopByCommentAndUserOrderByVoteIdDesc(comment, authService.getCurrentUser());

		if (voteByCommentAndUser.isPresent()
				&& voteByCommentAndUser.get().getVoteType().equals(commentvoteDto.getVoteType())) {
			throw new SpringRedditException("You have already" + commentvoteDto.getVoteType() + "'d for this comment");
		}

		if (UPVOTE.equals(commentvoteDto.getVoteType())) {
			comment.setVoteCount(comment.getVoteCount() + 1);
		} else {
			comment.setVoteCount(comment.getVoteCount() - 1);
		}

		commentVoteRepository.save(mapToVote(commentvoteDto, comment));
		commentRepository.save(comment);
	}

	private CommentVote mapToVote(CommentVoteDto commentvoteDto, Comment comment) {
		return CommentVote.builder().voteType(commentvoteDto.getVoteType()).comment(comment)
				.user(authService.getCurrentUser()).build();
	}
}
