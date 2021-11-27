package org.fate7.redditproject.controller;

import lombok.RequiredArgsConstructor;
import org.fate7.redditproject.dto.SubredditDto;
import org.fate7.redditproject.model.Subreddit;
import org.fate7.redditproject.service.SubredditService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;

@RestController
@RequestMapping("/api/subreddit")
@RequiredArgsConstructor
public class SubredditController {

    private final SubredditService subredditService;

    @PostMapping("")
    public ResponseEntity<SubredditDto> saveSubreddit(@RequestBody SubredditDto subredditDto){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(subredditService.saveSubreddit(subredditDto));
    }

    @GetMapping("")
    public ResponseEntity<List<SubredditDto>> getAllSubreddit(){
        return ResponseEntity.ok().body(subredditService.getAll());
    }
}
