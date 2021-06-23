package com.trainingproject.backend.dto;



import com.trainingproject.backend.model.VoteType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDto {
    public VoteType getVoteType() {
		return voteType;
	}
	public void setVoteType(VoteType voteType) {
		this.voteType = voteType;
	}
	public Long getPostId() {
		return postId;
	}
	public void setPostId(Long postId) {
		this.postId = postId;
	}
	private VoteType voteType;
    private Long postId;
}
