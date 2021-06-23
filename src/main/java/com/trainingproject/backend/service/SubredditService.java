package com.trainingproject.backend.service;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trainingproject.backend.dto.SubredditDto;
import com.trainingproject.backend.exceptions.SpringRedditException;
import com.trainingproject.backend.mapper.SubredditMapper;
import com.trainingproject.backend.model.Subreddit;
import com.trainingproject.backend.repository.SubredditRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor

public class SubredditService {

	private final SubredditRepository subredditRepository;
	private final SubredditMapper subredditMapper;

	@Transactional
	public SubredditDto save(SubredditDto subredditDto) {
		Subreddit save = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
		subredditDto.setId(save.getId());
		return subredditDto;
	}

	@Transactional(readOnly = true)
	public List<SubredditDto> getAll() {
		return subredditRepository.findAll().stream().map(subredditMapper::mapSubredditToDto).collect(toList());
	}

	public SubredditDto getSubreddit(Long id) {
		Subreddit subreddit = subredditRepository.findById(id)
				.orElseThrow(() -> new SpringRedditException("No subreddit found with ID - " + id));
		return subredditMapper.mapSubredditToDto(subreddit);
	}
}
