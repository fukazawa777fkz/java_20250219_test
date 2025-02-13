package com.example.studyapp.service;

import java.util.List;

import com.example.studyapp.entity.TaskEntity;

public interface TaskListService {

    List<TaskEntity> searchAllTask(int userId);

}
