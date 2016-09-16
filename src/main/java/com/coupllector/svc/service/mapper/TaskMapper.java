package com.coupllector.svc.service.mapper;

import com.coupllector.svc.domain.*;
import com.coupllector.svc.service.dto.TaskDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Task and its DTO TaskDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TaskMapper {

    TaskDTO taskToTaskDTO(Task task);

    List<TaskDTO> tasksToTaskDTOs(List<Task> tasks);

    Task taskDTOToTask(TaskDTO taskDTO);

    List<Task> taskDTOsToTasks(List<TaskDTO> taskDTOs);
}
