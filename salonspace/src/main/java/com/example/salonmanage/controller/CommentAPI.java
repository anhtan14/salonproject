package com.example.salonmanage.controller;


import com.example.salonmanage.DTO.CommentReponse;
import com.example.salonmanage.Entities.Comment;
import com.example.salonmanage.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/comment")
@CrossOrigin(origins = "*")
public class CommentAPI {
    @Autowired  CommentRepository commentRepository;

    @GetMapping("/{id}")
    public  List<CommentReponse> list(@PathVariable Integer id) {
        List<CommentReponse> commentReponses = new ArrayList<>();
        List<Comment> commentList= commentRepository.getCommentBy(id);
        for (int i = 0; i < commentList.size(); i++) {
            commentReponses.add(new CommentReponse(commentList.get(i).getId(),commentList.get(i).getText(),commentList.get(i).getDate(),commentList.get(i).getParentID(),
                    commentList.get(i).getUser().getId(), commentList.get(i).getUser().getImg(), commentList.get(i).getUser().getName()));
        }
        return commentReponses;
    }
}