package mariana.thePotteryPlace.controller;

import mariana.thePotteryPlace.dto.Response.ResponseUserDTO;
import mariana.thePotteryPlace.dto.UserDTO;
import mariana.thePotteryPlace.model.User;
import mariana.thePotteryPlace.service.ICrudService;
import mariana.thePotteryPlace.service.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController extends CrudController<User, UserDTO, ResponseUserDTO, Long> {

    private final IUserService userService;
    private final ModelMapper modelMapper;

    public UserController(IUserService userService, ModelMapper modelMapper) {
        super(User.class, UserDTO.class, ResponseUserDTO.class);
        this.userService = userService;
        this.modelMapper = new ModelMapper();
    }

    @Override
    protected ICrudService<User, Long> getService() {
        return this.userService;
    }

    @Override
    protected ModelMapper getModelMapper() {
        return this.modelMapper;
    }
}