package org.fate7.redditproject.service;

import lombok.RequiredArgsConstructor;
import org.fate7.redditproject.dto.SubredditDto;
import org.fate7.redditproject.exceptions.SpringRedditException;
import org.fate7.redditproject.mapper.SubredditMapper;
import org.fate7.redditproject.model.Subreddit;
import org.fate7.redditproject.repository.SubredditRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SubredditService {

    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;


    @Transactional
    public SubredditDto saveSubreddit(SubredditDto subredditDto) {

        Subreddit subreddit = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
        subredditDto.setId(subreddit.getId());
        return subredditDto;
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getAll(){

        return subredditRepository.findAll().stream()
                .map(subredditMapper::mapSubredditToDto)
                .collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    public SubredditDto get(Long id) {
        Optional<Subreddit> subredditOptional = subredditRepository.findById(id);
        Subreddit subreddit = subredditOptional.orElseThrow(() -> new SpringRedditException("Subreddit n'existe pas, id = " + id));
        return subredditMapper.mapSubredditToDto(subreddit);
    }
}
