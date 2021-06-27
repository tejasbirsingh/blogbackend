package com.trainingproject.backend.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
	private Long id;
	private Long postId;
	private Instant createdDate;
	private String text;
	private String userName;
	private Integer voteCount;
	private boolean upVote;
	private boolean downVote;
}
