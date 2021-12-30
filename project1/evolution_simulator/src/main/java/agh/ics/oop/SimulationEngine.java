package agh.ics.oop;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class SimulationEngine implements Runnable, IEngine, IAnimalObserver{
    private final AbstractWorldMap map;
    private final List<Animal> animals = new LinkedList<>();
    private final int moveEnergy;
    private final int plantEnergy;
    private final int startEnergy;
    private final boolean isMagic;
    private int day;
    private int deadNum;
    private float sumDeadLifeTime;
    private boolean suspendFlag = false;
    List<ISimulationObserver> observers = new LinkedList<>();


    public SimulationEngine(AbstractWorldMap map, int initialPopulation, int initialGrassNumber, int startEnergy, int moveEnergy, int plantEnergy, boolean isMagic){
        this.day = 0;
        this.deadNum = 0;
        this.isMagic = isMagic;
        this.map = map;
        this.moveEnergy = moveEnergy;
        this.plantEnergy = plantEnergy;
        this.startEnergy = startEnergy;
        Random random = new Random();
        int[] initdna = new int[32];
        for(int i=0; i < initialPopulation; i++){
            int x = random.nextInt(map.getUpperRightCorner().x + 1);
            int y = random.nextInt(map.getUpperRightCorner().y + 1);

            Vector2d position = new Vector2d(x, y);
            for(int j = 0; j < 32; j++){
                initdna[j] = random.nextInt(8);
            }
            Animal animal = new Animal(map, position, startEnergy, moveEnergy, initdna);
            map.place(animal);
            animals.add(animal);
            animal.addObserver(this);
        }
        for(int i=0; i < initialGrassNumber; i++){
            map.plantGrass(map.lowerLeftCorner, map.upperRightCorner, plantEnergy);
        }
    }

    public void run(){
        int revivalCounter = 0;
        while(animals.size() > 0){
            day ++;
            System.out.println(day);
            for(ISimulationObserver observer : observers){
                float sumEnergy = 0;
                float sumChildNum = 0;
                for (Animal animal : animals){
                    sumEnergy += animal.getEnergy();
                    sumChildNum += animal.getChildNumber();
                }
                observer.cyclePassed(map, day, animals.size(), map.getGrassNumber(), sumEnergy/animals.size(), (deadNum>0) ? sumDeadLifeTime/deadNum : 0, sumChildNum/animals.size(), geneMode());
            }

            if(isMagic && animals.size() == 5 && revivalCounter < 3){
                magicRevival();
                revivalCounter++;
                for(ISimulationObserver observer : observers){
                    observer.revival(revivalCounter, map);
                }
            }

            theGrimReaper();
            moveAll();
            map.feast();
            map.mating(startEnergy/2);
            map.plantGrass(map.lowerLeftCorner, map.upperRightCorner, plantEnergy);
            map.plantGrass(map.jungleLowerLeftCorner, map.jungleUpperRightCorner, plantEnergy);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for(ISimulationObserver observer : observers){
            observer.gameOver(day, map);
        }
    }

    private List<Integer> geneMode(){
        List<Integer> resultList = new LinkedList<>();
        int[] genesNumber = new int[8];

        for (Animal animal : animals){
            for (int gene : animal.getModeGenes()){
                genesNumber[gene]++;
            }
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

    private void magicRevival(){
        List<Animal> clonedAnimals = new LinkedList<>();
        for (Animal animal : animals){
            Random random = new Random();
            while (animals.size() < (map.upperRightCorner.x-map.lowerLeftCorner.x)*(map.upperRightCorner.y-map.lowerLeftCorner.y)){
                int x = random.nextInt(map.upperRightCorner.x - map.lowerLeftCorner.x + 1) + map.lowerLeftCorner.x ;
                int y = random.nextInt(map.upperRightCorner.y - map.lowerLeftCorner.y + 1) + map.lowerLeftCorner.y;

                Vector2d position = new Vector2d(x, y);

                if (!(map.objectAt(position) instanceof Animal)){
                    Animal clone = new Animal(map, position, startEnergy, moveEnergy, animal.getDna());
                    map.place(clone);
                    clone.addObserver(this);
                    clonedAnimals.add(clone);
                    break;
                }
            }
        }
        animals.addAll(clonedAnimals);
    }

    private void theGrimReaper(){
        List<Animal> spirits = new LinkedList<>();

        for (Animal animal : animals){
            if (animal.getEnergy() <= 0){
                spirits.add(animal);
            }
        }

        for (Animal spirit : spirits){
            sumDeadLifeTime += spirit.getAge();
            animals.remove(spirit);
            spirit.setDeathDay(day);
            map.checkPulse(spirit, spirit.getPosition());
        }
        deadNum += spirits.size();
    }

    private void moveAll(){
        for (Animal animal : animals){
            animal.move();
        }
    }

    public void addObserver(ISimulationObserver observer){
        observers.add(observer);
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal mapElement) {

    }

    @Override
    public void birth(Animal newAnimal) {
        animals.add(newAnimal);
        newAnimal.addObserver(this);
    }

    public int getDay() {
        return day;
    }

    public boolean isMagic() {
        return isMagic;
    }
}
