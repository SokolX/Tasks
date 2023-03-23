package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaskMapperTestSuite {

    private TaskMapper taskMapper = new TaskMapper();

    @Test
    void shouldReturnTaskFromMap() {
        //Given &When
        Task resultTask = taskMapper.mapToTask(new TaskDto(1L, "testTaskDtoTitle", "testTaskDtoContent"));

        //Then
        assertEquals(1L, resultTask.getId());
        assertEquals("testTaskDtoTitle", resultTask.getTitle());
        assertEquals("testTaskDtoContent", resultTask.getContent());
    }

    @Test
    void shouldReturnTaskDtoFromMap() {
        //Given & When
        TaskDto resultTaskDto = taskMapper.mapToTaskDto(new Task(1L, "testTaskTitle", "testTaskContent"));

        //Then
        assertEquals(1L, resultTaskDto.getId());
        assertEquals("testTaskTitle", resultTaskDto.getTitle());
        assertEquals("testTaskContent", resultTaskDto.getContent());
    }

    @Test
    void shouldReturnTaskDtoListFromFromMap() {
        //Given & When
        List<TaskDto> resultTaskDtoList = taskMapper.mapToTaskDtoList(
                List.of(
                        new Task(1L, "taskTitle", "TaskContent"),
                        new Task(2L, "taskTitle2", "TaskConent2")
                ));

        //Then
        assertEquals(2, resultTaskDtoList.size());
        assertEquals(1L, resultTaskDtoList.get(0).getId());
        assertEquals("TaskConent2", resultTaskDtoList.get(1).getContent());
    }

    @Test
    void shouldReturnEmptyTaskDtoListFromFromMap() {
        //Given & When
        List<TaskDto> resultTaskDtoList = taskMapper.mapToTaskDtoList(List.of());

        //Then
        assertEquals(0, resultTaskDtoList.size());
    }
}