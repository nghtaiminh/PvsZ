package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.HashMap;

public class PlantCards extends GameElements {

    private String name;
    private int cost;
    private int coolDownTime;
    private boolean isDisabled = false;
    private static ImageView border;
    private static int cardSelected = -1;
    private static HashMap<Integer, PlantCards> allElements;

    public PlantCards(int x, int y,String name, String path, int width, int height, int cost) {
        super(x, y, path, width, height);
        this.cost = cost;
        this.name = name;
    }

    public int getCost(){
        return cost;
    }

    public static void getCards(Pane pane){

        allElements=new HashMap<Integer, PlantCards>();

        PlantCards sunflowerCard=new PlantCards(24, 79, "sunflower",
                "resource/image/sunflowerCard.png",
                97, 58,50);
        sunflowerCard.drawImage(pane);
        sunflowerCard.coolDownTime=5000;
        allElements.put(1,sunflowerCard);
        sunflowerCard.img.setOnMouseClicked(e->{
            if (!sunflowerCard.isDisabled){
                    setCardSelected(1);
                }
            });

        PlantCards peashooterCard = new PlantCards(24, 147, "peashooter",
                "resource/image/peashooterCard.png",97,58,100);
        peashooterCard.drawImage(pane);
        peashooterCard.coolDownTime = 5000;
        allElements.put(2, peashooterCard);
        peashooterCard.img.setOnMouseClicked(e->{
            if (!peashooterCard.isDisabled){
                setCardSelected(2);
            }
        });

        border =new ImageView(new Image("resource/image/selectedCard.png",
                110.0,72.0,false,false));
        pane.getChildren().add(border);
        border.setVisible(false);
        border.setDisable(true);
    }

    public static void setCardSelected(int i){
        cardSelected = i;
        border.setVisible(true);
        border.setX(allElements.get(cardSelected).getX()-5);
        border.setY(allElements.get(cardSelected).getY()-5);
    }

    public static int getCardSelected(){
        return cardSelected;
    }

    public static PlantCards getElement(int x){
        if(allElements.containsKey(x)) return allElements.get(x);
        return null;
    }

    public void setDisabledOn(Pane pane){
        this.isDisabled=true;
        ImageView img =new ImageView(new Image(
                "resource/image/"+ this.name +"CardLocked.png",97.0,58.0,
                false,false));
        img.setX(this.getX());
        img.setY(this.getY());
        pane.getChildren().add(img);
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(this.coolDownTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.isDisabled=false;
            img.setVisible(false);
            img.setDisable(true);
        });
        t.start();
    }

    public static void setCardSelectedToNull(){
        cardSelected = -1;
        border.setVisible(false);
    }
}
