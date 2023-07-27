package GH.intermission.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    private String email;
    private String password;
    private String name;
    private String gender;
    private String phoneNumber;
    private int age;
    private Boolean isMarried;
    private String residenceType;
    private Boolean isRaisePet;
    private String kindOfPet;
    private String residenceArea;
    private String interviewPossibleArea;
    private String interviewReward;
    private String oftenUsingService;
    private String hobby;
    private String recommendWho;
    private int userPoint;
    private Boolean isAgree;
    private String accountNumber;
    @CreatedDate
    private LocalDateTime createdTime;
    private int recommendSum;
    private Boolean verified;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();
/*
    public Member() {
        roles.add(new Role("ROLE_USER"));
    }*/
}
