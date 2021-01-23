package org.vrang.springboot.demo.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.vrang.springboot.demo.service.posts.PostsService;
import org.vrang.springboot.demo.web.dto.PostsResponseDto;
import org.vrang.springboot.demo.web.dto.PostsSaveRequestDto;
import org.vrang.springboot.demo.web.dto.PostsUpdateRequestDto;

@RequiredArgsConstructor
@RestController
public class PostsApiController
{
    private final PostsService postsService;
    
    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto)
    {
        return postsService.save(requestDto);
    }
    
    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto)
    {
        return postsService.update(id, requestDto);
    }
    
    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id)
    {
        postsService.delete(id);
        return id;
    }
    
    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id)
    {
        return postsService.findById(id);
    }
}
