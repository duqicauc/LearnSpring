package com.javaadu.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author javaadu
 * @date 2023/02/15
 **/
@Data
public class User implements Serializable {
    private int userId;
    private String userName;
    private String password;

    private int credits;

    private String lastIp;

    private Date lastVisit;
}
