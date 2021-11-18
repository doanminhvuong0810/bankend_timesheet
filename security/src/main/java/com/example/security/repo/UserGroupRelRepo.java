package com.example.security.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.security.entity.UserGroupRel;

@Transactional
@Repository
public interface UserGroupRelRepo
    extends JpaRepository<UserGroupRel, String>, JpaSpecificationExecutor<UserGroupRel> {
//  List<UserGroupRel> findByUserId(String userId);

  List<UserGroupRel> findByGroupId(String groupId);

  @Query("select s from UserGroupRel s where s.userId=:userId")
  UserGroupRel findByUserId(String userId);

  Optional<UserGroupRel> findById(String id);

  @Query("delete from UserGroupRel u where u.id=:id")
  UserGroupRel removeUfromGr(String id);

  @Modifying
  long removeByUserId(String userId);

  @Modifying
  long removeByGroupId(String groupId);

  @Query(value ="select o from UserGroupRel o where o.groupId=:idGroup and o.userId=:idUser and o.isDeleted = false")
  Optional<UserGroupRel> findByIdUserAndIdGroup(String idGroup, String idUser);

  @Query("select  g from UserGroupRel g where  g.groupId like %:groupId%")
  List <UserGroupRel> allMemberinGroupbyId(String groupId);

  @Query("select  g from UserGroupRel g where  g.userId like %:userId%")
  UserGroupRel findbyUserIdtoadd(String userId);

  @Query(value ="select o from UserGroupRel o where o.groupId=:idGroup and o.userId=:idUser")
  UserGroupRel findByIdGrouPAndIdUser(String idGroup, String idUser);

  @Query("select g from UserGroupRel g where  g.id=:id")
  UserGroupRel findByIdAdd(String id);

}
