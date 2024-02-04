package com.seek.appcandidate.domain.model;

import com.seek.appcandidate.domain.enums.EGender;
import com.seek.appcandidate.infrastructure.dto.CreateCandidateDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Data
@Table("candidates")
@AllArgsConstructor
@NoArgsConstructor
public class Candidate {
    @Id
    private int id;
    private String name;
    private String email;
    private EGender gender;
    private String phone;
    private String address;
    private Double salary_expected;
    private LocalDate date_of_birth;
    private Integer experience_years;
    private String education;
    private ZonedDateTime created_at;
    private ZonedDateTime updated_at;

    public void update(CreateCandidateDTO newCandidate) {
        this.name = newCandidate.name();
        this.email = newCandidate.email();
        this.gender = newCandidate.gender();
        this.phone = newCandidate.phone();
        this.address = newCandidate.address();
        this.salary_expected = newCandidate.salary_expected();
        this.experience_years = newCandidate.experience_years();
        this.education = newCandidate.education();
        this.updated_at = ZonedDateTime.now();
    }


}
