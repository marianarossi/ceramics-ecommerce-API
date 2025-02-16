package mariana.thePotteryPlace.controller;

import jakarta.validation.Valid;
import mariana.thePotteryPlace.dto.response.ResponseUserDTO;
import mariana.thePotteryPlace.dto.update.UserUpdateDTO;
import mariana.thePotteryPlace.model.User;
import mariana.thePotteryPlace.service.ICrudService;
import mariana.thePotteryPlace.service.IUserService;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("users")
public class UserController extends CrudController<User, UserUpdateDTO, ResponseUserDTO, Long> {

    private final IUserService userService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserController(IUserService userService, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        super(User.class, UserUpdateDTO.class, ResponseUserDTO.class);
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public ResponseEntity<List<ResponseUserDTO>> findAll() {
        List<User> userbyauth = userService.findUserByAuth();
        return ResponseEntity.ok(userbyauth.stream()
                .map(user -> modelMapper.map(user, ResponseUserDTO.class))
                .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<ResponseUserDTO> update(@PathVariable Long id, @RequestBody @Valid UserUpdateDTO dto) {
        User existingUser = getService().findOne(id);

        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

        modelMapper.map(dto, existingUser);

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            String encryptedPassword = passwordEncoder.encode(dto.getPassword());
            existingUser.setPassword(encryptedPassword);
        }

        User updatedUser = getService().save(existingUser);

        ResponseUserDTO responseDTO = modelMapper.map(updatedUser, ResponseUserDTO.class);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
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