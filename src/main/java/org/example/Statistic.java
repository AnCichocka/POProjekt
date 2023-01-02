package org.example;

import java.lang.reflect.Array;
import java.util.*;

public class Statistic{
//    liczby wszystkich zwierząt,
//    liczby wszystkich roślin,
//    liczby wolnych pól,
//    najpopularniejszych genotypów,
//    średniego poziomu energii dla żyjących zwierząt,
//    średniej długości życia zwierząt dla martwych zwierząt (wartość uwzględnia wszystkie nieżyjące zwierzęta - od początku symulacji),
//    Po zatrzymaniu programu powinno być też możliwe pokazanie, które ze zwierząt mają dominujący (najpopularniejszy) genotyp (np. poprzez wyróżnienie ich wizualnie).


    private int theMostPopularGenotype;
    private int noDeadAnimals;
    private int deadAnimalsAgeSum;

    private SimulationEngine engine;
    private IMap map;

    private int[] mostPopularGenotype;


    public Statistic(IMap map, SimulationEngine engine){
        this.engine = engine;
        this.map = map;
        this.noDeadAnimals = 0;
        this.deadAnimalsAgeSum = 0;

    }

    public int getNoAnimals(){
        return engine.getAnimals().size();
    }

    public int getNoPlants(){
        return map.getPlants().size();
    }

    public int getNoFreeFields(){
        return map.getNoFreeFields();
    }

    public int getAvgEnergy(){
        int sumEnergy = 0;

        for(Animal animal : engine.getAnimals()){
            sumEnergy += animal.getEnergy();
        }

        return sumEnergy/this.getNoAnimals();
    }

    public double getAvgDeathAge(){
        return (double)this.deadAnimalsAgeSum/this.noDeadAnimals;
    }

    public String getTheMostPopularGenotype(){
        HashMap<int[], Integer> genotypesCounter = new HashMap<>();
        for(Animal animal : engine.getAnimals()){
            int[] genotype = animal.getGenotype();
            if(genotypesCounter.containsKey(genotype)){
                genotypesCounter.put(genotype, genotypesCounter.get(genotype) + 1);
            }else{
                genotypesCounter.put(genotype, 1);
            }
        }

        Map.Entry<int[], Integer> mostPopular = null;

        for(Map.Entry<int[], Integer> e : genotypesCounter.entrySet()){
            if (mostPopular == null || e.getValue() > mostPopular.getValue())
                mostPopular = e;
        }

        if(mostPopular != null){
            this.mostPopularGenotype = mostPopular.getKey();
            return Arrays.toString(this.mostPopularGenotype);
        }else{
            this.mostPopularGenotype = null;
            return null;
        }
    }

    public ArrayList<Animal> getAnimalsWithMostPopular(){
        ArrayList<Animal> popularGenotypeAnimals = new ArrayList<>();

        for(Animal animal : engine.getAnimals()){
            if(animal.getGenotype().equals(this.mostPopularGenotype))
            popularGenotypeAnimals.add(animal);
        }

        return popularGenotypeAnimals;
    }

    public void isDead(Animal animal){
        this.deadAnimalsAgeSum += animal.getAge();
        this.noDeadAnimals ++;
    }


}