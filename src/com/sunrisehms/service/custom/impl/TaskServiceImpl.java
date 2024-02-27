package com.sunrisehms.service.custom.impl;

import com.sunrisehms.dto.TaskDto;
import com.sunrisehms.entity.TaskEntity;
import com.sunrisehms.enums.RepositoryType;
import com.sunrisehms.repository.RepositoryFactory;
import com.sunrisehms.repository.TaskRepository;
import com.sunrisehms.service.custom.TaskService;
import com.sunrisehms.util.SessionFactoryConfiguration;
import java.util.ArrayList;
import java.util.List;

public class TaskServiceImpl implements TaskService {

    TaskRepository taskRepository = (TaskRepository) RepositoryFactory.getInstance().getRepository(RepositoryType.TASK);

    @Override
    public TaskDto get(Integer id) throws Exception {

        TaskEntity task = taskRepository.get(SessionFactoryConfiguration.getInstance().getSession(), id);
        return new TaskDto(
                task.getId(),
                task.getName()
        );
    }

    @Override
    public List<TaskDto> getAll() throws Exception {
        List<TaskEntity> tasks = taskRepository.getAll(SessionFactoryConfiguration.getInstance().getSession());

        List<TaskDto> taskList = new ArrayList<>();
        for (TaskEntity task : tasks) {
            taskList.add(new TaskDto(
                    task.getId(),
                    task.getName()
            ));
        }
        return taskList;
    }

}
