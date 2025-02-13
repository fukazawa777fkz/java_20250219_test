package com.example.studyapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.studyapp.dao.TaskListDao;
import com.example.studyapp.entity.TaskEntity;

@Service
public class TaskListServiceImpl implements TaskListService {

    @Autowired
    private TaskListDao taskListDao;

    // タスクの全件取得
    @Override
    public List<TaskEntity> searchAllTask(int userId) {
        return taskListDao.findAllTaskByUserId(userId);
    }

}
