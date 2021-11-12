package com.example.security.dto.groupuser;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ListMemberinGroup {
    @NotNull
    private String userId;

    private String userName;

    @NotNull
    private String groupId;

    private String groupName;
}
