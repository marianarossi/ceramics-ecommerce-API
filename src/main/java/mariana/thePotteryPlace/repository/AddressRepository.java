package mariana.thePotteryPlace.repository;

import mariana.thePotteryPlace.model.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUser_Id(Long userId);
    Page<Address> findByUser_Id(Long userId, Pageable pageable);
}
