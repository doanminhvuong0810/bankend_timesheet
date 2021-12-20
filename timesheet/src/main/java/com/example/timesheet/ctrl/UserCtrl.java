package com.example.timesheet.ctrl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.common.dto.response.SuccessResponse;
import com.example.timesheet.entity.User;
import com.example.timesheet.service.IUserService;
@RestController
@CrossOrigin
@RequestMapping("/api/v1/timesheet/user")
public class UserCtrl {
    @Autowired
    IUserService iUserService;

    @GetMapping("get")
    public List<User> getAllUsers(){
        return iUserService.getAllUsers();
    }


    @PostMapping("new")
    @ResponseBody
    public SuccessResponse create(@Valid @RequestBody User user) {
    	iUserService.create(user);
        return new SuccessResponse();
    }

    @PostMapping("update")
    @ResponseBody
    public SuccessResponse updateUserById(@Valid @RequestBody User user){
    	iUserService.updateUserById(user);
        return new SuccessResponse();
    }

    @PutMapping("delete/{id}")
    @ResponseBody
    public SuccessResponse delete(@Valid @RequestBody User user) {
    	iUserService.deleteUserById(user);
        return new SuccessResponse();
    }
}
