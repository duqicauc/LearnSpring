package com.javaadu.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class LogingLog implements Serializable {
    private int loginLogId;

    private int userId;

    private String ip;

    private Date loginDate;
}
