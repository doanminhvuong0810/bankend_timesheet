package com.example.security.ctrl.admin;

import com.example.common.dto.request.PatchRequest;
import com.example.common.dto.response.SuccessResponse;
import com.example.security.dto.user.GetAllUsers;
import com.example.security.dto.user.RegisterUserRequest;
import com.example.security.dto.user.UpdateUserRequest;
import com.example.security.entity.User;
import com.example.security.repo.UserRepo;
import com.example.security.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v2/guest/users")
@Validated
@Tag(name = "User Management Controller")
public class UsersMgtCtrl {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/listuser")
    @ResponseBody
    public List<GetAllUsers> getAllUsers() {
        return userService.getAllUsers();
    }
    @GetMapping(value = "/search/name/get")
    @ResponseBody
    public Optional<User> findByName(@RequestParam("name") String name)  {
    return userRepo.findByNameIgnoreCase(name);
//        throw new NotImplementedException();
    }
    @PostMapping("/register")
    @ResponseBody
    public SuccessResponse register(@Valid @RequestBody RegisterUserRequest request) {
        userService.register(request);
        return new SuccessResponse();
    }
    @PostMapping(value = "/{id}/patch")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse updateSomeFields(@PathVariable("id") String id,
                                            @Valid @RequestBody PatchRequest<UpdateUserRequest> patchRequest) throws NotFoundException {
        userService.updateSomeFields(id, patchRequest);
        return new SuccessResponse();
    }

}
