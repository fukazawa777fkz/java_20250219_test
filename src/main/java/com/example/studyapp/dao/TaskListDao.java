package com.example.studyapp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.studyapp.entity.TaskEntity;

@Mapper
public interface TaskListDao {

    List<TaskEntity> findAllTaskByUserId(int userId);

}
