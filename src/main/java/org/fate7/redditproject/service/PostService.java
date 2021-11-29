package org.fate7.redditproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fate7.redditproject.dto.PostRequest;
import org.fate7.redditproject.exceptions.SpringRedditException;
import org.fate7.redditproject.mapper.PostMapper;
import org.fate7.redditproject.model.Post;
import org.fate7.redditproject.model.Subreddit;
import org.fate7.redditproject.model.User;
import org.fate7.redditproject.repository.PostRepository;
import org.fate7.redditproject.repository.SubredditRepository;
import org.fate7.redditproject.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;
    private final AuthService authService;

    @Transactional
    public void save(PostRequest postRequest) {
        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
                .orElseThrow(() -> new SpringRedditException("Subreddit name does not exist"));
        User user = authService.getCurrentUser();
        postRepository.save(postMapper.mapToPost(postRequest, subreddit, user));

    }

    @Transactional(readOnly = true)
    public PostRequest get(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new SpringRedditException("Post id does not exist, " + id));
        return postMapper.mapToDto(post);


    }

    @Transactional(readOnly = true)
    public List<PostRequest> getAll() {
        return postRepository.findAll().stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostRequest> getAllBySubreddit(String name) {
        Subreddit subreddit = subredditRepository.findByName(name)
                .orElseThrow(() -> new SpringRedditException("subreddit name does not exist, " + name));
        return postRepository.findBySubreddit(subreddit).stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostRequest> getAllByUserName(String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new SpringRedditException("username does not exist, " + userName));
        return postRepository.findByUser(user).stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());


    }
}
