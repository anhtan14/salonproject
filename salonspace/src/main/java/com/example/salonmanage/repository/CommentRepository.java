package com.example.salonmanage.repository;

import com.example.salonmanage.Entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query(value = "SELECT * FROM Comment  WHERE service_id = ? order by date desc", nativeQuery = true)
    List<Comment> getCommentBy(int service_id);

    @Query(value = "SELECT * FROM Comment  WHERE parentid = ? ", nativeQuery = true)
    List<Comment> findByParentID(int parentID);
}