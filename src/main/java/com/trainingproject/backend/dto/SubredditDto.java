package com.trainingproject.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubredditDto {
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getNumberOfPosts() {
		return numberOfPosts;
	}
	public void setNumberOfPosts(Integer numberOfPosts) {
		this.numberOfPosts = numberOfPosts;
	}
	private Long id;
    private String name;
    private String description;
    private Integer numberOfPosts;
}
