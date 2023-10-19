package shepherd.birdup.data;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shepherd.birdup.TestHelper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ProfileRepositoryTest {

    @Autowired
    ProfileJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    TestHelper th = new TestHelper();

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

}
