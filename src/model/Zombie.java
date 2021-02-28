package model;

import controller.GameWindowController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.util.Iterator;

public abstract class Zombie extends GameElements {
    public int healthPoint;
    public int damage;
    public int lane;
    public int x, y;
    public int deltaX = -1;
    public Timeline move;
    public ImageView image;
    public Timeline eat;

    public Zombie(int x, int y, String imagePath, int healthPoint, int damage, int lane, int width, int height) {
        super(x, y, imagePath, width, height);
        this.healthPoint = healthPoint;
        this.damage = damage;
        this.lane = lane;

    }


    public void forward() {
        move = new Timeline(new KeyFrame(Duration.millis(70), e -> {
            checkHealthPoint();
            zombieWalk();
            checkReachedHouse();
        }));
        move.setCycleCount(Timeline.INDEFINITE);
        move.play();
        GameWindowController.animationTimelines.add(move);
    }

    public void zombieWalk() {
        if (getX() > 220) {
            setX(getX() + deltaX);
            eatPlant(detectPlant());
        }
    }

    public Plant detectPlant(){
        synchronized (GameWindowController.allPlants) {
            Iterator<Plant> plants = GameWindowController.allPlants.iterator();
            while (plants.hasNext()) {
                Plant plant = plants.next();
                if (plant.getRow() == lane && (getX() - plant.getX()) <= 1 ){
                    return plant;
                }
            }
        }
        return null;
    }

    public void eatPlant(Plant plant){
        if(plant != null) {
            stop();
            if (plant.getHp() > 0 && getHealthPoint() > 0) {
                eat = new Timeline(new KeyFrame(Duration.millis(100), actionEvent -> {
                    if(!GameWindowController.allPlants.contains(plant)){
                        eat.stop();
                        move();
                    }
                    plant.setHp(plant.getHp() - 1);
                }));
                eat.setCycleCount(Timeline.INDEFINITE);
                eat.play();

                GameWindowController.animationTimelines.add(eat);
            }
        }
    }

    public void stop(){
        deltaX = 0;
    }

    public void move(){
        deltaX = -1;
    }

    public void checkReachedHouse() {
        if (getX() < 220) {
            GameWindowController.endGame();
            String eatingBrainFile = "src/resource/sound/eatingbrain.wav";
            Media eatingBrain = new Media(new File(eatingBrainFile).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(eatingBrain);
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.play();

        }
    }

    public void chompingPlantSound() {
        String chompFile = "src/resource/sound/chomp.wav";
        Media chomp = new Media(new File(chompFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(chomp);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setStartTime(Duration.seconds(0));
        mediaPlayer.setStopTime(Duration.seconds(1));
        mediaPlayer.setCycleCount(1);
        mediaPlayer.play();
    }




    public int getHealthPoint() {
        return healthPoint;
    }

    public int getLane() {
        return lane;
    }

    public void setHealthPoint(int healthPoint) {
        this.healthPoint = healthPoint;
        checkHealthPoint();
    }

    public void checkHealthPoint(){
        if(healthPoint <= 0){
            this.img.setVisible(false);
            this.img.setDisable(true);
            move.stop();
            GameWindowController.allZombies.remove(this);
        }
    }
}
