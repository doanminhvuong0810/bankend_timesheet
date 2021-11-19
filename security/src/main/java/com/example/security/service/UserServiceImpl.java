package com.example.security.service;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.*;
import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.webjars.NotFoundException;

import com.example.common.dto.request.PatchRequest;
import com.example.common.enums.TokenTypeEnum;
import com.example.common.model.SecurityConfigParam;
import com.example.common.service.TokenService;
import com.example.common.util.SearchUtil;
import com.example.common.util.SecurityUtil;
import com.example.security.dto.user.CreateUserRequest;
import com.example.security.dto.user.ForgotPasswordRequest;
import com.example.security.dto.user.GetAllUsers;
import com.example.security.dto.user.RegisterUserRequest;
import com.example.security.dto.user.ResetPasswordRequest;
import com.example.security.dto.user.SearchUserRequest;
import com.example.security.dto.user.UpdatePasswordRequest;
import com.example.security.dto.user.UpdateUserRequest;
import com.example.security.entity.Role;
import com.example.security.entity.User;
import com.example.security.entity.UserRoleRel;
import com.example.security.enums.UserTypeEnum;
import com.example.security.repo.RoleRepo;
import com.example.security.repo.UserRepo;
import com.example.security.repo.UserRoleRelRepo;

import ch.qos.logback.core.joran.util.beans.BeanUtil;

@Service
@Transactional
public class UserServiceImpl implements UserService {
  @Autowired
  private UserRepo userRepo;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private TokenService tokenService;
  @Autowired
  private SecurityConfigParam securityParam;
  @Autowired
  private RoleRepo roleRepo;
  @Autowired
  private UserRoleRelRepo userRoleRelRepo;
  private String id;

  @Override
  public List<GetAllUsers> getAllUsers() {
    try {
        List<User> user = userRepo.findAll();
        List<GetAllUsers> getall = new ArrayList<>();
        user.forEach(u -> {
          GetAllUsers allUsers = new GetAllUsers() ;
          allUsers.setId(u.getId());
          allUsers.setName(u.getName());
          allUsers.setDisplayName(u.getDisplayName());
          allUsers.setPassword(u.getPassword());
          allUsers.setStaffID(u.getStaffID());
          allUsers.setBirthDay(u.getBirthDay());
          allUsers.setPhone(u.getPhone());
          allUsers.setBankName(u.getBankName());
          allUsers.setBankNumber(u.getBankNumber());
          allUsers.setEmail(u.getEmail());
          allUsers.setFailLoginCount(u.getFailLoginCount());
          allUsers.setLock(u.isLock());
          allUsers.setAffectDate(u.getAffectDate());
          allUsers.setExpireDate(u.getExpireDate());
          allUsers.setTestAmount(u.getTestAmount());
          allUsers.setTestSelectId(u.getTestSelectId());
          allUsers.setUserType(u.getUserType());
          allUsers.setFullTextSearch(u.getFullTextSearch());
          getall.add(allUsers);
        });
        return getall;
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
  }


  @Override
  public User create(@Valid CreateUserRequest user) {
    try {
      User u = new User();
      PropertyUtils.copyProperties(u, user);
      u.setPassword(passwordEncoder.encode("Admin123@"));
      user.setName(u.getName());
      userRepo.save(u);
      return u;
    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void updateAllFields(String id, @Valid UpdateUserRequest user) {
    Optional<User> oUser = userRepo.findById(id);
    if (!oUser.isPresent()) {
      throw new NotFoundException("common.error.not-found");
    } else {
      try {
        User u = oUser.get();
        PropertyUtils.copyProperties(u, user);
        userRepo.save(u);
      } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
        throw new RuntimeException(e);
      }
    }
  }

  @Override
  public void updateSomeFields(String id, @Valid PatchRequest<UpdateUserRequest> patchRequest) {
    Optional<User> oUser = userRepo.findById(id);
    if (!oUser.isPresent()) {
      throw new NotFoundException("common.error.not-found");
    } else {
      try {
        User u = oUser.get();
        for (String fieldName : patchRequest.getUpdateFields()) {
          Object newValue = PropertyUtils.getProperty(patchRequest.getData(), fieldName);
          PropertyUtils.setProperty(u, fieldName, newValue);
        }
        userRepo.save(u);
      } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
        throw new RuntimeException(e);
      }
    }
  }

  @Override
  public void deleteById(String id) {
    Optional<User> oUser = userRepo.findById(id);
    if (!oUser.isPresent()) {
      throw new NotFoundException("common.error.not-found");
    } else {
      userRepo.deleteById(id);
    }
  }

  @Override
  public int deleteByIdIn(List<String> ids) {
    int count = userRepo.deleteByIdIn(ids);
    if (count < ids.size()) {
      throw new NotFoundException("common.error.not-found");
    }
    return count;
  }

  @Override
  public void setActive(String id, boolean isActive) {
    int count = userRepo.updateIsActive(isActive, id);
    if (count == 0) {
      throw new NotFoundException("common.error.not-found");
    }
  }

  @Override
  public Page<User> advanceSearch(String filter, @Valid SearchUserRequest searchRequest, Pageable pageable) {
    if (searchRequest != null) {
      List<Specification<User>> specList = getAdvanceSearchSpecList(searchRequest);
      if (filter != null && !filter.isEmpty()) {
        specList.add(SearchUtil.like("fullTextSearch", "%" + filter + "%"));
      }
      if (specList.size() > 0) {
        Specification<User> spec = specList.get(0);
        for (int i = 1; i < specList.size(); i++) {
          spec = spec.and(specList.get(i));
        }
        return userRepo.findAll(spec, pageable);
      }
    }
    return userRepo.findAll(pageable);
  }

  private List<Specification<User>> getAdvanceSearchSpecList(@Valid SearchUserRequest s) {
    List<Specification<User>> specList = new ArrayList<>();
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
    if (s.getTestSelectId() != null && s.getTestSelectId().size() > 0) {
      if (s.getTestSelectId().size() == 1) {
        specList.add(SearchUtil.eq("testSelectId", s.getTestSelectId().get(0)));
      } else {
        specList.add(SearchUtil.in("testSelectId", s.getTestSelectId()));
      }
    }
    if (s.getUserType() != null && s.getUserType().size() > 0) {
      if (s.getUserType().size() == 1) {
        specList.add(SearchUtil.eq("userType", s.getUserType().get(0)));
      } else {
        specList.add(SearchUtil.in("userType", s.getUserType()));
      }
    }
    return specList;
  }
  DirContext connection;
  public void newConnection(){
		Properties env = new Properties();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, "ldap://localhost:10389");
		env.put(Context.SECURITY_PRINCIPAL, "uid=admin, ou=system");
		env.put(Context.SECURITY_CREDENTIALS, "secret");
		try {
			connection = new InitialDirContext(env);
			System.out.println("hello world : " + connection);
		}
		catch (AuthenticationException exception){
			System.out.println(exception.getMessage());
		}
		catch (NamingException e) {
			e.printStackTrace();
		}
	}
  @Override
  public void register(@Valid RegisterUserRequest request){
    try {
      String userName = request.getName();
      Optional<User> oUser = userRepo.findByNameIgnoreCase(userName);
      if (oUser.isPresent()) {
        throw new DuplicateKeyException("common.error.dupplicate");
      }
      User u = new User();
      System.out.println("đmmm"+request);
      PropertyUtils.copyProperties(u, request);
      u.setStaffID(request.getStaffID());
      u.setActive(true);
      u.setAffectDate(LocalDateTime.now());
      u.setPassword(passwordEncoder.encode(request.getPassword()));
      u.setUserType(UserTypeEnum.WebApp);
      u = userRepo.save(u);
      System.out.println("danh sach adbdsadkuadqa : "+ u.getId());
      UserRoleRel userRoleRel = new UserRoleRel();
      userRoleRel.setUserId(u.getId());
      userRoleRel.setRoleId(roleRepo.findRoleByName("ROLE_ADMIN").getId());

       //thêm vào ldap
      this.newConnection();
      Attributes attributes = new BasicAttributes();
      Attribute attribute = new BasicAttribute("objectClass");
      attribute.add("inetOrgPerson");
      attributes.put(attribute);
      // user details
      attributes.put("sn", ""+ u.getDisplayName());
      try {
        connection.createSubcontext("cn="+ u.getName()+",ou=users,ou=system", attributes);
        System.out.println("success");
      } catch (NamingException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      //.get().getId()
//      List<RoleForUserRegister> inpuRoleUser = request.getListUserRoleRels();
//      List<UserRoleRel> userRoleRels = inpuRoleUser.stream().map( e -> {               
////        Optional<User> usOptional = userRepo.findByNameIgnoreCase(userName);
//////        User user = usOptional.get();
//////        String idrole = usOptional.getId();
////        
////        
////        System.out.printf("danh sach: ", usOptional);
//         UserRoleRel userRoleRel = new UserRoleRel();
////         userRoleRel.setUserId(usOptional.get);
////         Optional<Role> role = Optional.ofNullable(roleRepo.findRoleByName
////             (String.valueOf(request.getListUserRoleRels().getClass().getName())));
//         System.out.printf("danh sach: ", userRoleRel);
//         return userRoleRel; 
      
         
//         Optional<Role> roleOptional =  roleRepo.findRoleRegister();
         
//         oUser
//          userRoleRel.getId()
//         Optional<UserRoleRel> userroleOptional = UserRoleRelRepo.;
//         if(userroleOptional == null) {
//           userRoleRel.setUserId(userRepo.findByNameIgnoreCase(userName).get().getId());
//            userRoleRel.setRoleId(roleRepo.findRoleByName(request.getListUserRoleRels().getClass().getName()).getId());
//         };
//         userRoleRel.setUser(u);
         
        // map thì phải return. alalala
//      }).collect(Collectors.toList());
      userRoleRelRepo.save(userRoleRel);
//      
//      return;
    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      throw new RuntimeException(e);
    } 
  }

  @Override
  public void updatePassword(String id, @Valid UpdatePasswordRequest request) {
    Optional<User> oUser = userRepo.findById(id);
    if (!oUser.isPresent()) {
      throw new NotFoundException("common.error.not-found");
    } else {
      String oldPwd = oUser.get().getPassword();
      if (!passwordEncoder.matches(request.getCurrentPassword(), oldPwd)) {
        throw new NotFoundException("common.error.not-found");
      }
      String pwd = passwordEncoder.encode(request.getNewPassword());
      userRepo.updatePassword(pwd, id);
    }
  }

  @Override
  public void resetPassword(@Valid ResetPasswordRequest request) {
    // Decrypt and validate token
    String token;
    try {
      token = SecurityUtil.decryptDefault(request.getResetPasswordToken());
    } catch (Exception e) {
      throw new IllegalArgumentException("common.error.not-valid-token");
    }
    if (token == null) {
      throw new IllegalArgumentException("common.error.not-valid-token");
    }
    String[] parts = token.split("\n");
    if (parts.length != 4) {
      throw new IllegalArgumentException("common.error.not-valid-token");
    }
    String userId = parts[0];
    String value = parts[1];
    long affectDate = Long.parseLong(parts[2]);
    long expireDate = Long.parseLong(parts[3]);
    long c = System.currentTimeMillis();
    if (c < affectDate || c > expireDate) {
      throw new IllegalArgumentException("common.error.not-valid-token");
    }
    boolean b = tokenService.isTokenValid(userId, value);
    if (!b) {
      throw new IllegalArgumentException("common.error.not-valid-token");
    }

    // Update password
    String pwd = passwordEncoder.encode(request.getNewPassword());
    userRepo.updatePassword(pwd, userId);
  }

  @Override
  public String recoveryRequest(@Valid @RequestBody ForgotPasswordRequest request) {
    Optional<User> oUser = userRepo.findByNameIgnoreCase(request.getName());
    if (!oUser.isPresent()) {
      throw new NotFoundException("common.error.not-found");
    } else {
      // TODO: Send email, remove return result
      String token = generateResetPasswordToken(oUser.get().getId());
      return token;
    }
  }
//  @Override 
//  public UserDetails loadUserDetails(String username) {
//      User user = userRepo.findBy
//  }
  
  private String generateResetPasswordToken(String userId) {
    try {
      String token = UUID.randomUUID().toString();
      LocalDateTime issueDate = LocalDateTime.now();
      LocalDateTime expireDate = issueDate;
      expireDate.plusSeconds(securityParam.getResetPasswordTokenExpirationSecond());
      tokenService.saveToken(userId, TokenTypeEnum.AccessToken, token, issueDate, expireDate);
      return SecurityUtil.encryptDefault(
          String.format("%s\n%s\n%d\n%d", userId, token, issueDate.atZone(ZoneId.systemDefault()).toEpochSecond(),
              expireDate.atZone(ZoneId.systemDefault()).toEpochSecond()));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

//  @Override
//  public List<User> getAllUser() {
//    try {
//    List<User> users;
//    users = userRepo.findAll();
//    List<GetAllUsers> getAllUsers = new ArrayList<>();
//    BeanUtils.copyProperties(getAllUsers, users);
//    return users;
//    } catch (Exception e) {
//      throw new RuntimeException(e);
//    }
//  }
}
