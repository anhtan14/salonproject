package com.example.salonmanage.repository;

import com.example.salonmanage.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface userRepository extends JpaRepository<User,Integer> {

    Optional<User> findByPhone(String phone);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    User findByEmail(String email);
    @Query(value = "select * from users where branch_id=?", nativeQuery = true)
    List<User> findByBranch(Integer branch);

    User getByPhone(String phone);
    @Query(value="SELECT COUNT(*) FROM users u inner join users_roles r on u.id = r.user_id  inner join roles ro on ro.id = r.role_id where ro.name =? and u.status != 3", nativeQuery = true)
    int countAllWithNotRemove(String role);
    List<User> findByRolesName(String roleName);
    @Query(value = "SELECT  top(4) u.img, u.name, r.name as role, COUNT(b.ID) as booking_count " +
            "FROM users u " +
            "JOIN booking b ON u.id = b.nhanvien " +
            "JOIN users_roles ur ON u.id = ur.user_id " +
            "JOIN roles r ON ur.role_id = r.id " +
            "WHERE r.name = 'ROLE_STAFF' " +
            "GROUP BY u.id, u.img, u.name, r.name " +
            "ORDER BY booking_count DESC ", nativeQuery = true)
    List<Object[]> findTop4UsersWithMostBookings();
}
