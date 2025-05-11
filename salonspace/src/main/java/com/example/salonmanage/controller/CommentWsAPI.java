package com.example.salonmanage.controller;

import com.example.salonmanage.DTO.CommentDTO;
import com.example.salonmanage.DTO.CommentReponse;
import com.example.salonmanage.Entities.Comment;
import com.example.salonmanage.repository.CommentRepository;
import com.example.salonmanage.repository.ServiceRepository;
import com.example.salonmanage.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import com.example.salonmanage.repository.userRepository;

@Controller
public class CommentWsAPI {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private userRepository userRepository;
    @Autowired
    private CommentService commentService;

    @MessageMapping("/comment/{channelId}")
    @SendTo("/topic/comment/{channelId}")
    public CommentReponse get(@DestinationVariable String channelId, @Payload CommentDTO message) throws Exception {
        Comment comment = new Comment();
        comment.setParentID(message.getParentID());
        comment.setDate(message.getDate());
        comment.setText(message.getText());
        comment.setService(serviceRepository.findById(message.getService_id()).get());
        comment.setUser(userRepository.findById(message.getUser_id()).get());
        Comment newComment = commentRepository.save(comment);
        CommentReponse commentReponse = new CommentReponse(newComment.getId(), newComment.getText(), newComment.getDate(), newComment.getParentID(),
                newComment.getUser().getId(), newComment.getUser().getImg(), newComment.getUser().getName());
        return commentReponse;
    }

    @MessageMapping("/updateComment/{channelId}")
    @SendTo("/topic/updateComment/{channelId}")
    public CommentReponse update(@DestinationVariable String channelId, @Payload CommentDTO message) throws Exception {
        if (commentRepository.existsById(message.getId())) {
            Comment comment = commentRepository.findById(message.getId()).get();
            if (comment.getUser().getId().equals(message.getUser_id())) {
                comment.setText(message.getText());
                Comment newComment = commentRepository.save(comment);
                CommentReponse commentReponse = new CommentReponse(newComment.getId(), newComment.getText(), newComment.getDate(), newComment.getParentID(),
                        newComment.getUser().getId(), newComment.getUser().getImg(), newComment.getUser().getName());
                return commentReponse;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @MessageMapping("/deleteComment/{channelId}")
    @SendTo("/topic/deleteComment/{channelId}")
    public int delete(@DestinationVariable String channelId, @Payload CommentDTO message) throws Exception {
        if (commentRepository.existsById(message.getId())) {
            Comment comment = commentRepository.findById(message.getId()).get();
            if (comment.getUser().getId().equals(message.getUser_id())) {
                commentService.delete(message.getId());
                commentRepository.delete(comment);
                return message.getId();
            } else if (message.getParentID()==1) {
                commentService.delete(message.getId());
                commentRepository.delete(comment);
                return message.getId();
            } else{
                return 0;
            }
        } else {
            return 0;
        }


    }
}