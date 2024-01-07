package agh.ics.oop.model.classes;

public class AnimalStats {
    private int plantsConsumed = 0;
    private int numOfChildren = 0;
    private int numOfDescendants = 0;
    private int age = 0;
    private int deathDay = 0;

    public AnimalStats() {
    }

    public void increasePlantsConsumed() {
        plantsConsumed++;
    }

    public void increaseChildren() {
        numOfChildren++;
    }

    public void increaseDescendants() {
        numOfDescendants++;
    }

    public void increaseAge() {
        age++;
    }

    public int getPlantsConsumed() {
        return plantsConsumed;
    }

    public int getNumOfChildren() {
        return numOfChildren;
    }

    public int getNumOfDescendants() {
        return numOfDescendants;
    }

    public int getAge() {
        return age;
    }

    public int getDeathDay() {
        return deathDay;
    }

    public void setDead(int daySinceSimulationStarted) {
        deathDay = daySinceSimulationStarted;
    }
}
