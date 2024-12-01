package com.company.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="product_images")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductImage {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_url", length = 300)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product productId;

}
