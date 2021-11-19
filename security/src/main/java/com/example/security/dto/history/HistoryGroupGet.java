package com.example.security.dto.history;

import lombok.Data;

import java.util.Date;


@Data
public class HistoryGroupGet {
    private String id;
    private Date date;
    private String methos;
    private String changeby;
    private String groupId;
    private String note;
}
