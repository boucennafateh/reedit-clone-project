package org.fate7.redditproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {

    private String postId;
    private String postName;
    private String subredditName;
    private String UserName;
    private String description;
    private String url;
}
