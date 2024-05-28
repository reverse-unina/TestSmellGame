package com.dariotintore.tesi.userservice.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

/**
 * The type User.
 */
@Entity
@Data
@Builder
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid", unique = true, updatable = false)
    @NotNull
    private Long userId;

    @Column(name = "email", unique = true)
    @NotNull
    private String email;

    @Column(name = "username", unique = true)
    @NotNull
    private String userName;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "password")
    private String password;

    @Column(name = "creation_timestamp")
    @CreationTimestamp
    private Date creationTimestamp;

    @Column(name = "modification_timestamp")
    @LastModifiedDate
    private Date modificationTimestamp;
}

