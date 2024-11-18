package mariana.thePotteryPlace.service.impl;

import mariana.thePotteryPlace.model.Product;
import mariana.thePotteryPlace.repository.ProductRepository;
import mariana.thePotteryPlace.service.IProductService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl extends ListServiceImpl<Product, Long> implements IProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    protected JpaRepository<Product, Long> getRepository() {
        return productRepository;
    }

    @Override
    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }
}
