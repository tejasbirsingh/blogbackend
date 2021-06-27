package com.trainingproject.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trainingproject.backend.model.Post;
import com.trainingproject.backend.model.Subreddit;
import com.trainingproject.backend.model.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	List<Post> findAllBySubreddit(Subreddit subreddit);

	List<Post> findByUser(User user);
}
