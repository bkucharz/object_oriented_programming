package agh.ics.oop;

import java.util.*;

public abstract class AbstractWorldMap implements IWorldMap, IAnimalObserver {
    protected Vector2d upperRightCorner;
    protected Vector2d lowerLeftCorner;
    protected Vector2d jungleLowerLeftCorner;
    protected Vector2d jungleUpperRightCorner;
    protected Map<Vector2d, List<Animal>> animals = new LinkedHashMap<>();
    private Map<Vector2d, Grass> grasses = new LinkedHashMap<>();

    public AbstractWorldMap(int width, int height, float jungleRatio){
        lowerLeftCorner = new Vector2d(0, 0);
        upperRightCorner = new Vector2d(width - 1, height - 1);
        int jungleWidth = (int) (width*jungleRatio);
        int jungleHeight = (int) (height*jungleRatio);
        int jx0 = (width - jungleWidth)/2;
        int jy0 = (height - jungleHeight)/2;
        jungleLowerLeftCorner = new Vector2d(jx0, jy0);
        jungleUpperRightCorner = new Vector2d(jx0 + jungleWidth, jy0 + jungleHeight);
    }

    public AbstractWorldMap(int width, int height){
        this(width, height, 0.25F);
    }

    protected void putAnimal(Animal animal, Vector2d position){
        if(animals.get(position) == null){
            animals.put(position, new LinkedList<>());
        }
        animals.get(position).add(animal);
    }

    public void feast(){
        for(Vector2d position : animals.keySet()){
            if(grasses.get(position) != null){
                Animal animal = (Animal) objectAt(position);    // a jeśli zwierząt jest kilka?
                animal.eat(grasses.get(position));
                grasses.remove(position);
            }
        }
    }


    public void mating(int minimalEnergy){
        for (List<Animal> positionList : animals.values()){
            if (positionList.size() > 1){
                Animal animal1 = positionList.get(0);
                Animal animal2 = positionList.get(0);
                for (Animal a : positionList){
                    if (a.getEnergy() >= animal1.getEnergy()){
                        animal2 = animal1;
                        animal1 = a;
                    }
                }
                if (animal1.getEnergy() >= minimalEnergy && animal2.getEnergy() >= minimalEnergy)
                {
                    animal1.procreate(animal2);
                }
            }
        }
    }

    public void birth(Animal newAnimal){
        putAnimal(newAnimal, newAnimal.getPosition());
        newAnimal.addObserver(this);
    }

    public void checkPulse(Animal animal, Vector2d position){   // za miesiąc będzie Pan rozumiał tę nazwę? + dlaczego to jest metoda mapy?
        // removing dead animal from given position
        if (animal.getEnergy() <= 0){
            removeAnimal(animal, position);
        }
    }

    protected void removeAnimal(Animal animal, Vector2d position){
        List<Animal> positionList = animals.get(position);
        if(positionList == null || positionList.size()==0) {
            throw new IllegalArgumentException();
        }
        else {
            positionList.remove(animal);
            if (positionList.size() == 0)   animals.remove(position);
        }
    }

    public abstract Vector2d onMapPosition(Vector2d wishedPosition);

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal) {
        removeAnimal(animal, oldPosition);
        putAnimal(animal, newPosition);
    }


    public boolean place(Animal animal){
        if(canMoveTo(animal.getPosition())){
            putAnimal(animal, animal.getPosition());
            animal.addObserver(this);
            return true;
        }
        throw new IllegalArgumentException("Cannot place animal at position "+  animal.getPosition());
    }

    public void plantGrass(Vector2d leftCorner, Vector2d rightCorner, int nutritiousness, Vector2d jungleLeftCorner, Vector2d jungleRightCorner){
        // planting one grass in random place outside of jungle
        Random random = new Random();
        int number_of_tries = 0;
        while (number_of_tries < 3*(rightCorner.x - leftCorner.x)*(rightCorner.y - leftCorner.y)){
            int x = random.nextInt(rightCorner.x - leftCorner.x + 1) + leftCorner.x ;
            int y = random.nextInt(rightCorner.y - leftCorner.y + 1) + leftCorner.y;

            Vector2d position = new Vector2d(x, y);

            if (position.precedes(jungleRightCorner) && position.follows(jungleLeftCorner)){
                continue;
            }

            if (objectAt(position) == null){
                this.grasses.put(position, new Grass(position, nutritiousness));
                break;
            }
            number_of_tries++;
        }
    }


    public void plantGrass(Vector2d leftCorner, Vector2d rightCorner, int nutritiousness){
        // planting one grass in random place inside given area
        plantGrass(leftCorner, rightCorner, nutritiousness, new Vector2d(0,0), new Vector2d(0,0));
    }


    public boolean isOccupied(Vector2d position){
        if(animals.containsKey(position)){
            return true;
        }
        else return grasses.containsKey(position);
    }


    public Object objectAt(Vector2d position){
        if (animals.get(position) != null){
            return Collections.max(animals.get(position), Comparator.comparingInt(Animal::getEnergy));
        }
        return grasses.get(position);
    }

    public Vector2d getUpperRightCorner(){
        return upperRightCorner;
    }

    public Vector2d getLowerLeftCorner(){
        return lowerLeftCorner;
    }

    public Vector2d getJungleLowerLeftCorner() {
        return jungleLowerLeftCorner;
    }

    public Vector2d getJungleUpperRightCorner() {
        return jungleUpperRightCorner;
    }

    public int getGrassNumber() {
        return grasses.size();
    }

}
