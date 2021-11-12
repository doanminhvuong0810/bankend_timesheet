package com.example.security.service;

import com.example.security.dto.groupuser.AddMemberToGroup;
import com.example.security.entity.UserGroupRel;
import org.springframework.stereotype.Service;

@Service
public interface UserGroupService {

    UserGroupRel addmember(AddMemberToGroup addMemberToGroup);

    void removemember(String id);

//    Optional<ListMemberinGroup> getlistMemberinGroup̣̣̣̣̣̣(String idGroup, ListMemberinGroup listMemberinGroup);


}
