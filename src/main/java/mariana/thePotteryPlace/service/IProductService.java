package mariana.thePotteryPlace.service;

import mariana.thePotteryPlace.model.Product;

import java.util.List;

public interface IProductService extends IListService<Product, Long>{
    List<Product> getProductsByCategory(Long categoryId);
}
