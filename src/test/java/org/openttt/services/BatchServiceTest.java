package org.openttt.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openttt.OpenTTTimetablesApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = OpenTTTimetablesApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BatchServiceTest {
    @Autowired
    private BatchService batchService;

    @Test
    public void testDatabaseClearing() {

    }
}
