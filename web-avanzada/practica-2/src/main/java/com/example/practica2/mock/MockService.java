package com.example.practica2.mock;

import com.example.practica2.exception.*;
import com.example.practica2.expiration.Expiration;
import com.example.practica2.expiration.ExpirationRepo;
import com.example.practica2.project.Project;
import com.example.practica2.project.ProjectService;
import com.example.practica2.security.UserLoggued;
import com.example.practica2.security.jwt.JwtTokenProvider;
import com.example.practica2.user.User;
import com.example.practica2.utils.ERole;
import com.example.practica2.utils.response.ApiResponse;
import com.example.practica2.utils.response.CustResponseBuilder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.*;

@Service
public class MockService {
    private MockRepo mockRepo;
    private CustResponseBuilder custResponseBuilder;
    private ModelMapper modelMapper;
    private UserLoggued userLoggued;
    private ExpirationRepo expirationRepo;
    private ProjectService projectService;
    private JwtTokenProvider jwtTokenProvider;

    public MockService(MockRepo mockRepo, CustResponseBuilder custResponseBuilder, ModelMapper modelMapper,
                       UserLoggued userLoggued, ExpirationRepo expirationRepo, ProjectService projectService
                        , JwtTokenProvider jwtTokenProvider) {
        this.mockRepo = mockRepo;
        this.custResponseBuilder = custResponseBuilder;
        this.modelMapper = modelMapper;
        this.userLoggued = userLoggued;
        this.expirationRepo = expirationRepo;
        this.projectService = projectService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public ResponseEntity<ApiResponse> getOneById(Long id) {
        ResponseEntity<ApiResponse> response;

        Mock mock = myFindOneById(id);
        try {
            MockResponse mockResponse = modelMapper.map(mock, MockResponse.class);
            String jwt = jwtTokenProvider.generateToken(mock);
            mockResponse.setToken(jwt);

            response = custResponseBuilder.buildResponse(HttpStatus.OK.value(), mockResponse);
        }catch (Exception e){
            response = custResponseBuilder.buildResponse(HttpStatus.BAD_REQUEST.value(), "Error Finding Mock", e.getMessage());
        }
        return response;
    }

    public Mock myFindOneById(Long id){
        User user = userLoggued.getUserLoggued();
        Mock m1 = mockRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(Mock.class.getSimpleName(), "ID", id));

        if (user.getRoles().stream().anyMatch(rol -> rol.getRole().equals(ERole.ROLE_ADMIN.name()))){
           return m1;
        }else if(m1.getProject().getUser().getId().equals(user.getId())){
            return m1;
        } else{
            throw new ResourceNotFoundException(Project.class.getSimpleName(), "ID", id);
        }
    }
    public ResponseEntity<ApiResponse> updateMock(UUID uuid, Long mockId, MockRequest mockRequest) {
        ObjectMapper mapper = new ObjectMapper();
        ResponseEntity<ApiResponse> response;

        Expiration expiration = expirationRepo.findById(mockRequest.getExpiration_id()).orElseThrow(() ->
                new ResourceNotFoundException(Mock.class.getSimpleName(), "ID", mockRequest.getExpiration_id()));
        Project p1 = projectService.myFindOneById(mockRequest.getProject_id());
        Mock mock = myFindOneById(mockId);
        List<Mock> newList = p1.getMocks().stream().filter(m -> !m.getId().equals(mockId)).toList();

        validPath(mockRequest.getPath(), newList);

        try {
            String jsonHeader = mapper.writeValueAsString(mockRequest.getHeader());

            modelMapper.map(mockRequest, mock);
            mock.setExpiration(expiration);
            mock.setProject(p1);
            mock.setHeader(jsonHeader);
            Mock m1 = mockRepo.save(mock);

            String jwt = jwtTokenProvider.generateToken(m1);

            MockResponse mockResponse = modelMapper.map(m1, MockResponse.class);
            mockResponse.setExpirationName(m1.getExpiration().getName());
            mockResponse.setProjectName(p1.getName());
            mockResponse.setToken(jwt);

            response = custResponseBuilder.buildResponse(HttpStatus.OK.value(), "Mock Updated Successfully", mockResponse);
        }catch (Exception e){
            response = custResponseBuilder.buildResponse(HttpStatus.BAD_REQUEST.value(), "Error Updating Mock", e.getMessage());
        }
        return response;
    }
    public ResponseEntity<ApiResponse> save(MockRequest mockRequest) {
        ObjectMapper mapper = new ObjectMapper();
        ResponseEntity<ApiResponse> response;

        Expiration expiration = expirationRepo.findById(mockRequest.getExpiration_id()).orElseThrow(() ->
                new ResourceNotFoundException(Mock.class.getSimpleName(), "ID", mockRequest.getExpiration_id()));

        Project p1 = projectService.myFindOneById(mockRequest.getProject_id());
        validPath(mockRequest.getPath(), p1.getMocks());

        try {
            String jsonHeader = mapper.writeValueAsString(mockRequest.getHeader());

            Mock mock = new Mock();
            modelMapper.map(mockRequest, mock);
            mock.setId(null);
            mock.setExpiration(expiration);
            mock.setProject(p1);
            mock.setHeader(jsonHeader);
            Mock m1 = mockRepo.save(mock);

            MockResponse mockResponse = modelMapper.map(m1, MockResponse.class);
            mockResponse.setExpirationName(m1.getExpiration().getName());
            mockResponse.setProjectName(p1.getName());

            if (mockRequest.getEnableJwt()){
                String jwt = jwtTokenProvider.generateToken(m1);
                mockResponse.setToken(jwt);
            }

            response = custResponseBuilder.buildResponse(HttpStatus.OK.value(), "Mock Created Successfully", mockResponse);
        }catch (Exception e){
            response = custResponseBuilder.buildResponse(HttpStatus.BAD_REQUEST.value(), "Error creating Mock", e.getMessage());
        }

        return response;
    }

    private void validPath(String path, List<Mock> mocks) {
        ResponseEntity<ApiResponse> response;

        for (Mock m1 : mocks) {
            if (m1.getPath().equals(path)){
                response = custResponseBuilder.buildResponse(HttpStatus.BAD_REQUEST.value(), "Can't create a mock with the same path in the same project");
                throw new BadRequestException(response);
            }
        }
    }

    public ResponseEntity<ApiResponse> deleteMock(Long id, UUID uuid) {
        ResponseEntity<ApiResponse> response;
        Mock mock = myFindOneById(id);

        try{
            if (mock.getProject().getId().equals(uuid)){
                mockRepo.delete(mock);
                response = custResponseBuilder.buildResponse(HttpStatus.OK.value(), "Mock Deleted Successfully");
            }else{
                response = custResponseBuilder.buildResponse(HttpStatus.UNAUTHORIZED.value(), "Mock doesn't belong to the project");
                throw new UserErrorException(response);
            }
        }catch (Exception e){
            response = custResponseBuilder.buildResponse(HttpStatus.BAD_REQUEST.value(), "Error creating Mock", e.getMessage());
        }
        return response;
    }

    public ResponseEntity mockResponse(UUID uuid, String path, HttpServletRequest request, String token) {

        Project project = projectService.myFindOneById(uuid); //Cambiar este metodo si quiero que quiensea me consulte
        Mock mock = myFindOneByPath(project, path);
        ResponseEntity response;

        if (!mock.getMethod().equalsIgnoreCase(request.getMethod())){
            throw new BadRequestException(custResponseBuilder.buildResponse(HttpStatus.METHOD_NOT_ALLOWED.value(), "Method Not Allowed"));
        }

        if (mock.getEnableJwt()){
            validToken(mock, token);
        }
        validDelay(mock.getDelay());

        try{
            HttpHeaders headers = headersAsObject(mock.getHeader());

            MediaType mediaType = MediaType.parseMediaType(mock.getType());
            String contentType = mediaType + "; charset="+mock.getContentEncode();

            headers.setContentType(MediaType.parseMediaType(mock.getContentType()));
            headers.set(HttpHeaders.CONTENT_TYPE, contentType);

            response = new ResponseEntity(mock.getBody(), headers, mock.getCode());
        }catch (Exception e){
            response = custResponseBuilder.buildResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            throw new BadRequestException(response);
        }

        return response;
    }

    private void validToken(Mock mock, String token) {
        ResponseEntity<ApiResponse> response;

        jwtTokenProvider.validateToken(token);
        Long mockId = jwtTokenProvider.getMockIdFromJWT(token);
        if (!mockId.equals(mock.getId())){
            System.out.println("MockId doesn't equals to jwt mock id");
            response = custResponseBuilder.buildResponse(HttpStatus.UNAUTHORIZED.value(), "You don't have permission to access to this mock");
            throw new UnauthorizedException(response);
        }
    }

    private Boolean validExpiration(Date date, int seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, seconds);
        Date expirationDate = calendar.getTime();
        if (expirationDate.before(new Date())){
            System.out.println("MOCK EXPIRADO: "+expirationDate);
            return false;
        }
        System.out.println("MOCK NO EXPIRADO HASTA: "+expirationDate);
        return true;
    }

    private void validDelay(Integer delay) {
        if (delay != null){
            try {
                Thread.sleep(delay*1000);
            }catch (Exception e){
                throw new AppException(e.getMessage());
            }
        }
    }

    private HttpHeaders headersAsObject(String header) {
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            List<HeaderRequest> keyValueList = objectMapper.readValue(header, new TypeReference<List<HeaderRequest>>() {});
            MultiValueMap<String, String> headersMap = new LinkedMultiValueMap<>();

            for (HeaderRequest keyValue : keyValueList) {
                headersMap.add(keyValue.getKey(), keyValue.getValue());
            }

            HttpHeaders headers = new HttpHeaders();
            headers.addAll(headersMap);
            return headers;
        }catch (Exception e){
            System.out.println("Error here: headersAsObject");
            throw new BadRequestException(custResponseBuilder.buildResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }

    }

    private Mock myFindOneByPath(Project project, String path) {
        Mock mock = project.getMocks().stream().filter(p1 -> p1.getPath().equals(path)).findFirst()
                        .orElseThrow(() -> new ResourceNotFoundException(Mock.class.getSimpleName(), "PATH", path));

        if (!validExpiration(mock.getCreatedAt(), mock.getExpiration().getValue())){
            throw new BadRequestException(custResponseBuilder.buildResponse(HttpStatus.BAD_REQUEST.value(), "The Mock Has Expired!"));
        }
        return mock;
    }

}
