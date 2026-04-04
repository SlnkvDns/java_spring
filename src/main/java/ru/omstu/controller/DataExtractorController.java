package ru.omstu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.omstu.dto.ErrorResponse;
import ru.omstu.dto.ExtractRequest;
import ru.omstu.dto.ExtractResponse;
import ru.omstu.service.DataProcessingService;

@RestController
@RequestMapping("/api/data")
public class DataExtractorController {

    private final DataProcessingService dataProcessingService;

    @Autowired
    public DataExtractorController(DataProcessingService dataProcessingService) {
        this.dataProcessingService = dataProcessingService;
    }

    @PostMapping("/extract")
    public ResponseEntity<?> extract(@RequestBody ExtractRequest request) {
        try {
            String value = dataProcessingService.process(
                    request.getType(),
                    request.getData(),
                    request.getPath()
            );
            return ResponseEntity.ok(new ExtractResponse(value));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }
}
