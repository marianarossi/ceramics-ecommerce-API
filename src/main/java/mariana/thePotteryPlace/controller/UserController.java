package mariana.thePotteryPlace.controller;

import mariana.thePotteryPlace.dto.request.UserDTO;
import mariana.thePotteryPlace.dto.response.ResponseUserDTO;
import mariana.thePotteryPlace.dto.update.UserUpdateDTO;
import mariana.thePotteryPlace.model.User;
import mariana.thePotteryPlace.service.ICrudService;
import mariana.thePotteryPlace.service.IUserService;
import mariana.thePotteryPlace.validation.UpdateGroup;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
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
    public ResponseEntity<ResponseUserDTO> update(
            @PathVariable Long id,
            @RequestBody @Validated(UpdateGroup.class) UserDTO dto) {
        User existingUser = getService().findOne(id);
        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Convert UserDTO to UserUpdateDTO manually (or via ModelMapper)
        UserUpdateDTO updateDto = new UserUpdateDTO();
        updateDto.setDisplayName(dto.getDisplayName());
        updateDto.setPassword(dto.getPassword());
        updateDto.setSsn(dto.getSsn());
        updateDto.setBirthDate(dto.getBirthDate());
        updateDto.setGender(dto.getGender());
        updateDto.setPhone(dto.getPhone());

        // Update only the allowed fields
        if (updateDto.getDisplayName() != null) {
            existingUser.setDisplayName(updateDto.getDisplayName());
        }
        if (updateDto.getSsn() != null) {
            existingUser.setSsn(updateDto.getSsn());
        }
        if (updateDto.getBirthDate() != null) {
            existingUser.setBirthDate(updateDto.getBirthDate());
        }
        if (updateDto.getGender() != null) {
            existingUser.setGender(updateDto.getGender());
        }
        if (updateDto.getPhone() != null) {
            existingUser.setPhone(updateDto.getPhone());
        }
        if (updateDto.getPassword() != null && !updateDto.getPassword().isEmpty()) {
            existingUser.setPassword(updateDto.getPassword());
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
