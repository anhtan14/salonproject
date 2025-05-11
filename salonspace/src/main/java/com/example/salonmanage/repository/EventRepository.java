package com.example.salonmanage.repository;

import com.example.salonmanage.Entities.event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepository extends JpaRepository<event,Integer> {
    @Query(value = "select top(1) * from event where status = 0",nativeQuery = true)
    event showevent();
   List<event> findByDate(String date);

    @Query(value = "select  * from event where status != 3",nativeQuery = true)
    List<event> getList();
}
