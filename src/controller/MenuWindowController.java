package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javafx.scene.image.ImageView;


public class MenuWindowController {

    public TextArea helpText;

    @FXML
    private AnchorPane mainRoot;

    @FXML
    private ImageView playButton;


    public void toggleHelpText() {
        helpText.setVisible(!helpText.isVisible());
    }

    @FXML
    public void playButton(MouseEvent mouseEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/GameWindow.fxml"));
        AnchorPane root = loader.load();
        GameWindowController controller = loader.<GameWindowController>getController();
        controller.createGame();
        mainRoot.getChildren().setAll(root);
    }


}
