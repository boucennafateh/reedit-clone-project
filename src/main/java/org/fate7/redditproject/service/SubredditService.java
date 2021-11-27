package org.fate7.redditproject.service;

import lombok.RequiredArgsConstructor;
import org.fate7.redditproject.dto.SubredditDto;
import org.fate7.redditproject.model.Subreddit;
import org.fate7.redditproject.repository.SubredditRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SubredditService {

    private final SubredditRepository subredditRepository;


    @Transactional
    public SubredditDto saveSubreddit(SubredditDto subredditDto) {

        Subreddit subreddit = subredditRepository.save(dtoTOSubreddit(subredditDto));
        subredditDto.setId(subreddit.getId());
        return subredditDto;
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getAll(){

        return subredditRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

    }


    private Subreddit dtoTOSubreddit(SubredditDto subredditDto){
        return Subreddit.builder()
                .name(subredditDto.getName())
                .description(subredditDto.getDescription())
                .build();
    }

    private SubredditDto mapToDto(Subreddit subreddit){
        return SubredditDto.builder()
                .id(subreddit.getId())
                .name(subreddit.getName())
                .description(subreddit.getDescription())
                .nbPosts(subreddit.getPosts().size())
                .build();

    }
}
