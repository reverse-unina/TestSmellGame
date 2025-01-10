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
import java.util.List;

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

    @Column(name = "exp")
    private int exp;

    @Column(name = "creation_timestamp")
    @CreationTimestamp
    private Date creationTimestamp;

    @Column(name = "modification_timestamp")
    @LastModifiedDate
    private Date modificationTimestamp;

    @ElementCollection
    @CollectionTable(name = "missions_status", joinColumns = @JoinColumn(name = "userid"))
    private List<MissionStatus> missions;

    public void updateMissionStatus(MissionStatus missionStatus) {
        for (MissionStatus mission : missions) {
            if (mission.getMissionId().equals(missionStatus.getMissionId())) {
                mission.setSteps(missionStatus.getSteps());
                return;
            }
        }

        missions.add(missionStatus);
    }
}
