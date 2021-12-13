package agh.ics.oop;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import java.io.FileInputStream;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;

public class GuiElementBox{
    private VBox vBox;
    private Label label;

    public GuiElementBox(IWorldMapElement mapElement) throws FileNotFoundException {
        Image image = new Image(new FileInputStream(mapElement.getImagePath()));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);
        label = new Label(mapElement.getPosition().toString());
        vBox = new VBox();
        vBox.getChildren().add(imageView);
        vBox.getChildren().add(label);
        vBox.setAlignment(Pos.CENTER);

    }

    public VBox getVBox(){
        return vBox;
    }

    public void setLabel(Label newLabel){
        label = newLabel;
    }
}
