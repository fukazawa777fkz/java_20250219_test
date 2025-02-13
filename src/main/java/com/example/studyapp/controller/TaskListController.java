package com.example.studyapp.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.studyapp.entity.TaskEntity;
import com.example.studyapp.entity.UserEntity;
import com.example.studyapp.service.TaskListService;

import jakarta.servlet.http.HttpSession;

@Controller
public class TaskListController {

    @Autowired
    private TaskListService taskListService;

    @GetMapping("/taskList/{userId}")
    public String viewTaskList(Model model, @PathVariable("userId") int userId, HttpSession session) {

        // セッションからユーザー情報を取得
        UserEntity userEntity = (UserEntity) session.getAttribute("userEntity");
        if (userEntity == null) {
            return "redirect:/login";
        } else if (userEntity.getUserId() != userId) {
            return "redirect:/login";
        } else {
            model.addAttribute("userEntity", userEntity);
            List<TaskEntity> taskList = taskListService.searchAllTask(userEntity.getUserId());
            if (taskList == null || taskList.size() == 0) {
                taskList = Collections.emptyList();
                model.addAttribute("Msg", "⚠️ 登録されているタスクはありません。");
            }
            model.addAttribute("taskList", taskList);
            return "taskList";
        }
    }

}
