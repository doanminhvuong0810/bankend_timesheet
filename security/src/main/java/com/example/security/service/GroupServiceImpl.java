package com.example.security.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.example.security.dto.group.*;
import com.example.security.entity.User;
import com.example.security.entity.UserGroupRel;
import com.example.security.repo.UserGroupRelRepo;
import com.example.security.repo.UserRepo;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import com.example.common.dto.request.PatchRequest;
import com.example.common.util.SearchUtil;
import com.example.security.entity.Group;
import com.example.security.repo.GroupRepo;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {

    @Autowired
    private UserGroupRelRepo userGroupRelRepo;
    @Autowired
    private UserRepo userRole;
    @Autowired
    private GroupRepo groupRepo;

    @Override
    public List<GetAllGroups> getAllGroups() {
        try {
            List<Group> groups = groupRepo.findAll();
            List<GetAllGroups> getAllGroups = new ArrayList<>();
            groups.forEach(group -> {
                GetAllGroups allGroup = new GetAllGroups();
                allGroup.setId(group.getId());
                allGroup.setName(group.getName());
                getAllGroups.add(allGroup);
            });

            return getAllGroups;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Optional<Group> findById(String id) {
        return groupRepo.findById(id);
    }

    @Override
    public Group create(@Valid CreateGroupRequest group) {
        Optional<Group> oGroup = groupRepo.findByNameIgnoreCase(group.getName());
        if (oGroup.isPresent()) {
            throw new DuplicateKeyException("common.error.dupplicate");
        } else {
            try {
                Group g = new Group();
                PropertyUtils.copyProperties(g, group);
                g.setFullTextSearch(g.getName());
                return groupRepo.save(g);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Group update(@Valid UpdateGroupRequest updateGroupRequest) {
        try {
            Optional<Group> ugr = groupRepo.findById(updateGroupRequest.getId());
            if(!ugr.isPresent()){
                throw new NotFoundException("common.error.not-found");
            } else {
                try {
                    Group gr = ugr.get();
                    PropertyUtils.copyProperties(gr, updateGroupRequest);
                    return groupRepo.save(gr);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Group updateAllFields(String id, @Valid UpdateGroupRequest group) {
        Optional<Group> oGroup = groupRepo.findById(id);
        Optional<Group> oGroupByName = groupRepo.findByNameIgnoreCase(group.getName());
        if (!oGroup.isPresent()) {
            throw new NotFoundException("common.error.not-found");
        } else if (oGroupByName.isPresent()) {
            throw new DuplicateKeyException("common.error.dupplicate");
        } else {
            try {
                Group g = oGroup.get();
                PropertyUtils.copyProperties(g, group);
                g.setFullTextSearch(g.getName());
                return groupRepo.save(g);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Group updateSomeFields(String id, @Valid PatchRequest<UpdateGroupRequest> patchRequest) {
        Optional<Group> oGroup = groupRepo.findById(id);
        if (!oGroup.isPresent()) {
            throw new NotFoundException("common.error.not-found");
        } else {
            try {
                Group g = oGroup.get();
                for (String fieldName : patchRequest.getUpdateFields()) {
                    Object newValue = PropertyUtils.getProperty(patchRequest.getData(), fieldName);
                    PropertyUtils.setProperty(g, fieldName, newValue);
                }
                g.setFullTextSearch(g.getName());
                return groupRepo.save(g);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void deleteById(String id) {
        Optional<Group> oGroup = groupRepo.findById(id);
        if (!oGroup.isPresent()) {
            throw new NotFoundException("common.error.not-found");
        } else {
            groupRepo.deleteById(id);
        }
    }

    @Override
    public int deleteByIdIn(List<String> ids) {
        int count = groupRepo.deleteByIdIn(ids);
        if (count < ids.size()) {
            throw new NotFoundException("common.error.not-found");
        }
        return count;
    }

    @Override
    public Group setActive(String id, boolean isActive) {
        Optional<Group> oGroup = groupRepo.findById(id);
        if (oGroup.isEmpty()) {
            throw new NotFoundException("common.error.not-found");
        }
        oGroup.get().setActive(isActive);
        return oGroup.get();
    }

    @Override
    public Page<Group> advanceSearch(String filter, SearchGroupRequest searchRequest, Pageable pageable) {
        if (searchRequest != null) {
            List<Specification<Group>> specList = getAdvanceSearchSpecList(searchRequest);
            if (filter != null && !filter.isEmpty()) {
                specList.add(SearchUtil.like("fullTextSearch", "%" + filter + "%"));
            }
            if (specList.size() > 0) {
                Specification<Group> spec = specList.get(0);
                for (int i = 1; i < specList.size(); i++) {
                    spec = spec.and(specList.get(i));
                }
                return groupRepo.findAll(spec, pageable);
            }
        }
        return groupRepo.findAll(pageable);
    }

    private List<Specification<Group>> getAdvanceSearchSpecList(@Valid SearchGroupRequest s) {
        List<Specification<Group>> specList = new ArrayList<>();
        if (s.getName() != null && !s.getName().isEmpty()) {
            specList.add(SearchUtil.like("name", "%" + s.getName() + "%"));
        }
        if (s.getIsActive() != null) {
            specList.add(SearchUtil.eq("isActive", s.getIsActive()));
        }
        if (s.getCreateDate() != null) {
            if (s.getCreateDate().getFromValue() != null) {
                specList.add(SearchUtil.ge("createdDate", s.getCreateDate().getFromValue()));
            }
            if (s.getCreateDate().getFromValue() != null) {
                specList.add(SearchUtil.lt("createdDate", s.getCreateDate().getToValue()));
            }
        }
        return specList;
    }

    @Override
    public Optional<Group> findByNameIgnoreCase(String name) {
        return groupRepo.findByNameIgnoreCase(name);
    }

    @Override
    public Page<Group> findAll(Pageable pageable) {
        return groupRepo.findAll(pageable);
    }

    @Override
    public Page<Group> findByFullTextSearchContains(String filter, Pageable pageable) {
        return groupRepo.findByFullTextSearchContains(filter, pageable);
    }

    @Override
    public void addUserToGroup(GroupForAdminRequest request) {
        try {
            Optional<User> optionalUser = userRole.findByName(request.getUsername());
            if (optionalUser.isEmpty()) throw new NotFoundException("user.not.found");
            Optional<Group> optionalGroup = groupRepo.findById(request.getId());
            if (optionalGroup.isEmpty()) throw new NotFoundException("group.error.not.found");
            UserGroupRel userGroupRel = new UserGroupRel();
            String idGroup = optionalGroup.get().getId();
            String idUser = optionalUser.get().getId();
            Optional<UserGroupRel> optionalUserGroupRel = userGroupRelRepo.findByIdUserAndIdGroup(idGroup, idUser);
            if (!optionalUserGroupRel.isEmpty()) throw new DuplicateKeyException("user.contains.in.group");
            userGroupRel.setGroupId(idGroup);
            userGroupRel.setUserId(idUser);
//        userGroupRel.setUser(optionalUser.get());
//        userGroupRel.setGroup(optionalGroup.get());
            userGroupRel.setDeleted(false);
            userGroupRelRepo.save(userGroupRel);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeUserFromGroup(GroupForAdminRequest request) {
        try {
            Optional<User> optionalUser = userRole.findByName(request.getUsername());
            if (optionalUser.isEmpty()) throw new NotFoundException("user.not.found");
            Optional<Group> optionalGroup = groupRepo.findById(request.getId());
            if (optionalGroup.isEmpty()) throw new NotFoundException("group.error.not.found");
            String idGroup = optionalGroup.get().getId();
            String idUser = optionalUser.get().getId();
            Optional<UserGroupRel> optionalUserGroupRel = userGroupRelRepo.findByIdUserAndIdGroup(idGroup, idUser);
            if (optionalUserGroupRel.isEmpty()) throw new DuplicateKeyException("user.not.found.in.group");
            UserGroupRel userGroupRel = optionalUserGroupRel.get();
//        userGroupRel.setUser(optionalUser.get());
//        userGroupRel.setGroup(optionalGroup.get());
            userGroupRel.setDeleted(true);
            userGroupRelRepo.save(userGroupRel);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}