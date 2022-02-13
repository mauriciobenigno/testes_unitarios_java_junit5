package com.healthycoderapp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DietPlannerTest {

    private DietPlanner dietPlanner;

    @BeforeEach
    void setup(){
        this.dietPlanner = new DietPlanner(20, 30, 50);
    }

    @AfterEach
    void depoisDeTestar() {
        System.out.println("O teste unitário terminou");
    }

    @Test
    void retorna_PlanoDeDieta_Quando_CoderCorreto(){
        // given
         Coder coder = new Coder(1.82, 75.0, 26, Gender.MALE);
         DietPlan esperado = new DietPlan(2202, 110, 73, 275);

        //when
        DietPlan atual  = dietPlanner.calculateDiet(coder);

        // then
        assertAll(
                () -> assertEquals(esperado.getCalories(), atual.getCalories()),
                () -> assertEquals(esperado.getProtein(), atual.getProtein()),
                () -> assertEquals(esperado.getFat(), atual.getFat()),
                () -> assertEquals(esperado.getCarbohydrate(), atual.getCarbohydrate())
        );
    }
}