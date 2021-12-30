package agh.ics.oop.gui;

import agh.ics.oop.Animal;
import agh.ics.oop.IWorldMapElement;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class GuiElementBox {
    private final VBox vBox;

    public GuiElementBox(IWorldMapElement mapElement, double width, double height){
        vBox = new VBox();

        if (mapElement instanceof Animal){
            Circle c = new Circle();
            c.setRadius(Math.min(width, height)/3);
            c.setFill(Color.rgb(Math.min(255,mapElement.getEnergy()),20,20));
            vBox.getChildren().add(c);
        }
        else{
            Rectangle c = new Rectangle(width/2, height/2, Color.rgb(21,138,56));
            vBox.getChildren().add(c);
        }

        vBox.setAlignment(Pos.CENTER);
        vBox.setFillWidth(true);

    }

    public VBox getVBox(){
        return vBox;
    }
}
