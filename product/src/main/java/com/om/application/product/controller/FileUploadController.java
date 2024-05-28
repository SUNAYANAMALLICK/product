package com.om.application.product.controller;

import com.om.application.product.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
class FileUploadController {

    @Autowired
    private final JobService jobService;

    public FileUploadController(JobService jobService) {
        this.jobService = jobService;
    }


    @GetMapping("/status/{jobId}")
    public ResponseEntity<String> getJobStatus(@PathVariable String jobId) {
        String status = jobService.getJobStatus(jobId);
        return ResponseEntity.ok(status);
    }
}