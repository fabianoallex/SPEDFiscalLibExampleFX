package com.example.spedfiscallibexamplefx;

import sped.core.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import java.text.ParseException;
import java.util.ArrayList;

interface TextFieldTimerListener {
    void onChange(String oldValue, String newValue) throws ParseException;
}

class TextFieldTimer extends TextField {
    static public long DEFAULT_DELAY = 1000L;
    private Timeline timeline = null;
    private TextFieldTimerListener listener;
    private String oldText = "";
    TextFieldTimer(long delay) {
        this.textProperty().addListener((observable, oldValue, newValue) -> {
            if (listener == null)
                return;

            if (timeline != null)
                timeline.stop();

            timeline = new Timeline(
                    new KeyFrame(Duration.millis(DEFAULT_DELAY), event -> {
                        try {
                            listener.onChange(oldText, newValue);
                        } catch (ParseException exception) {
                            throw new RuntimeException(exception);
                        }
                        oldText = newValue;
                    })
            );

            timeline.setCycleCount(1);
            timeline.play();
        });
    }

    TextFieldTimer() {
        this(TextFieldTimer.DEFAULT_DELAY);
    }

    void setListener(TextFieldTimerListener listener) {
        this.listener = listener;
    }
}

class FieldControls {
    private final Field<?> field;
    private final Pane pane = new Pane();

    private final Label labelFieldName = new Label();
    private final TextFieldTimer textField = new TextFieldTimer();
    private final TextField textFieldFormatted = new TextField();

    FieldControls(Field<?> field, Register register, ListView<ValidationEventListViewItem> listView) {
        this.field = field;
        labelFieldName.setText(field.getName());

        labelFieldName.setLayoutY(5);
        textField.setLayoutY(5);
        textFieldFormatted.setLayoutY(5);

        textField.setLayoutX(90);
        textFieldFormatted.setLayoutX(390);

        textField.setPrefWidth(300);
        textFieldFormatted.setPrefWidth(300);

        textField.appendText(field.getValueAsString());

        textField.setListener((oldValue, newValue) -> {
            listView.getItems().clear();



            field.setValueFromString(newValue);
            textFieldFormatted.clear();
            textFieldFormatted.appendText(FieldFormatter.formatField(field, register));

            FieldValidator fieldValidator = new FieldValidator(register, new ValidationListener() {
                @Override
                public void onSuccessMessage(ValidationEvent validationEvent) {

                }

                @Override
                public void onWarningMessage(ValidationEvent validationEvent) {

                }

                @Override
                public void onErrorMessage(ValidationEvent validationEvent) {
                    listView.getItems().add(new ValidationEventListViewItem(validationEvent));
                }
            });

            fieldValidator.validate(field);
        });

        textFieldFormatted.appendText(FieldFormatter.formatField(field, register));

        pane.getChildren().addAll(labelFieldName, textField, textFieldFormatted);
    }

    public Label getLabelFieldName() {
        return labelFieldName;
    }

    public TextField getTextField() {
        return textField;
    }

    public TextField getTextFieldFormatted() {
        return textFieldFormatted;
    }

    Pane getPane() {
        return this.pane;
    }

    public Field<?> getField() {
        return field;
    }
}

public class RegisterViewController {
    private final Register register;
    private final ArrayList<FieldControls> fieldControlsList = new ArrayList<>();

    @FXML
    private VBox vbox;

    RegisterViewController(Register register) {
        this.register = register;
    }

    public Register getRegister() {
        return register;
    }

    public void update() {
        ListView<ValidationEventListViewItem> listView = new ListView<>();
        listView.setMaxHeight(150);

        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                if (!mouseEvent.getButton().equals(MouseButton.PRIMARY)) return;
                if (mouseEvent.getClickCount() != 2) return;

                var item = listView.getSelectionModel().getSelectedItem();
                if (item == null) return;

                Register register = item.getRegister();
                if (register == null) return;

                Field<?> field = (item.validationEvent() instanceof FieldValidationEvent fieldValidationEvent) ?
                        fieldValidationEvent.getField() : null;

                System.out.println(field);

                if (field == null) return;

                fieldControlsList.forEach(fieldControls -> {
                   if (fieldControls.getField().equals(field)) {
                        fieldControls.getTextField().requestFocus();
                    }
                });
            }
        });

        register.getFields().forEach((s, field) -> {
            FieldControls fieldControls = new FieldControls(field, this.register, listView);
            fieldControlsList.add(fieldControls);
            vbox.getChildren().add(fieldControls.getPane());
        });

        Button buttonValidarTodos = new Button("Validar Fields");

        vbox.getChildren().add(buttonValidarTodos);
        vbox.getChildren().add(listView);

        buttonValidarTodos.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                listView.getItems().clear();

                register.getFields().forEach((s, field) -> {
                    FieldValidator fieldValidator = new FieldValidator(register, new ValidationListener() {
                        @Override
                        public void onSuccessMessage(ValidationEvent validationEvent) {

                        }

                        @Override
                        public void onWarningMessage(ValidationEvent validationEvent) {

                        }

                        @Override
                        public void onErrorMessage(ValidationEvent validationEvent) {
                            listView.getItems().add(new ValidationEventListViewItem(validationEvent));
                        }
                    });

                    fieldValidator.validate(field);
                });
            }
        });
    }
}
