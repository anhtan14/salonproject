package com.example.salonmanage.repository;


import com.example.salonmanage.Entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    @Query(value = "select * from notification n where n.user_id = ?  order by n.date desc ", nativeQuery = true)
    List<Notification> listAll(int user);

    @Query(value = "select COUNT(*) from notification n where n.user_id = ? and n.status = 0 ", nativeQuery = true)
    int count(int user);

    @Transactional
    @Modifying()
    @Query(value = "update notification set status = 1 from notification  where user_id = ? and status =0", nativeQuery = true)
    void read(int user);
}
