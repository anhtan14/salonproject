package com.example.salonmanage.repository;

import com.example.salonmanage.DTO.ChartDTO;
import com.example.salonmanage.Entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Integer> {
    Booking findBookingByID(Integer id);
    @Query(value = "SELECT * FROM Booking b where user_id=? order by b.id desc", nativeQuery = true)
    List<Booking> findByUserPhone(int userid);

    List<Booking> findBookingByDateAndNhanvien(String date, int nhanvien);

    @Query(value = "SELECT * FROM Booking b WHERE b.status = 0 or b.payment = 0 order by b.id desc", nativeQuery = true)
    List<Booking> findAllWithNotRemove();
    @Query(value = "SELECT * FROM Booking b WHERE b.status = 0 and b.payment = 0 and b.user_id=?", nativeQuery = true)
    List<Booking> existbooking(int user_id);

    @Query(value = "SELECT * FROM Booking b WHERE ((b.status != 1 or b.payment = 0) and b.status != 3)  and branch_id = ? order by b.id desc", nativeQuery = true)
    List<Booking> findAllWithNotRemoveWithBranch(int branch);

    @Query(value = "SELECT * FROM Booking b WHERE CONVERT(datetime, b.date, 103) >= ? and CONVERT(datetime, b.date, 103) <= ? ", nativeQuery = true)
    List<Booking> getDataCalendarWithNone(String start, String end);

    @Query(value = "SELECT * FROM Booking b WHERE CONVERT(datetime, b.date, 103) >= ? and CONVERT(datetime, b.date, 103) <= ? and branch_id = ?", nativeQuery = true)
    List<Booking> getDataCalendarWithBranch(String start, String end, int branch);

    @Query("SELECT COUNT(*) FROM Booking b WHERE b.status = 1 and b.payment = 1")
    int countAllWithNotRemove();

    @Query("SELECT SUM(b.totalPrice) FROM Booking b where b.status = 1 and b.payment = 1")
    Long sumAmount();

    @Query(value = "SELECT count(*) FROM Booking b WHERE b.status = 1 and b.payment = 1 and CONVERT(datetime, b.date, 103) <= DATEADD(DAY, -30, GETDATE())", nativeQuery = true)
    int countAllWithNotRemove30DayBefore();

    @Query(value = "SELECT top(10) *  FROM Booking b  where b.status = 1 and b.payment = 1 order by CONVERT(datetime, b.date, 103) desc", nativeQuery = true)
    List<Booking> getTop10();

    @Query(value = "select b.date as date, SUM(b.total_price) as total from  booking b WHERE MONTH(CONVERT(datetime, b.date, 103)) = MONTH(GETDATE()) and b.status = 1 and b.payment = 1 GROUP BY b.date order by CONVERT(datetime, b.date, 103) ASC", nativeQuery = true)
    List<ChartDTO> getDataChart();

    @Query(value = "select b.date as date, SUM(b.total_price) as total from  booking b where CONVERT(datetime, b.date, 103) between ? and ? and b.status = 1 and b.payment = 1 GROUP BY b.date order by CONVERT(datetime, b.date, 103) ASC", nativeQuery = true)
    List<ChartDTO> getDataChartWithDate(String dateStart, String dateEnd);

    @Query(value = "select c.date, SUM(c.total) as total  from " +
            "(select  YEAR(CONVERT(datetime, b.date, 103)) as date, SUM(b.total_price) as total from  booking b where CONVERT(datetime, b.date, 103) between ? and ? and b.status = 1 and b.payment = 1 GROUP BY CONVERT(datetime, b.date, 103)) c " +
            "group by c.date", nativeQuery = true)
    List<ChartDTO> getDataChartWithDateYear(String dateStart, String dateEnd);

    @Query(value = "select c.date, SUM(c.total) as total  from " +
            "(select  CONCAT(MONTH(CONVERT(datetime, b.date, 103)),'/',YEAR(CONVERT(datetime, b.date, 103))) as date, SUM(b.total_price) as total from  booking b where CONVERT(datetime, b.date, 103) between ? and ? and b.status = 1 and b.payment = 1 GROUP BY CONVERT(datetime, b.date, 103)) c " +
            "group by c.date", nativeQuery = true)
    List<ChartDTO> getDataChartWithDateMonth(String dateStart, String dateEnd);

    @Query(value = "select b.date as date, SUM(b.total_price) as total from  booking b where CONVERT(datetime, b.date, 103) between ? and ? and b.status = 1 and b.payment = 1 and branch_id=? GROUP BY b.date order by CONVERT(datetime, b.date, 103) ASC", nativeQuery = true)
    List<ChartDTO> getDataChartWithDateBranch(String dateStart, String dateEnd, int branch);

    @Query(value = "select c.date, SUM(c.total) as total  from " +
            "(select  YEAR(CONVERT(datetime, b.date, 103)) as date, SUM(b.total_price) as total from  booking b where CONVERT(datetime, b.date, 103) between ? and ? and b.status = 1 and b.payment = 1  and branch_id=? GROUP BY CONVERT(datetime, b.date, 103)) c " +
            "group by c.date", nativeQuery = true)
    List<ChartDTO> getDataChartWithDateYearBranch(String dateStart, String dateEnd, int branch);

    @Query(value = "select c.date, SUM(c.total) as total  from " +
            "(select  CONCAT(MONTH(CONVERT(datetime, b.date, 103)),'/',YEAR(CONVERT(datetime, b.date, 103))) as date, SUM(b.total_price) as total from  booking b where CONVERT(datetime, b.date, 103) between ? and ? and b.status = 1 and b.payment = 1  and branch_id=? GROUP BY CONVERT(datetime, b.date, 103)) c " +
            "group by c.date", nativeQuery = true)
    List<ChartDTO> getDataChartWithDateMonthBranch(String dateStart, String dateEnd, int branch);

    @Transactional
    @Modifying()
    @Query(value = "update booking set status = 3 from booking b  where (b.status = 0 or b.status =2) and CONVERT(datetime, b.date, 103)< CAST(GETDATE() AS DATE)", nativeQuery = true)
    void checkStatus();

}
