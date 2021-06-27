package com.trainingproject.backend.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trainingproject.backend.dto.CommentRequest;
import com.trainingproject.backend.dto.CommentResponse;
import com.trainingproject.backend.service.CommentService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/comments/")
@AllArgsConstructor
public class CommentsController {
	private CommentService commentService;

	@PostMapping
	public ResponseEntity<Void> createComment(@RequestBody CommentRequest commentsDto) {
		commentService.save(commentsDto);
		return new ResponseEntity<>(CREATED);
	}

	@GetMapping("/by-post/{postId}")
	public ResponseEntity<List<CommentResponse>> getAllCommentsForPost(@PathVariable Long postId) {
		return ResponseEntity.status(OK).body(commentService.getAllCommentsForPost(postId));
	}

	@GetMapping("/by-user/{userName}")
	public ResponseEntity<List<CommentResponse>> getAllCommentsForUser(@PathVariable String userName) {
		return ResponseEntity.status(OK).body(commentService.getAllCommentsForUser(userName));
	}

	@GetMapping("/{commentId}")
	public ResponseEntity<CommentResponse> getComment(@PathVariable Long commentId) {
		return ResponseEntity.status(OK).body(commentService.getComment(commentId));
	}

}
