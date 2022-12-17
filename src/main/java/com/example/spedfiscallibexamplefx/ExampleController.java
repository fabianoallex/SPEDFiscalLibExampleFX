package com.example.spedfiscallibexamplefx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sped.core.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

public class ExampleController {
    @FXML
    private ListView<ValidationEventListViewItem> listViewValidations;

    @FXML
    private ListView<SPEDRow> listViewContents;

    @FXML
    protected void listViewContentsMouseClick(MouseEvent event) throws IOException {
        if (!event.getButton().equals(MouseButton.PRIMARY)) return;
        if (event.getClickCount() != 2) return;

        var item = listViewContents.getSelectionModel().getSelectedItem();
        if (item == null) return;

        Register register = item.register();
        if (register == null) return;

        ShowRegisterView(register);
    }

    @FXML
    protected void listViewValidationsMouseClick(MouseEvent event) throws IOException {
        if (!event.getButton().equals(MouseButton.PRIMARY)) return;
        if (event.getClickCount() != 2) return;

        var item = listViewValidations.getSelectionModel().getSelectedItem();
        if (item == null) return;

        Register register = item.getRegister();
        if (register == null) return;

        ShowRegisterView(register);
    }

    private void ShowRegisterView(Register register) throws IOException {
        RegisterViewController controller = new RegisterViewController(register);

        FXMLLoader fxmlLoader = new FXMLLoader(ExampleApplication.class.getResource("register-view.fxml"));
        fxmlLoader.setController(controller);

        Scene scene = new Scene(fxmlLoader.load(), 0, 0);
        Stage stage = new Stage();
        stage.setTitle("Registro " + register.getName());
        stage.setScene(scene);

        controller.update();

        stage.show();
    }

    @FXML
    protected void onGenerateButtonClick() {
        try {
            SpedGenerator spedGenerator =
                    SpedGenerator.newBuilder("definitions.xml")
                    .setFileLoader(fileName -> Objects.requireNonNull(ExampleApplication.class.getResourceAsStream(fileName)))
                    .setValidationHelper(new MyValidation())
                    .build();

            Register r = spedGenerator.getRegister0000().getRegister();  //0000

            r.setFieldValue("COD_VER", 14);
            r.setFieldValue("COD_FIN", 1);
            r.setFieldValue("DT_INI", new Date());
            r.setFieldValue("DT_FIN", new Date(555525));
            r.setFieldValue("NOME", "  FABIANO ARNDT ");
            r.setFieldValue("CPF", "123456789-10");
            r.setFieldValue("CNPJ", "00.360.305/0001-04");
            r.setFieldValue("UF", "");
            r.setFieldValue("COD_MUN", 1501452);
            r.setFieldValue("IND_PERFIL", "A");
            r.setFieldValue("IE", "ISENTO");
            r.setFieldValue("IND_ATIV", 0);

            Block b0 = spedGenerator.newBlockBuilder()
                    .setBlockName("0")
                    .setOpeningRegisterName("0001")
                    .setClosureRegisterName("0990")
                    .build();

            r = b0.addRegister("0002");
            r.setFieldValue("CLAS_ESTAB_IND", "05");

            //usando classe especializada de NamedRegister
            Register0005 register0005 = (Register0005) b0.addNamedRegister(Register0005.class);
            register0005.setFantasia("Nome de Fantasia");
            register0005.setCep("78123456");
            register0005.setNumero("1500");
            register0005.setComplemento("Rua das rosas");
            register0005.setBairro("Centro");

            Register r0190 = b0.addRegister("0190");
            r0190.setFieldValue("UNID", "M");
            r0190.setFieldValue("DESCR", "METRO");

            r0190 = b0.addRegister("0190");
            r0190.setFieldValue("UNID", "M2");
            r0190.setFieldValue("DESCR", "METRO QUADRADO");

            r0190 = b0.addRegister("0190");
            r0190.setFieldValue("UNID", "KG123456789");  //formatted: KG1234
            r0190.setFieldValue("DESCR", "QUILO");

            Register r0200 = b0.addRegister("0200");
            r0200.setFieldValue("COD_ITEM", "1000");
            r0200.setFieldValue("DESCR_ITEM", "ABACATE");
            r0200.setFieldValue("UNID_INV", r0190);

            System.out.println("0200 ID: " + r0200.getID()); //COD_ITEM

            r = b0.addRegister("0205");
            //r.setFieldValue("teste", "ABACATE");            //throws FieldNotFoundException - nao existe campo teste NO REGISTRO 0200
            r.setFieldValue("DESCR_ANT_ITEM", "ABACATE ANTIGO");


            Block bc = spedGenerator.newBlockBuilder()
                    .setBlockName("C")
                    .setOpeningRegisterName("C001")
                    .setClosureRegisterName("C990")
                    .build();

            r = bc.addRegister("C100");

            for (int i = 0; i < 4; i++) {
                Register c590 = bc.addRegister("C590");
                Register c591 = c590.addRegister("C591");
                c591.setFieldValue("VL_FCP_OP", 2555.9933 + i);
                c591.setFieldValue("VL_FCP_ST", 2333.09 + 2*i);
            }

            Block bd = spedGenerator.newBlockBuilder()
                    .setBlockName("D")
                    .setOpeningRegisterName("D001")
                    .setClosureRegisterName("D990")
                    .build();

            Block be = spedGenerator.newBlockBuilder()
                    .setBlockName("E")
                    .setOpeningRegisterName("E001")
                    .setClosureRegisterName("E990")
                    .build();

            //totalizacao: gerar os registros de contagem (bloco 9)
            spedGenerator.generateBlock9();
            spedGenerator.totalize();

            //altere aqui para 0, 1 ou 2 para testar as diferentes formas de obter a saida dos dados
            int writerOptions = 2;

            //Exemplo com StringBuilder
            if (writerOptions == 0) {
                StringBuilderWriter writer = new StringBuilderWriter(new StringBuilder());
                spedGenerator.write(writer);
                listViewContents.getItems().clear();
                //listViewContents.getItems().add(writer.stringBuilder().toString());
            }

            //exemplo com FileWriter
            if (writerOptions == 1) {
                //exemplo com FileWriter:
                FileWriter fileWriter = new FileWriter("c:/caminho/do/arquivo/teste2.txt");
                FileWriterWriter writer = new FileWriterWriter(fileWriter);
                fileWriter.close();
            }

            if (writerOptions == 2) {
                //exemplo implementando Writer em uma lambda
                listViewContents.getItems().clear();
                spedGenerator.write((string, register) -> {
                    listViewContents.getItems().add(new SPEDRow(string, register));
                });
            }

            listViewValidations.getItems().clear();

            spedGenerator.validate(new ValidationListener() {
                @Override
                public void onSuccessMessage(ValidationEvent event) {
                    listViewValidations.getItems().add(new ValidationEventListViewItem(event));
                }

                @Override
                public void onWarningMessage(ValidationEvent event) {
                    listViewValidations.getItems().add(new ValidationEventListViewItem(event));
                }

                @Override
                public void onErrorMessage(ValidationEvent event) {
                    listViewValidations.getItems().add(new ValidationEventListViewItem(event));
                }
            });
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

record ValidationEventListViewItem(ValidationEvent validationEvent) {
    @Override
    public String toString() {
        return this.validationEvent.getMessage();
    }

    public Register getRegister() {
        return this.validationEvent.getRegister();
    }
}

record SPEDRow(String row, Register register) {
    @Override
    public String toString() {
        return this.register.toString();
    }
}