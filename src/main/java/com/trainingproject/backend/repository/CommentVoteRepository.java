package com.trainingproject.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trainingproject.backend.model.Comment;
import com.trainingproject.backend.model.CommentVote;
import com.trainingproject.backend.model.User;

@Repository
public interface CommentVoteRepository extends JpaRepository<CommentVote, Long> {
	Optional<CommentVote> findTopByCommentAndUserOrderByVoteIdDesc(Comment comment, User currentUser);

}
