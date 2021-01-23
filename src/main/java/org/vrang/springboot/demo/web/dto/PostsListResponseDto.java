package org.vrang.springboot.demo.web.dto;

import lombok.Getter;
import org.vrang.springboot.demo.web.domain.posts.Posts;

import java.time.LocalDateTime;

@Getter
public class PostsListResponseDto
{
    private Long          id;
    private String        title;
    private String        author;
    private LocalDateTime modifiedDateTime;
    
    public PostsListResponseDto(Posts entity)
    {
        this.id               = entity.getId( );
        this.title            = entity.getTitle( );
        this.author           = entity.getAuthor( );
        this.modifiedDateTime = entity.getModifiedDateTime( );
    }
}
