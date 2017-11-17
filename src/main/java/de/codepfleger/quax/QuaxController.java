/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2017.
 */

package de.codepfleger.quax;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuaxController {
    private SimpleGraphiteClient simpleGraphiteClient;

    public QuaxController() {
        simpleGraphiteClient = new SimpleGraphiteClient(System.getProperty("CARBON_HOST", "localhost"), Integer.parseInt(System.getProperty("CARBON_HOST", "2003")));
    }

    @PostMapping("/ingest")
    public ResponseEntity<Boolean> ingest(@RequestParam("metric") String metric, @RequestParam("value") Double value, @RequestParam(value = "timestamp", required = false) String timestamp) {
        long time = timestamp == null ? System.currentTimeMillis() : Long.parseLong(timestamp);
        String datagram = metric + " " + value + " " + time;
        System.out.println(datagram);

        try {
            simpleGraphiteClient.sendMetric(metric, value, time);
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(Boolean.FALSE);
        }

        return ResponseEntity.ok(Boolean.TRUE);
    }
}