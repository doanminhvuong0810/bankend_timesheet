package com.example.security.dto.history;

import com.example.security.entity.Group;
import lombok.Data;

import java.util.Date;

@Data
public class HistoryGroupCreate {
    private Date date;
    private String methos;
    private String changeby;
    private String groupId;
    private String note;
}
