package model;

import controller.GameWindowController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class PeaBullet extends GameElements {

    private int plantPos;
    private Timeline soar;
    private Zombie target;

    public PeaBullet(int x, int y, int plantPos, Zombie target ) {
        super(x, y, "resource/image/Pea.png", 20, 20);
        this.plantPos = plantPos;
        this.target = target;
    }

    public void drawImage(Pane pane){
        super.drawImage(pane);
    }

    public void shoot(){
        soar = new Timeline(new KeyFrame(Duration.millis(3), event -> {
            if(getX() <= 1050) setX(getX() + 1);
            if(getX() <= plantPos) img.setVisible(false);
            else img.setVisible(true);
            collideZombie();
        }));
        soar.setCycleCount(Timeline.INDEFINITE);
        soar.play();

        GameWindowController.animationTimelines.add(soar);
    }

    public void collideZombie(){
        if(getX() >= target.getX()) {
            target.setHealthPoint(target.getHealthPoint() - 1);
            soar.stop();
            img.setVisible(false);
            img.setDisable(true);
        }
    }
}
