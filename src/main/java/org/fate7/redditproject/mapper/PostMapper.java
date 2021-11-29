package org.fate7.redditproject.mapper;

import org.fate7.redditproject.dto.PostRequest;
import org.fate7.redditproject.model.Post;
import org.fate7.redditproject.model.Subreddit;
import org.fate7.redditproject.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    Post mapToPost(PostRequest postRequest, Subreddit subreddit, User user);

    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "userName", source = "user.username")
    PostRequest mapToDto(Post post);
}
