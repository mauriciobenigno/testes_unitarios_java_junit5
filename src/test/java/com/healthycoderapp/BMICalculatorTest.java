package com.healthycoderapp;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BMICalculatorTest {

    private String environmment = "dev";

    @BeforeAll
    static void beforeAll() {
        System.out.println("Antes de todos os testes");
    }

    @AfterAll
    static void afterAll(){
        System.out.println("Depois de todos os testes");
    }

    @Nested
    class IsDietRecommendedTests {
        @Test
        void retorna_Verdadeiro_Caso_DietaRecomendada1(){
            // given
            double weight = 89.0;
            double height = 1.72;

            //when
            boolean recomendado = BMICalculator.isDietRecommended(weight, height);

            // then
            assertTrue(recomendado);
        }

        @ParameterizedTest
        @ValueSource(doubles = {89.0, 95.0, 110.0})
        void retorna_Verdadeiro_Caso_DietaRecomendada2(Double coderWeight){
            // given
            double weight = coderWeight;
            double height = 1.72;

            //when
            boolean recomendado = BMICalculator.isDietRecommended(weight, height);

            // then
            assertTrue(recomendado);
        }

        @ParameterizedTest
        @CsvSource(value = {"89.0, 1.72", "95.0, 1.75", "110.0, 1.78"})
        void retorna_Verdadeiro_Caso_DietaRecomendada3(Double coderWeight, Double coderHeight){
            // given
            double weight = coderWeight;
            double height = coderHeight;

            //when
            boolean recomendado = BMICalculator.isDietRecommended(weight, height);

            // then
            assertTrue(recomendado);
        }

        @ParameterizedTest(name = "weight={0}, height={1}")
        @CsvSource(value = {"89.0, 1.72", "95.0, 1.75", "110.0, 1.78"})
        void retorna_Verdadeiro_Caso_DietaRecomendada4(Double coderWeight, Double coderHeight){
            // given
            double weight = coderWeight;
            double height = coderHeight;

            //when
            boolean recomendado = BMICalculator.isDietRecommended(weight, height);

            // then
            assertTrue(recomendado);
        }

        @ParameterizedTest(name = "weight={0}, height={1}")
        @CsvFileSource(resources = "/diet-recommended-input-data.csv", numLinesToSkip = 1)
        void retorna_Verdadeiro_Caso_DietaRecomendada5(Double coderWeight, Double coderHeight){
            // given
            double weight = coderWeight;
            double height = coderHeight;

            //when
            boolean recomendado = BMICalculator.isDietRecommended(weight, height);

            // then
            assertTrue(recomendado);
        }

        @RepeatedTest(value = 10, name = RepeatedTest.LONG_DISPLAY_NAME)
        void retorna_Verdadeiro_Caso_DietaRecomendada6(){
            // given
            double weight = 89.0;
            double height = 1.72;

            //when
            boolean recomendado = BMICalculator.isDietRecommended(weight, height);

            // then
            assertTrue(recomendado);
        }

        @Test
        void retorna_Falso_Caso_DietaNapRecomendada(){
            // given
            double weight = 50.0;
            double height = 1.92;

            //when
            boolean recomendado = BMICalculator.isDietRecommended(weight, height);

            // then
            assertFalse(recomendado);
        }

        @Test
        void retorna_ExcecaoAritimetica_Caso_HeightZero(){
            // given
            double weight = 50;
            double height = 0.0;

            //when
            Executable executable = () -> BMICalculator.isDietRecommended(weight, height);

            // then
            assertThrows(ArithmeticException.class, executable);
        }
    }

    @Nested
    @DisplayName("Testes de BMI")
    class FindCoderWithWorstBMITests{
        @Test
        @DisplayName("Primeiro teste")
        void deve_Retornar_OPiorBMI_Quando_AListaDeCodersNaoForVazia(){
            // given
            List<Coder> coders = new ArrayList<>();
            coders.add(new Coder(1.80, 60.0));
            coders.add(new Coder(1.82, 98.0));
            coders.add(new Coder(1.82, 64.7));

            // when

            Coder piorCoderBMI = BMICalculator.findCoderWithWorstBMI(coders);

            // then
            assertAll(
                    () -> assertEquals(1.82, piorCoderBMI.getHeight()),
                    () -> assertEquals(98.0, piorCoderBMI.getWeight())
            );
        }

        @Test
        @DisplayName("Segundo teste")
        @Disabled
        void deve_Retornar_OPiorBMI_Em1Ms_QuandoAListaTem10000Elementos(){
            // given


            List<Coder> coders = new ArrayList<>();
            for(int i = 0; i< 10000; i++){
                coders.add(new Coder(1.0 + i, 10+i));
            }

            // when

            Coder piorCoderBMI = BMICalculator.findCoderWithWorstBMI(coders);

            Executable executavel = () -> BMICalculator.findCoderWithWorstBMI(coders);

            // then
            assertTimeout(Duration.ofMillis(50), executavel);
        }

        @Test
        void deve_Retornar_OPiorBMI_Quando_AListaDeCodersForVazia(){
            // given
            List<Coder> coders = new ArrayList<>();

            // when
            Coder piorCoderBMI = BMICalculator.findCoderWithWorstBMI(coders);

            // then
            assertNull(piorCoderBMI);
        }
    }


    @Nested
    class GetCodersBMIScores{
        @Test
        void deve_Retornar_ScoreBMICorreto_Quando_AListaDeCodersNaoForVazia(){
            // given
            List<Coder> coders = new ArrayList<>();
            coders.add(new Coder(1.80, 60.0));
            coders.add(new Coder(1.82, 98.0));
            coders.add(new Coder(1.82, 64.7));
            double[] esperado = {18.52, 29.59, 19.53};


            // when
            double[] bmiScore = BMICalculator.getBMIScores(coders);

            // then
            assertArrayEquals(esperado, bmiScore);
        }
    }
}