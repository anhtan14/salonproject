package com.example.salonmanage.service;

import com.example.salonmanage.Entities.Comment;
import com.example.salonmanage.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class CommentServiceImpl implements CommentService{

    @Autowired private CommentRepository commentRepository;

    @Override
    public void delete(int id){
        List<Comment> comments = commentRepository.findByParentID(id);
        for (int i = 0; i < comments.size(); i++) {
            delete(comments.get(i).getId());
            commentRepository.delete(comments.get(i));
        }
    }
}