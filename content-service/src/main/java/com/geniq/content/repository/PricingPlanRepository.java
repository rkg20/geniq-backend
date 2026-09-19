package com.geniq.content.repository;

import com.geniq.content.model.PricingPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PricingPlanRepository extends JpaRepository<PricingPlan, String> {
}
