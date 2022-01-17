package agh.ics.oop;

public interface IAnimalObserver {
    void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal mapElement);
    void birth(Animal newAnimal);   // może animalBorn?
}
