package mariana.thePotteryPlace.controller;

import mariana.thePotteryPlace.dto.ProductDTO;
import mariana.thePotteryPlace.dto.Response.ResponseProductDTO;
import mariana.thePotteryPlace.model.Product;
import mariana.thePotteryPlace.service.IListService;
import mariana.thePotteryPlace.service.IProductService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("products")
public class ProductController extends ListController<Product, ProductDTO, ResponseProductDTO, Long> {

    private final IProductService productService;
    private final ModelMapper modelMapper;

    public ProductController(IProductService productService, ModelMapper modelMapper) {
        super(Product.class, ProductDTO.class, ResponseProductDTO.class);
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @Override
    protected IListService<Product, Long> getService() {
        return this.productService;
    }

    @Override
    protected ModelMapper getModelMapper() {
        return this.modelMapper;
    }
}

