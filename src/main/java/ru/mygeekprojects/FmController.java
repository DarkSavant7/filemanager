package ru.mygeekprojects;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FmController {

    @FXML
    VBox leftPanel, rightPanel;

    public void btnExitAction(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void btnCopy(ActionEvent actionEvent) {
        PanelController leftPC = (PanelController) leftPanel.getProperties().get("ctrl");
        PanelController rightPC = (PanelController) rightPanel.getProperties().get("ctrl");

        if (leftPC.getSelectedFileName() == null && rightPC.getSelectedFileName() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No file choose!", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        PanelController srcPC = null, dstPC = null;
        if (leftPC.getSelectedFileName() != null) {
            srcPC = leftPC;
            dstPC = rightPC;
        }

        if (rightPC.getSelectedFileName() != null) {
            srcPC = rightPC;
            dstPC = leftPC;
        }

        Path srcPath = Paths.get(srcPC.getCurrentPath(), srcPC.getSelectedFileName());
        Path dstPath = Paths.get(dstPC.getCurrentPath()).resolve(srcPath.getFileName().toString());

        try {
            Files.copy(srcPath, dstPath);
            dstPC.updateList(Paths.get(dstPC.getCurrentPath()));
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Cannot copy this file!", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void btnMove(ActionEvent actionEvent) {

    }
}
