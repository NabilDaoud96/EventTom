package API.EventTom.repositories;

import API.EventTom.models.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    @Query("SELECT v FROM Voucher v WHERE v.code = :code")
    Optional<Voucher> findByCode(@Param("code") String code);

    @Query("SELECT v FROM Voucher v WHERE v.customer.user.id = :userId")
    List<Voucher> findByUserId(@Param("userId") Long userId);

    boolean existsByCode(String code);

}
