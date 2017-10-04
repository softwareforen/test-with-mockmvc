package de.softwareforen.todoExample.todo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StreamUtils;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class TodoControllerTest {

    private static final String EXPECTED_SUBJECT = "subject";
    private static final String EXPECTED_DESCRIPTION = "test_description";

    public static final String EXPECTED_JSON_SUBJECT = "Unit tests schreiben!";
    public static final String EXPECTED_JSON_DESCRIPTION = "Beschreibung hier! öüäÜÄ?ß";

    @Value("classpath:todo/TodoItem.json")
    private Resource json;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoRepository todoRepository;

    private String requestURL = "/todos";

    @Captor
    private ArgumentCaptor<TodoEntity> todoCaptor;
    private long todoItemId = 5;

    @Before
    public void setup()
    {
        List<TodoEntity> todoList = getTodoItems();
        when(todoRepository.findAll()).thenReturn(todoList);

        when(todoRepository.findById(any(long.class))).thenReturn(todoList.get(0));

        when(todoRepository.save(any(TodoEntity.class))).thenAnswer((Answer<TodoEntity>) invocationOnMock -> {
            TodoEntity arg = invocationOnMock.getArgumentAt(0, TodoEntity.class);
            arg.setId(todoItemId);
            return arg;
        });
    }

    @After
    public void createTodoItem() throws Exception {
        todoRepository.deleteAll();
    }

    @Test
    public void findToDoEntities() throws Exception {
        mockMvc.perform(get(requestURL)).andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(1))).andExpect(jsonPath("$[0].subject", is(EXPECTED_SUBJECT)))
            .andExpect(jsonPath("$[0].description", is(EXPECTED_DESCRIPTION)));
    }

    @Test
    public void findToDoEntitiesById() throws Exception {
        mockMvc.perform(get(requestURL + "/" + todoItemId)).andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.subject", is(EXPECTED_SUBJECT)))
            .andExpect(jsonPath("$.description", is(EXPECTED_DESCRIPTION)));
    }

    @Test
    public void createToDoEntity_Null() throws Exception {
        mockMvc.perform(post(requestURL)).andExpect(status().isBadRequest());

        verify(todoRepository, never()).save(any(TodoEntity.class));
    }

    @Test
    public void createToDoEntity() throws Exception {
        String content = StreamUtils.copyToString(json.getInputStream(), Charset.forName("UTF-8"));
        mockMvc.perform(post(requestURL).contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isCreated())
            .andExpect(header().string("location", containsString(requestURL)));

        verify(todoRepository).save(todoCaptor.capture());
        TodoEntity savedEntity = todoCaptor.getValue();

        assertThat(savedEntity.getSubject(), is(EXPECTED_JSON_SUBJECT));
        assertThat(savedEntity.getDescription(), is(EXPECTED_JSON_DESCRIPTION));
    }

    public List<TodoEntity> getTodoItems() {
        List<TodoEntity> list = new ArrayList<>();
        TodoEntity todo1 = TodoEntity.builder().subject(EXPECTED_SUBJECT).description(EXPECTED_DESCRIPTION).build();
        list.add(todo1);

        return list;
    }
}