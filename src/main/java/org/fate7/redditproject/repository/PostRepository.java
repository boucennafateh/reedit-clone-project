package org.fate7.redditproject.repository;

import org.fate7.redditproject.model.Post;
import org.fate7.redditproject.model.Subreddit;
import org.fate7.redditproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUser(User user);
    List<Post> findBySubreddit(Subreddit subreddit);
}
