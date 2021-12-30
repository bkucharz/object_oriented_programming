package agh.ics.oop;

import java.util.*;
import java.util.stream.IntStream;

public class Animal implements IWorldMapElement{
    private Vector2d position;
    private int energy;
    private final int[] dna;
    private MapDirection orientation;
    private final IWorldMap map;
    private final List<IAnimalObserver> observers = new ArrayList<>();
    private int age = 0;
    private int childNumber = 0;
    private final int dailyFatigue;
    private final List<Integer> modeGenes;
    private int deathDay = -1;

    public Animal(IWorldMap map, Vector2d initialPosition, int initialEnergy, int dailyFatigue, int[] dna){
        this.dna = Arrays.copyOf(dna, 32);
        this.modeGenes = getMode(dna);
        this.dailyFatigue = dailyFatigue;
        this.map = map;
        this.position = initialPosition;
        this.energy = initialEnergy;
        this.orientation = MapDirection.values()[new Random().nextInt(MapDirection.values().length)];
    }


    private List<Integer> getMode(int[] dna){
        List<Integer> resultList = new LinkedList<>();
        int[] genesNumber = new int[8];
        for (int gene : dna){
            genesNumber[gene]++;
        }
        int maxNumber = genesNumber[0];
        for (int geneNumber : genesNumber){
            maxNumber = Math.max(maxNumber, geneNumber);
        }
        for (int gene = 0; gene < 8; gene++){
            if (genesNumber[gene] == maxNumber){
                resultList.add(gene);
            }
        }
        return resultList;
    }


    public String toString(){
        return switch (orientation){
            case NORTH -> "^";
            case NORTH_EAST -> "ne";
            case EAST -> ">";
            case SOUTH_EAST -> "se";
            case SOUTH -> "v";
            case SOUTH_WEST -> "sw";
            case WEST -> "<";
            case NORTH_WEST -> "nw";
        };
    }

    public void move(){
        Random random = new Random();
        MoveDirection direction = new OptionsParser().parse(dna[random.nextInt(32)]);

        orientation = switch (direction){
            case SLIGHT_RIGHT -> orientation.next();
            case RIGHT-> orientation.next().next();
            case SHARP_RIGHT -> orientation.next().next().next();
            case SLIGHT_LEFT -> orientation.previous();
            case LEFT -> orientation.previous().previous();
            case SHARP_LEFT -> orientation.previous().previous().previous();
            default -> orientation;
        };

        Vector2d oldPosition = position;
        Vector2d updatedPosition = switch (direction){
            case FORWARD -> position.add(orientation.toUnitVector());
            case BACKWARD -> position.subtract(orientation.toUnitVector());
            default -> position;
        };

        updatedPosition = map.onMapPosition(updatedPosition);
        if(map.canMoveTo(updatedPosition)){
            position = updatedPosition;
            positionChanged(oldPosition, updatedPosition);
        }
        else if(direction == MoveDirection.LEFT || direction == MoveDirection.RIGHT){
            positionChanged(oldPosition, oldPosition);
        }

        reduceEnergy(dailyFatigue);
        age++;
    }

    public void addObserver(IAnimalObserver observer){
        observers.add(observer);
    }

    public void removeObserver(IAnimalObserver observer){
        observers.remove(observer);
    }

    private void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        for(IAnimalObserver observer : observers){
            observer.positionChanged(oldPosition, newPosition, this);
        }
    }


    public Animal procreate(Animal other){
        Animal alfa;
        Animal beta;
        if(this.energy > other.energy){
            alfa = this;
            beta = other;
        }
        else{
            alfa = other;
            beta = this;
        }

        int alfaGens = 32* alfa.energy/(alfa.energy + beta.energy);
        int betaGens = 32 - alfaGens;

        Random random = new Random();
        int side = random.nextInt(2);
        int[] head;
        int[] tail;
        if (side == 0){
            head = Arrays.copyOfRange(alfa.dna, 0, alfaGens);
            tail = Arrays.copyOfRange(beta.dna, alfaGens, 32);
        }
        else{
            head = Arrays.copyOfRange(beta.dna, 0, betaGens);
            tail = Arrays.copyOfRange(alfa.dna, betaGens, 32);
        }

        int[] newDna;
        newDna = IntStream.concat(Arrays.stream(head), Arrays.stream(tail)).toArray();
        int givenEnergy = (this.energy+other.energy)/4;
        this.reduceEnergy(this.energy/4);
        other.reduceEnergy(other.energy/4);
        Animal child = new Animal(this.map, this.position, givenEnergy, dailyFatigue, newDna);
        for (IAnimalObserver observer : observers){
            observer.birth(child);
        }
        childNumber++;
        return child;
    }

    public void eat(Grass grass){
        this.increaseEnergy(grass.getEnergy());
    }

    private void reduceEnergy(int damage) {
        this.energy -= damage;
    }

    private void increaseEnergy(int EnergyDose){
        this.energy += EnergyDose;
    }

    public int[] getDna(){
        return Arrays.copyOf(dna, dna.length);
    }

    public int getEnergy(){
        return energy;
    }

    public int getChildNumber() {
        return childNumber;
    }

    public int getAge() {
        return age;
    }

    public List<Integer> getModeGenes() {
        return modeGenes;
    }

    public void setDeathDay(int deathDay) {
        this.deathDay = deathDay;
    }

    public int getDeathDay() {
        return deathDay;
    }

    public Vector2d getPosition() {
        return position;
    }

    public MapDirection getOrientation(){
        return orientation;
    }
}
