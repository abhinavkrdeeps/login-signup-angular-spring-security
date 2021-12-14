package com.wissen.training.loginsignupspringboot;

import lombok.Data;

import java.util.Date;

@Data
public class Todo {
    private String task;
    private Date startDate;
    private Date expectedEndDate;

    public Todo(String task, Date startDate, Date expectedEndDate) {
        this.task=task;
        this.startDate=startDate;
        this.expectedEndDate=expectedEndDate;
    }
}
