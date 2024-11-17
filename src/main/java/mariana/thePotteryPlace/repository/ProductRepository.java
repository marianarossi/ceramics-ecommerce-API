package mariana.thePotteryPlace.repository;

import mariana.thePotteryPlace.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
//    List<Product> findByCategory(Category category);
}
