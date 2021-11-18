package com.example.security.dto.groupuser;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ListMemberinGroup {

    private String id;

    @NotNull
    private String userId;

    private String userName;

    private String staffID;

    @NotNull
    private String groupId;

    private String groupName;
}
