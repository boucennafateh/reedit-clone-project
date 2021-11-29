package org.fate7.redditproject.controller;

import lombok.RequiredArgsConstructor;
import org.fate7.redditproject.dto.PostRequest;
import org.fate7.redditproject.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity createPost(@RequestBody PostRequest postRequest){
        postService.save(postRequest);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<PostRequest> getPost(@PathVariable Long id){
        return ResponseEntity.ok().body(postService.get(id));
    }

    @GetMapping
    public ResponseEntity<List<PostRequest>> getAllPosts(){
        return ResponseEntity.ok().body(postService.getAll());
    }

    @GetMapping("/by-subreddit/{subredditName}")
    public List<PostRequest> getAllBySubreddit(@PathVariable String subredditName){
        return postService.getAllBySubreddit(subredditName);
    }

    @GetMapping("/by-user/{userName}")
    public List<PostRequest> getAllByUserName(@PathVariable String userName){
        return postService.getAllByUserName(userName);
    }


}
