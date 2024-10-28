package com.todayyum;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = TodayYumApplicationTests.class)
@ActiveProfiles("local")
class TodayYumApplicationTests {

    @Test
    void contextLoads() {
    }

}
