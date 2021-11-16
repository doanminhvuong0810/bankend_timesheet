package com.example.security.service;

import com.example.security.dto.groupuser.AddMemberToGroup;
import com.example.security.dto.groupuser.ListMemberinGroup;
import com.example.security.entity.UserGroupRel;
import com.example.security.repo.UserGroupRelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserGroupServiceImpl implements UserGroupService {

    @Autowired
    UserGroupRelRepo userGroupRelRepo;

    @Override
    public UserGroupRel addmember(AddMemberToGroup addMemberToGroup) {
        try {
            UserGroupRel userGroupRel = userGroupRelRepo.findByIdGrouPAndIdUser(addMemberToGroup.getGroupId(), addMemberToGroup.getUserId());
            System.out.println("adsdadasdsa" + userGroupRel);
            if (userGroupRel != null) {
                throw new DuplicateKeyException("common.error.dupplicate");
            } else {
                UserGroupRel ugr = new UserGroupRel();
                ugr.setGroupId(addMemberToGroup.getGroupId());
                ugr.setUserId(addMemberToGroup.getUserId());
                return userGroupRelRepo.save(ugr);
            }
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void removemember(String id) {
        Optional<UserGroupRel> ugr = userGroupRelRepo.findById(id);
        if (!ugr.isPresent()) {
            throw new NotFoundException("common.error.not-found");
        } else {
            userGroupRelRepo.deleteById(id);
        }
    }

    @Override
    public List<ListMemberinGroup> getlistMemberinGroup̣̣̣̣̣̣(String groupId) {
        try {

            List<UserGroupRel> userGroupRel = userGroupRelRepo.allMemberinGroupbyId(groupId);
            List<ListMemberinGroup> listMemberinGroups = new ArrayList<>();
            userGroupRel.forEach(gr -> {
                ListMemberinGroup listMG = new ListMemberinGroup();
                listMG.setGroupId(gr.getGroupId());
                listMG.setGroupName(gr.getGroup().getName());
                listMG.setUserId(gr.getUserId());
                listMG.setUserName(gr.getUser().getName());
                listMemberinGroups.add(listMG);
            });

            return listMemberinGroups;
        } catch (Exception e){
        throw new RuntimeException(e);
    }
    }
}
