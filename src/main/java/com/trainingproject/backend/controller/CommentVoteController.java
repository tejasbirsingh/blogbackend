package com.trainingproject.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trainingproject.backend.dto.CommentVoteDto;
import com.trainingproject.backend.service.CommentVoteService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/comment-votes/")
@AllArgsConstructor
public class CommentVoteController {

	private CommentVoteService commentVoteService;

	@PostMapping
	public ResponseEntity<Void> vote(@RequestBody CommentVoteDto commentVoteDto) {

		commentVoteService.vote(commentVoteDto);

		return new ResponseEntity<>(HttpStatus.OK);
	}
}