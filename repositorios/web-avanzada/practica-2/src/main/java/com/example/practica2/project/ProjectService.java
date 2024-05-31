package com.example.practica2.project;

import com.example.practica2.exception.BadRequestException;
import com.example.practica2.exception.ResourceNotFoundException;
import com.example.practica2.mock.Mock;
import com.example.practica2.mock.MockResponse;
import com.example.practica2.security.UserLoggued;
import com.example.practica2.security.jwt.JwtTokenProvider;
import com.example.practica2.user.User;
import com.example.practica2.utils.ERole;
import com.example.practica2.utils.response.ApiResponse;
import com.example.practica2.utils.response.CustResponseBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProjectService {
    private ProjectRepository projectRepository;
    private CustResponseBuilder custResponseBuilder;
    private ModelMapper modelMapper;
    private UserLoggued userLoggued;
    private JwtTokenProvider jwtTokenProvider;

    public ProjectService(ProjectRepository projectRepository, CustResponseBuilder custResponseBuilder, ModelMapper modelMapper, UserLoggued userLoggued, JwtTokenProvider jwtTokenProvider) {
        this.projectRepository = projectRepository;
        this.custResponseBuilder = custResponseBuilder;
        this.modelMapper = modelMapper;
        this.userLoggued = userLoggued;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public ResponseEntity<ApiResponse> getAll() {
        ResponseEntity<ApiResponse> response;
        User user = userLoggued.getUserLoggued();
        List<Project> p1 = new ArrayList<>();
        List<ProjectResponse> projectResponseList;

        if (user.getRoles().stream().anyMatch(rol -> rol.getRole().equals(ERole.ROLE_ADMIN.name()))){
            p1 = projectRepository.findAll();
        }else{
            p1 = user.getProjects();
        }

        projectResponseList = Arrays.asList(modelMapper.map(p1, ProjectResponse[].class));
        response = custResponseBuilder.buildResponse(HttpStatus.OK.value(), projectResponseList);
        return response;
    }

    public ResponseEntity<ApiResponse> getOneById(UUID id) {
        Project project = myFindOneById(id);
        List<MockResponse> mocks = new ArrayList<>();

        ProjectResponse projectResponse = modelMapper.map(project, ProjectResponse.class);

        mocks = Arrays.asList(modelMapper.map(project.getMocks(), MockResponse[].class));
        for (Mock m1 : project.getMocks()) {
            if (m1.getEnableJwt()){
                String jwt = jwtTokenProvider.generateToken(m1);
                mocks.stream().filter(m3 -> m3.getId().equals(m1.getId())).findFirst().get().setToken(jwt);
            }
        }
//        for(MockResponse mr : mocks){
//            for(Mock m : project.getMocks()){
//                String jwt = jwtTokenProvider.generateToken(m);
//                mr.setToken(jwt);
//            }
//        }
        projectResponse.setMockResponseList(mocks);


        return custResponseBuilder.buildResponse(HttpStatus.OK.value(), projectResponse);
    }

    public Project myFindOneById(UUID id){
        User user = userLoggued.getUserLoggued();
        Project p1 = null;

        if (user.getRoles().stream().anyMatch(rol -> rol.getRole().equals(ERole.ROLE_ADMIN.name()))){
            p1 = projectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Project.class.getSimpleName(), "ID", id));
            return p1;
        }else{
            Optional<Project> project = user.getProjects().stream().filter(pr -> pr.getId().equals(id)).findFirst();
            if (project.isPresent() && project != null){
                return project.get();
            }else{
                System.out.println("El usuario no tiene acceso a este proyecto");
                throw new ResourceNotFoundException(Project.class.getSimpleName(), "ID", id);
            }
        }
    }

    public ResponseEntity<ApiResponse> save(String name) {
        ResponseEntity<ApiResponse> response;
        User user = userLoggued.getUserLoggued();

        try {
            Project project = new Project(name);
            project.setUser(user);
            Project p1 = projectRepository.save(project);
            ProjectResponse projectResponseList = modelMapper.map(p1, ProjectResponse.class);
            response = custResponseBuilder.buildResponse(HttpStatus.OK.value(), "Project Created Successfully", projectResponseList);
        }catch (Exception e){
            response = custResponseBuilder.buildResponse(HttpStatus.BAD_REQUEST.value(), "Error creating Project", e.getMessage());
        }
        return response;
    }

    public ResponseEntity<ApiResponse> deleteProject(UUID id) {
        ResponseEntity<ApiResponse> response;
        User user = userLoggued.getUserLoggued();
        Project p1 = projectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Project.class.getSimpleName(), "ID", id));

        try {
            if (user.getRoles().stream().anyMatch(rol -> rol.getRole().equals(ERole.ROLE_ADMIN.name()))){
                projectRepository.delete(p1);
                response = custResponseBuilder.buildResponse(HttpStatus.OK.value(), "Project Deleted Successfully");
            }else if(p1.getUser().getId().equals(user.getId())){
                projectRepository.delete(p1);
                response = custResponseBuilder.buildResponse(HttpStatus.OK.value(), "Project Deleted Successfully");
            }else{
                throw new ResourceNotFoundException(Project.class.getSimpleName(), "ID", id);
            }
        }catch (Exception e){
            response = custResponseBuilder.buildResponse(HttpStatus.BAD_REQUEST.value(), "Error deleting Project", e.getMessage());
        }
        return response;
    }

    public ResponseEntity<ApiResponse> updateProject(UUID uuid, String name) {
        ResponseEntity<ApiResponse> response;
        Project p1 = myFindOneById(uuid);

        try {
            p1.setName(name);
            p1 = projectRepository.save(p1);
            response = custResponseBuilder.buildResponse(HttpStatus.OK.value(), "Project Updated Succesfully", p1);
        }catch (Exception e){
            response = custResponseBuilder.buildResponse(HttpStatus.BAD_REQUEST.value(), "Error udpdating Project");
            throw new BadRequestException(response);
        }
        return response;
    }
}
