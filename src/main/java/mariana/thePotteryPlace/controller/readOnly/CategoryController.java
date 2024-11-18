package mariana.thePotteryPlace.controller.readOnly;

import mariana.thePotteryPlace.dto.request.CategoryDTO;
import mariana.thePotteryPlace.dto.response.ResponseCategoryDTO;
import mariana.thePotteryPlace.model.Category;
import mariana.thePotteryPlace.service.ICategoryService;
import mariana.thePotteryPlace.service.IListService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("categories")
public class CategoryController extends ListController<Category, CategoryDTO, ResponseCategoryDTO, Long> //T, D, ID
{
    private final ICategoryService categoryService;
    private final ModelMapper modelMapper;

    public CategoryController(ICategoryService categoryService,
                              ModelMapper modelMapper) {
        super(Category.class, CategoryDTO.class, ResponseCategoryDTO.class);
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @Override
    protected IListService<Category, Long> getService() {
        return this.categoryService;
    }

    @Override
    protected ModelMapper getModelMapper() {
        return this.modelMapper;
    }
}
