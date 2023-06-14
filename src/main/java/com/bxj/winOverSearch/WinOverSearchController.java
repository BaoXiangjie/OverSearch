package com.bxj.winOverSearch;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * @author BaoXiangjie
 * @date  2023/6/13 20:55
 */
public class WinOverSearchController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("666");
    }
}