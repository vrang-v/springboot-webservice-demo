package org.vrang.springboot.demo.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.vrang.springboot.demo.config.auth.LoginUser;
import org.vrang.springboot.demo.config.auth.dto.SessionUser;
import org.vrang.springboot.demo.service.posts.PostsService;
import org.vrang.springboot.demo.web.dto.PostsResponseDto;

import java.util.Objects;

@RequiredArgsConstructor
@Controller
public class IndexController
{
    private final PostsService postsService;
    
    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user)
    {
        model.addAttribute("posts", postsService.findAllDesc( ));
        if (Objects.nonNull(user)) {
            model.addAttribute("userName", user.getName( ));
        }
        return "index";
    }
    
    @GetMapping("/posts/save")
    public String postsSave( )
    {
        return "posts-save";
    }
    
    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model)
    {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        
        return "posts-update";
    }
}
