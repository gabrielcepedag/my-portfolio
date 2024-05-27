package com.example.practica2.project;

import com.example.practica2.exception.ResponseEntityException;
import com.example.practica2.expiration.Expiration;
import com.example.practica2.expiration.ExpirationRepo;
import com.example.practica2.mock.MockRequest;
import com.example.practica2.mock.MockService;
import com.example.practica2.security.UserLoggued;
import com.example.practica2.user.User;
import com.example.practica2.user.UserRequest;
import com.example.practica2.utils.ERole;
import com.example.practica2.utils.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping("/project")
public class ProjectController {
    private ProjectService projectService;
    private MockService mockService;
    private ExpirationRepo expirationRepo;
    private UserLoggued userLoggued;
    private MessageSource messageSource;


    public ProjectController(ProjectService projectService, MockService mockService,
                             ExpirationRepo expirationRepo, UserLoggued userLoggued,
                             MessageSource messageSource) {
        this.projectService = projectService;
        this.mockService = mockService;
        this.expirationRepo = expirationRepo;
        this.userLoggued = userLoggued;
        this.messageSource = messageSource;
    }

    @ExceptionHandler(ResponseEntityException.class)
    public ResponseEntity<ApiResponse> handleExceptions(ResponseEntityException exception) {
        System.out.println("Paso por ResponseEntityErrorException en ProjectController");
        return exception.getApiResponse();
    }

    @GetMapping(value = "/")
    public String getAllProject(Model model){
        User userLogged = userLoggued.getUserLoggued();
        model.addAttribute("userLogged",  userLogged);
        model.addAttribute("projectList", projectService.getAll().getBody().getData());
        //       return projectService.getAll();
        return "projects";
    }
    @GetMapping(value = "/{id}")
    public String getProject(@PathVariable UUID id, Model model){
        ResponseEntity<ApiResponse> response = projectService.getOneById(id);
        User userLogged = userLoggued.getUserLoggued();

        model.addAttribute("userLogged",  userLogged);
        model.addAttribute("response", response);
        return "mocks";
    }

    @PostMapping(value = "/")
    public ResponseEntity<ApiResponse> createProject(@Valid @RequestParam String name){
        return projectService.save(name);
    }
    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<ApiResponse> deleteProject(@PathVariable UUID id){
        return projectService.deleteProject(id);
    }
    @PostMapping(value = "/{uuid}/delete/{id}")
    public ResponseEntity<ApiResponse> deleteMock(@PathVariable Long id, @PathVariable UUID uuid){
        return mockService.deleteMock(id, uuid);
    }
    @PostMapping(value = "/update/{uuid}")
    public ResponseEntity<ApiResponse> updateProject(@PathVariable UUID uuid, @Valid @RequestParam String name ){
        return projectService.updateProject(uuid, name);
    }
    @PostMapping(value = "/{uuid}/update/{id}")
    public ResponseEntity<ApiResponse> updateMockt(@PathVariable UUID uuid,
                                                   @PathVariable Long id,
                                                   @RequestBody MockRequest mockRequest){
        return mockService.updateMock(uuid, id, mockRequest);
    }

    @RequestMapping(value = "/{uuid}/{path}")
    public ResponseEntity mockResponse(@PathVariable UUID uuid, @PathVariable String path,
                                       @RequestParam(required = false) String token, HttpServletRequest request){

        System.out.println("========================================");
        System.out.println("TOKEN: "+token);
        System.out.println("========================================");

        return mockService.mockResponse(uuid, path, request, token);
    }

    @GetMapping(value = "/{id}/mock")
    public String createMockForProject(@PathVariable UUID id, Model model, Locale locale){
        ResponseEntity<ApiResponse> projectResponse = projectService.getOneById(id);
        List<Expiration> expirationList = expirationRepo.findAll();
        User userLogged = userLoggued.getUserLoggued();

        model.addAttribute("userLogged",  userLogged);
        model.addAttribute("projectResponse", projectResponse);
        model.addAttribute("expirationList", expirationList);
        setInternacionalization(model, locale);
        return "mockForm";
    }

    @GetMapping(value = "/{uuid}/mock/{id}")
    public String editMockForProject(@PathVariable UUID uuid, @PathVariable Long id, Model model){
        ResponseEntity<ApiResponse> projectResponse = projectService.getOneById(uuid);
        ResponseEntity<ApiResponse> mockResponse = mockService.getOneById(id);
        List<Expiration> expirationList = expirationRepo.findAll();
        User userLogged = userLoggued.getUserLoggued();

        model.addAttribute("userLogged",  userLogged);
        model.addAttribute("projectResponse", projectResponse);
        model.addAttribute("mockResponse", mockResponse);
        model.addAttribute("expirationList", expirationList);
        return "mockForm";
    }

    private void setInternacionalization(Model model, Locale locale) {
        model.addAttribute("path", messageSource.getMessage("label.path", null, locale));
        model.addAttribute("method", messageSource.getMessage("label.method", null, locale));
        model.addAttribute("headers", messageSource.getMessage("label.headers", null, locale));
        model.addAttribute("responseCode", messageSource.getMessage("label.responseCode", null, locale));
        model.addAttribute("contentType", messageSource.getMessage("label.contentType", null, locale));
        model.addAttribute("contentEncoding", messageSource.getMessage("label.contentEncoding", null, locale));
        model.addAttribute("responseBody", messageSource.getMessage("label.responseBody", null, locale));
        model.addAttribute("displayName", messageSource.getMessage("label.displayName", null, locale));
        model.addAttribute("description", messageSource.getMessage("label.description", null, locale));
        model.addAttribute("responseDelay", messageSource.getMessage("label.responseDelay", null, locale));
        model.addAttribute("seconds", messageSource.getMessage("label.seconds", null, locale));
        model.addAttribute("enableJwt", messageSource.getMessage("label.enableJwt", null, locale));
        model.addAttribute("expiration", messageSource.getMessage("label.expiration", null, locale));
        model.addAttribute("responseDetails", messageSource.getMessage("label.responseDetails", null, locale));
        model.addAttribute("requestDetails", messageSource.getMessage("label.requestDetails", null, locale));
        model.addAttribute("generalDetails", messageSource.getMessage("label.generalDetails", null, locale));
    }
}
