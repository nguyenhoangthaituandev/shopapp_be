package com.company.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="tokens")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Token {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="token", length=225)
    private String token;

    @Column(name="token_type", length=50)
    private String tokenType;

    @Column(name="expiration_date")
    private LocalDateTime expirationDate;

    private Boolean revoked;

    private Boolean expired;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
}
