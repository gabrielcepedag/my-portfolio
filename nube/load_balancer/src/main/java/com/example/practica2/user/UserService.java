package com.example.practica2.user;

import com.example.practica2.exception.BadRequestException;
import com.example.practica2.exception.ResourceNotFoundException;
import com.example.practica2.utils.response.ApiResponse;
import com.example.practica2.utils.response.CustResponseBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private CustResponseBuilder custResponseBuilder;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository, CustResponseBuilder custResponseBuilder, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.custResponseBuilder = custResponseBuilder;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }
    public ResponseEntity<ApiResponse> getAll(){
        ResponseEntity<ApiResponse> response;

        try{
            List<User> lista = userRepository.findAll();
            List<UserResponse> userResponseList = Arrays.asList(modelMapper.map(lista, UserResponse[].class));
            response = custResponseBuilder.buildResponse(HttpStatus.OK.value(), userResponseList);
        }catch (Exception e){
            response = custResponseBuilder.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
            throw new BadRequestException(response);
        }

        return response;
    }
    public ResponseEntity<ApiResponse> save(UserRequest userRequest) {
        ResponseEntity<ApiResponse> response;

        try{
            User u1 = new User();
            modelMapper.map(userRequest, u1);
            u1.setPassword(passwordEncoder.encode(userRequest.getPassword()));

            User user = userRepository.save(u1);
            response = custResponseBuilder.buildResponse(HttpStatus.OK.value(), "User saved!", user);
        }catch (Exception e){
            response = custResponseBuilder.buildResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }

        return response;
    }

    public ResponseEntity<ApiResponse> deleteUser(Long id){
        //Validar que tenga permiso ADMIN
        ResponseEntity<ApiResponse> response;

        User u1 = findUserById(id);
        u1.setActive(false);
        userRepository.delete(u1);
        response = custResponseBuilder.buildResponse(HttpStatus.OK.value(), "User deleted!", u1);

        return response;
    }
    public ResponseEntity<ApiResponse> updateUser(Long id, UserRequest userRequest){
        ResponseEntity<ApiResponse> response;

        try {
            User u1 = findUserById(id);
            modelMapper.map(userRequest, u1);
            User user = userRepository.save(u1);
            response = custResponseBuilder.buildResponse(HttpStatus.OK.value(), "User updated!", user);

        }catch (Exception e){
            response = custResponseBuilder.buildResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
        return response;
    }
    public ResponseEntity<ApiResponse> getOneById(Long id){
        ResponseEntity<ApiResponse> response;

        UserResponse u1 = findUserResponseById(id);
        response = custResponseBuilder.buildResponse(HttpStatus.OK.value(), u1);

        return response;
    }

    private UserResponse findUserResponseById(Long id) {
        User u1 = userRepository.findById(id).orElse(null);
        if (u1 == null){
            throw new ResourceNotFoundException(User.class.getSimpleName(), "ID", id);
        }
        UserResponse userResponse = modelMapper.map(u1, UserResponse.class);

        return userResponse;
    }
    private User findUserById(Long id) {
        User u1 = userRepository.findById(id).orElse(null);
        if (u1 == null){
            System.out.println("paso por aqui 1");
            throw new ResourceNotFoundException(User.class.getSimpleName(), "ID", id);
        }

        return u1;
    }

    public Optional<User> getOneByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user;
    }

    public List<User> getUsersByAdmin(){
        return userRepository.getUsersByAdmin();
    }
}
