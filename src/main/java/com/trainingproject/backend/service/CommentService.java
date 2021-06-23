package com.trainingproject.backend.service;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.trainingproject.backend.dto.CommentsDto;
import com.trainingproject.backend.exceptions.PostNotFoundException;
import com.trainingproject.backend.mapper.CommentMapper;
import com.trainingproject.backend.model.Comment;
import com.trainingproject.backend.model.NotificationEmail;
import com.trainingproject.backend.model.Post;
import com.trainingproject.backend.model.User;
import com.trainingproject.backend.repository.CommentRepository;
import com.trainingproject.backend.repository.PostRepository;
import com.trainingproject.backend.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommentService {
	private static final String POST_URL = "";
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final AuthService authService;
	private final CommentMapper commentMapper;
	private final CommentRepository commentRepository;
	private final MailContentBuilder mailContentBuilder;
	private final MailService mailService;

	public void save(CommentsDto commentsDto) {
		Post post = postRepository.findById(commentsDto.getPostId())
				.orElseThrow(() -> new PostNotFoundException(commentsDto.getPostId().toString()));
		Comment comment = commentMapper.map(commentsDto, post, authService.getCurrentUser());
		commentRepository.save(comment);

		String message = mailContentBuilder
				.build(authService.getCurrentUser() + " posted a comment on your post." + POST_URL);
		sendCommentNotification(message, post.getUser());
	}

	private void sendCommentNotification(String message, User user) {
		mailService.sendMail(
				new NotificationEmail(user.getUsername() + " Commented on your post", user.getEmail(), message));
	}

	public List<CommentsDto> getAllCommentsForPost(Long postId) {
		Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
		return commentRepository.findByPost(post).stream().map(commentMapper::mapToDto).collect(toList());
	}

	public List<CommentsDto> getAllCommentsForUser(String userName) {
		User user = userRepository.findByUsername(userName).orElseThrow(() -> new UsernameNotFoundException(userName));
		return commentRepository.findAllByUser(user).stream().map(commentMapper::mapToDto).collect(toList());
	}
}