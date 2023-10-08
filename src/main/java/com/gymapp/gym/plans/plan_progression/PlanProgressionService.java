package com.gymapp.gym.plans.plan_progression;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlanProgressionService {
    @Autowired
    private PlanProgressionRepository repository;

    public PlanProgression getPlanProgressionByUserId(int userId) {
        return repository.getByUserId(userId);
    };

     public void deletePlanProgressionById(int userId) {
         repository.deleteById(userId);
     }

}
