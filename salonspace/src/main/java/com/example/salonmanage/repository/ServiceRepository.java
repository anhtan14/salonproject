package com.example.salonmanage.repository;

import com.example.salonmanage.Entities.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Integer> {
    @Query("SELECT b FROM Service b WHERE b.status = 1")
    List<Service> findAllWithNotRemove();

    @Query("SELECT b FROM Service b WHERE b.status != 3")
    List<Service> findAllWithNotRemoveAdmin();

    @Query("SELECT b FROM Service b WHERE b.status != 3 AND b.name LIKE %:serviceName%")
    List<Service> findAllWithNotRemoveByName(@Param("serviceName") String serviceName);

    Service findServiceById(Integer id);

    @Query("SELECT COUNT(*) FROM Service b WHERE b.status != 3")
    int countAllWithNotRemove();


    @Query(value = "SELECT TOP(12) s.img, s.name, s.price, b.booking_count" +
            " FROM Service s " +
            " INNER JOIN (" +
            "    SELECT bd.service_id, COUNT(bd.service_id) AS booking_count" +
            "    FROM booking_detail bd" +
            "    GROUP BY bd.service_id" +
            " ) b ON s.id = b.service_id where s.status=1" +
            " ORDER BY b.booking_count DESC",
            nativeQuery = true)
    List<Object[]> findMostBookedServices();

}