package com.om.application.product.service;

import com.om.application.product.entity.Product;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
@Service
public class JobService {
    private final Map<String, String> jobStatusMap = new HashMap<>();
    private final ProductService productService;

    public JobService(ProductService productService) {
        this.productService = productService;
    }

    @Async
    public String processFileAsync(MultipartFile file) {
        String jobId = UUID.randomUUID().toString();

        // Save file or initiate processing asynchronously
        // For simplicity, we just save the file name in the job status map
        jobStatusMap.put(jobId, "pending");

        // Simulate processing time (replace with actual processing logic)
        try {
           // List<Product> products = parseFile(file);
            productService.parseFile(file);
            jobStatusMap.put(jobId, "completed");
        } catch (Exception e) {
            jobStatusMap.put(jobId, "failed");
        }

        return jobId;
    }

    public String getJobStatus(String jobId) {
        return jobStatusMap.getOrDefault(jobId, "not found");
    }



}
