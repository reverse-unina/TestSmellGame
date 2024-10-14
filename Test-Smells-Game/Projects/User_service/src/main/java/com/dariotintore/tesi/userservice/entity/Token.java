package com.dariotintore.tesi.userservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * The type Token.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "activetokens")
public class Token {
    @Id
    @Column(name = "tokenid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenid;
    @Column(name = "email")
    private String email;
    @Column(name = "token")
    private String token;
}
