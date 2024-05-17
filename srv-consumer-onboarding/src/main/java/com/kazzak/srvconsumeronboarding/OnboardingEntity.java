package com.kazzak.srvconsumeronboarding;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "onboarding")
@Entity
public class OnboardingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String cpf;
    private String email;
}
