package mariana.thePotteryPlace.controller;

import mariana.thePotteryPlace.dto.ProductDTO;
import mariana.thePotteryPlace.dto.Response.ResponseProductDTO;
import mariana.thePotteryPlace.model.Product;
import mariana.thePotteryPlace.service.IListService;
import mariana.thePotteryPlace.service.IProductService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/category/{id}")
    public ResponseEntity<List<ResponseProductDTO>> getProductByCategory(@PathVariable Long id) {
        List<Product> products = productService.getProductsByCategory(id);
        List<ResponseProductDTO> responseDTOs = products.stream()
                .map(product -> modelMapper.map(product, ResponseProductDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
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

