package com.m.edmsbackend.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

@Entity(name = "user_config")
@Data
@DynamicUpdate
@DynamicInsert
public class UserConfig {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Boolean enableCheckAttendanceNotification;
    private Integer enableCheckAttendanceNotificationHour;
    private Integer enableCheckAttendanceNotificationMinute;
    private Boolean enablePushTaskNotification;
    private Integer enablePushTaskNotificationHour;
    private Integer enablePushTaskNotificationMinute;
    private Boolean enableInProgressTaskNotification;
    private Integer enableInProgressTaskNotificationHour;
    private Integer enableInProgressTaskNotificationMinute;

    private Long userId;
}
