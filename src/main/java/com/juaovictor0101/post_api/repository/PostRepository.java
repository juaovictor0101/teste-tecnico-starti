package com.juaovictor0101.post_api.repository;

import com.juaovictor0101.post_api.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUserIdAndArchivedFalse(Long userId);
}
