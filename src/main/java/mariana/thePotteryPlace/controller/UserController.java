package mariana.thePotteryPlace.controller;

import jakarta.validation.Valid;
import mariana.thePotteryPlace.dto.response.ResponseUserDTO;
import mariana.thePotteryPlace.dto.request.UserDTO;
import mariana.thePotteryPlace.model.User;
import mariana.thePotteryPlace.service.ICrudService;
import mariana.thePotteryPlace.service.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("users")
public class UserController extends CrudController<User, UserDTO, ResponseUserDTO, Long> {

    private final IUserService userService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserController(IUserService userService, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        super(User.class, UserDTO.class, ResponseUserDTO.class);
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
    public ResponseEntity<ResponseUserDTO> update(@PathVariable Long id, @RequestBody @Valid UserDTO dto) {
        User existingUser = getService().findOne(id);

        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Map the UserDTO to the existing User entity
        modelMapper.map(dto, existingUser);

        // Check if a new password is provided, and if so, encrypt it
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            String encryptedPassword = passwordEncoder.encode(dto.getPassword());
            existingUser.setPassword(encryptedPassword);  // Set the encrypted password
        }

        // Save the updated user entity
        User updatedUser = getService().save(existingUser);

        // Convert the updated entity to Response DTO
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