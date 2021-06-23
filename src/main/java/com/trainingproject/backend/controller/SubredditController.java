package com.trainingproject.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trainingproject.backend.dto.SubredditDto;
import com.trainingproject.backend.service.SubredditService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor

public class SubredditController {

	private SubredditService subredditService;

	@PostMapping
	public ResponseEntity<SubredditDto> createSubreddit(@RequestBody SubredditDto subredditDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(subredditService.save(subredditDto));
	}

	@GetMapping
	public ResponseEntity<List<SubredditDto>> getAllSubreddits() {
		return ResponseEntity.status(HttpStatus.OK).body(subredditService.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<SubredditDto> getSubreddit(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(subredditService.getSubreddit(id));
	}
}
