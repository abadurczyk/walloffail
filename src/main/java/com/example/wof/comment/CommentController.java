package com.example.wof.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("comment/")
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public List<CommentDto> getComment(Integer failId){
        return commentService.getComment(failId);
    }

    @PutMapping
    public void commentFail(CommentDto comment){
        commentService.saveComment(comment);
    }

    @DeleteMapping
    public void deleteComment(Integer commentId){
        commentService.deleteComment(commentId);
    }
}
