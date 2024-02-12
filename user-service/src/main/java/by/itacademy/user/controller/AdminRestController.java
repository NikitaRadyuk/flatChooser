package by.itacademy.user.controller;

import by.itacademy.user.aspect.Auditor;
import by.itacademy.user.core.dto.UserCreateDto;
import by.itacademy.user.core.dto.UserDTO;
import by.itacademy.user.core.entity.UserEntity;
import by.itacademy.user.core.enums.ActionForAudit;
import by.itacademy.user.core.enums.EntityForAudit;
import by.itacademy.user.service.api.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@RestController
@RequestMapping(value = "/users")
public class AdminRestController {
    private final ModelMapper modelMapper;
    private final IUserService userService;

    public AdminRestController(ModelMapper modelMapper, IUserService userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<String> createUser(@RequestBody UserCreateDto user){
        this.userService.createUserByAdmin(user);
        return new ResponseEntity<>("Пользователь создан", HttpStatus.CREATED);
    }

    @GetMapping
    @ResponseBody
    public Page<UserDTO> getUsers(@RequestParam(defaultValue = "0") Integer number,
                                  @RequestParam(defaultValue = "20") Integer size){
        Pageable pageable = PageRequest.of(number, size);
        Page<UserEntity> page = this.userService.getAllUsers(pageable);
        return page.map(AdminRestController::apply);
    }

    @GetMapping("/{uuid}")
    @ResponseBody
    public UserDTO getUserByUuid(@PathVariable(name = "uuid") UUID uuid){
        return modelMapper.map(this.userService.findUserByUUID(uuid), UserDTO.class);
    }

    @PutMapping("/{uuid}/dt_update/{dt_update}")
    @ResponseBody
    public ResponseEntity<String> updateUser(@PathVariable(name = "uuid") UUID uuid,
                                             @PathVariable(name = "dt_update") Long dt_update,
                                             @Validated @RequestBody UserCreateDto userCreateDto){
        LocalDateTime updatedDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(dt_update), ZoneId.systemDefault());
        this.userService.updateUser(userCreateDto, uuid, updatedDate);
        return ResponseEntity.ok("Обновлена информация о пользователе");
    }

    private static UserDTO apply(UserEntity user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUuid(user.getUuid());
        userDTO.setDt_create(user.getDtCreate());
        userDTO.setDt_update(user.getDtUpdate());
        userDTO.setMail(user.getMail());
        userDTO.setFio(user.getFio());
        userDTO.setRole(user.getRole());
        userDTO.setStatus(user.getUserStatus());
        return userDTO;
    }
}
