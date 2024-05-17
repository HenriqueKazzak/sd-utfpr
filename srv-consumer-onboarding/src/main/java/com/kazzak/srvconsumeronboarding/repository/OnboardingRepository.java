package com.kazzak.srvconsumeronboarding.repository;

import com.kazzak.srvconsumeronboarding.OnboardingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OnboardingRepository extends JpaRepository<OnboardingEntity, Long> {
}
