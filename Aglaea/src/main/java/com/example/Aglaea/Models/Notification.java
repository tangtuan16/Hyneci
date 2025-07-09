package com.example.Aglaea.Models;

import java.time.LocalDate;

public class Notification {
    private Integer id;
    private User user;
    private String title;
    private String content;
    private LocalDate notificationTime;
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(LocalDate notificationTime) {
        this.notificationTime = notificationTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
