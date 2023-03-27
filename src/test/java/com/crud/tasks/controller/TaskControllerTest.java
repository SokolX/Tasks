package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {

    private static final String URI_TASKS_TEMPLATE = "/v1/tasks/";
    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private DbService service;

    @Test
    public void shouldGetAllTasks() throws Exception {
        //Given
        Task task1 = new Task(1L,"test-title1", "Test-content1");
        Task task2 = new Task(2L,"test-title2", "Test-content2");
        service.saveTask(task1);
        service.saveTask(task2);
        List<Task> taskList = service.getAllTasks();
        Long task1Id = taskList.get(0).getId();
        Long task2Id = taskList.get(1).getId();

        //When & Then
        mockMvc.
                perform(MockMvcRequestBuilders
                        .get(URI_TASKS_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", Matchers.is("test-title1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content", Matchers.is("Test-content1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title", Matchers.is("test-title2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].content", Matchers.is("Test-content2")));

        //CleanUp
        service.deleteTask(task1Id);
        service.deleteTask(task2Id);
    }

    @Test
    public void shouldAddTask() throws Exception {
        //Given
        Task task1 = new Task(1L,"test-title1", "Test-content1");

        Gson gson = new Gson();
        String jsonContent = gson.toJson(task1);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(URI_TASKS_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200));

        //CleanUp
        List<Task> taskList = service.getAllTasks();
        Long task1Id = taskList.get(0).getId();
        service.deleteTask(task1Id);
    }

    @Test
    public void shouldGetTask() throws Exception {
        //Given
        Task task1 = new Task(1L,"test-title1", "Test-content1");
        Task task2 = new Task(2L,"test-title2", "Test-content2");
        service.saveTask(task1);
        service.saveTask(task2);
        List<Task> taskList = service.getAllTasks();
        Long task1Id = taskList.get(0).getId();
        Long task2Id = taskList.get(1).getId();

        //When & Then
        mockMvc.
                perform(MockMvcRequestBuilders
                        .get(URI_TASKS_TEMPLATE + task1Id.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("test-title1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("Test-content1")));

        //CleanUp
        service.deleteTask(task1Id);
        service.deleteTask(task2Id);
    }

    @Test
    public void shouldDeleteTaskTest() throws Exception {
        //Given
        Task task1 = new Task(1L,"test-title1", "Test-content1");
        service.saveTask(task1);
        List<Task> taskList = service.getAllTasks();
        Long task1Id = taskList.get(0).getId();

        //When & Then
        mockMvc.
                perform(MockMvcRequestBuilders
                        .delete(URI_TASKS_TEMPLATE + task1Id.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));

        taskList = service.getAllTasks();
        assertEquals(0, taskList.size());
    }

    @Test
    public void shouldUpdateTaskTest() throws Exception {
        //Given
        Task task1 = new Task(1L,"test-title1", "Test-content1");
        service.saveTask(task1);
        List<Task> taskList = service.getAllTasks();
        Long task1Id = taskList.get(0).getId();
        task1 = new Task(task1Id, "test1-update", "Test1-update");

        Gson gson = new Gson();
        String jsonContent = gson.toJson(task1);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put(URI_TASKS_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200));

        Task updatedTask = service.getTask(task1Id);
        assertEquals("test1-update", updatedTask.getTitle());
        assertEquals("Test1-update", updatedTask.getContent());

        //CleanUp
        service.deleteTask(task1Id);
    }
}