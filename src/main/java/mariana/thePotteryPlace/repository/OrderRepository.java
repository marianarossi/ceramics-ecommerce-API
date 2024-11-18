package mariana.thePotteryPlace.repository;

import mariana.thePotteryPlace.model.Order;
import mariana.thePotteryPlace.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser_Id(Long userid);
    List<Order> findByUser(User user);
    Page<Order> findByUser(User user, Pageable pageable);
}
