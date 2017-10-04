package de.softwareforen.todoExample;

import de.softwareforen.todoExample.todo.TodoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoTestJunitApplicationTests {

    /**
     * Da wir für das Interface keine Implementierung haben, brauchen wir ein Mock da sonst das Autowiring fehlschlägt.
     */
    @MockBean
    private TodoRepository todoRepository;

    @Test
    public void contextLoads() {
    }

}
