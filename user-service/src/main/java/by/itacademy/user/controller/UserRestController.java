package by.itacademy.user.controller;

import by.itacademy.user.core.dto.*;
import by.itacademy.user.service.api.IAuthorizationService;
import by.itacademy.user.service.api.IVerificationService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/users")
public class UserRestController {
    private final IAuthorizationService authorizationService;
    private final ModelMapper modelMapper;

    public UserRestController(IAuthorizationService authorizationService, ModelMapper modelMapper) {
        this.authorizationService = authorizationService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/registration")
    @ResponseBody
    public ResponseEntity<String> registration(@RequestBody UserRegDTO userRegDTO) {
            this.authorizationService.registrUser(userRegDTO);
            return new ResponseEntity<>("Пользователь зарегистрирован", HttpStatus.OK);
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<String> login(@RequestBody UserLoginDTO userLoginDTO) {
        String token = authorizationService.loginUser(userLoginDTO);
        return new ResponseEntity<>("Токен для авторизации " + token, HttpStatus.OK);
    }

    @GetMapping("/verification")
    @ResponseBody
    public ResponseEntity<String> verify(@RequestParam(value = "code") String code,
                                         @RequestParam(value = "mail") String mail){
        VerificationDTO verificationDTO = new VerificationDTO(code, mail);
        authorizationService.verifyUserByToken(verificationDTO);
        return new ResponseEntity<>("Верификация пользователя прошла успешно", HttpStatus.OK);
    }

    @GetMapping("/me")
    @ResponseBody
    public UserDTO me(){
        return modelMapper.map(this.authorizationService.getInfoAboutMe(), UserDTO.class);
    }
}
