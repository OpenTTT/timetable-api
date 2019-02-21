package org.openttt.rest;

import org.openttt.services.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("test")
public class TestingController {
    @Autowired
    private BatchService batchService;

    @DeleteMapping("/all")
    public void reset() {
        batchService.deleteAll();
    }
}
