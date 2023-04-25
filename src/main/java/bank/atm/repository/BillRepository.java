package bank.atm.repository;

import bank.atm.model.Bill;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BillRepository extends JpaRepository<Bill, Long> {
    List<Bill> findByCount(long count);

    @Modifying
    @Query(value = "delete from bills b where b.id in (select b2.id from bills b2 where b2.count "
            + "= :billCount order by b2.id asc limit :limit)", nativeQuery = true)
    void deleteTopByOrderByIdAsc(@Param("limit") int limit, @Param("billCount") int billCount);
}
