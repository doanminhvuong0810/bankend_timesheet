package com.example.security.service;

import com.example.security.dto.groupuser.AddMemberToGroup;
import com.example.security.dto.groupuser.ListMemberinGroup;
import com.example.security.dto.groupuser.LoadUserIdnoGroup;
import com.example.security.entity.User;
import com.example.security.entity.UserGroupRel;
import com.example.security.repo.UserGroupRelRepo;
import com.example.security.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserGroupServiceImpl implements UserGroupService {

    @Autowired
    UserGroupRelRepo userGroupRelRepo;

    @Autowired
    UserRepo userRepo;

    @Override
    public UserGroupRel addmember(@Valid AddMemberToGroup addMemberToGroup) {
        Optional<User> user = userRepo.findById(addMemberToGroup.getUserId());
        if (user == null) {
            throw new NotFoundException("common.error.not-found");
        }
        String Iduser = user.get().getId();
        UserGroupRel ugroup = userGroupRelRepo.findbyUserIdtoadd(Iduser);
        System.out.println("User in group: " + ugroup);

//        UserGroupRel userGroupRel = userGroupRelRepo.findByIdGrouPAndIdUser(addMemberToGroup.getGroupId(), addMemberToGroup.getUserId());
//        System.out.println("adsdadasdsa" + userGroupRel);

        if (ugroup != null) {
            throw new DuplicateKeyException("common.error.dupplicate");
        } else {
            try {
                UserGroupRel ugr = new UserGroupRel();
                ugr.setGroupId(addMemberToGroup.getGroupId());
                ugr.setUserId(addMemberToGroup.getUserId());
                return userGroupRelRepo.save(ugr);

            } catch (RuntimeException ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    @Override
    public List<ListMemberinGroup> getlistMemberinGroup̣̣̣̣̣̣(String groupId) {
        try {

            List<UserGroupRel> userGroupRel = userGroupRelRepo.allMemberinGroupbyId(groupId);
            List<ListMemberinGroup> listMemberinGroups = new ArrayList<>();
            userGroupRel.forEach(gr -> {
                ListMemberinGroup listMG = new ListMemberinGroup();
                listMG.setId(gr.getId());
                listMG.setGroupId(gr.getGroupId());
                listMG.setGroupName(gr.getGroup().getName());
                listMG.setUserId(gr.getUserId());
                listMG.setUserName(gr.getUser().getName());
                listMemberinGroups.add(listMG);
            });

            return listMemberinGroups;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<LoadUserIdnoGroup> loadUserIdnoGroups() {
        List<User> users = userRepo.findAll();
        List<LoadUserIdnoGroup> loadUserIdnoGroups = new ArrayList<>();
        users.forEach(ugr -> {
            if(userGroupRelRepo.findByUserId(ugr.getId()) == null){
                LoadUserIdnoGroup loadUserIdnoGroup = new LoadUserIdnoGroup();
                loadUserIdnoGroup.setUserId(ugr.getId());
                loadUserIdnoGroup.setUserName(ugr.getName());
                loadUserIdnoGroups.add(loadUserIdnoGroup);
            }
        });
        return loadUserIdnoGroups;
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
}
