package com.juaovictor0101.post_api.repository;

import com.juaovictor0101.post_api.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPostId(Long postId);

    List<Comment> findByUserIdAndPostArchivedFalse(Long userId);

}
