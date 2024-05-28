package com.om.application.product.entity;

import com.opencsv.bean.CsvBindByName;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

//import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CsvBindByName(column = "name")
    private String name;
    @CsvBindByName(column = "description")

    private String description;
    @CsvBindByName(column = "price")

    private BigDecimal price;
    @CsvBindByName(column = "currency")

    private String currency;


    @ElementCollection
    private Set<String> tags;

    @CsvBindByName(column = "quantity")

    private int quantity;
    @CsvBindByName(column = "warehouseLocation")

    private String warehouseLocation;

    @CsvBindByName(column = "createdAt")

    private LocalDateTime createdAt;
    @CsvBindByName(column = "updatedAt")

    private LocalDateTime updatedAt;

    // Getters and setters
}
