package com.example.security.service;

import com.example.security.dto.groupuser.AddMemberToGroup;
import com.example.security.dto.groupuser.ListMemberinGroup;
import com.example.security.entity.UserGroupRel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserGroupService {

    UserGroupRel addmember(AddMemberToGroup addMemberToGroup);

    void removemember(String id);

    List<ListMemberinGroup> getlistMemberinGroup̣̣̣̣̣̣(String idGroup);


}
