package com.company.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="products")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 200, nullable = false)
    private String name;

    private Float price;

    @Column(name = "thumbnail", length = 300)
    private String thumbnail;

    private String description;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;


}
