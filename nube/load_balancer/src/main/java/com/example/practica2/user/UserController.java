package com.example.practica2.user;

import com.example.practica2.exception.ResponseEntityException;
import com.example.practica2.expiration.Expiration;
import com.example.practica2.security.Rol;
import com.example.practica2.security.RolRepository;
import com.example.practica2.security.UserLoggued;
import com.example.practica2.utils.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    private RolRepository roleRepository;
    private UserLoggued userLoggued;
    public UserController(UserService userService, RolRepository roleRepository, UserLoggued userLoggued) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.userLoggued = userLoggued;
    }
    @ExceptionHandler(ResponseEntityException.class)
    public ResponseEntity<ApiResponse> handleExceptions(ResponseEntityException exception) {
        System.out.println("Paso por ResponseEntityErrorException");
        return exception.getApiResponse();
    }
    @GetMapping("/")
    public String getAllUser(Model model){
        User userLogged = userLoggued.getUserLoggued();
        model.addAttribute("userLogged",  userLogged);
        model.addAttribute("userList", userService.getAll().getBody().getData());
        model.addAttribute("adminList", userService.getUsersByAdmin());
        //      return userService.getAll();
        return "users";
    }
    @GetMapping( "/{id}")
    public ResponseEntity<ApiResponse> getUser(@PathVariable Long id){
        return userService.getOneById(id);
    }
    @GetMapping(value = "/create")
    public String createUser(Model model){
        List<Rol> roleList = roleRepository.findAll();
        User userLogged = userLoggued.getUserLoggued();

        model.addAttribute("userLogged",  userLogged);
        model.addAttribute("roleList", roleList);
        return "userForm";
    }
    @GetMapping(value = "/update/{id}")
    public String editUser(@PathVariable Long id, Model model){
        ResponseEntity<ApiResponse> userResponse = userService.getOneById(id);
        List<Rol> roleList = roleRepository.findAll();
        User userLogged = userLoggued.getUserLoggued();

        model.addAttribute("userLogged",  userLogged);
        model.addAttribute("userResponse", userResponse);
        model.addAttribute("roleList", roleList);
        return "userForm";
    }
    @PostMapping( "/update/{id}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequest userRequest ){
        return userService.updateUser(id, userRequest);
    }
    @PostMapping("/")
    public ResponseEntity<ApiResponse> createUser(@Valid @RequestBody UserRequest userRequest){
        return userService.save(userRequest);
    }
    @PostMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }
}
