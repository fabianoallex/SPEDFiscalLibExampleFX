package com.example.spedfiscallibexamplefx;

import SPEDFiscalLib.Factory;
import SPEDFiscalLib.NamedRegister;

public class Register0005 extends NamedRegister {
    public static String REGISTER_NAME = "0005";
    public static String FIELD_NAME_FANTASIA = "FANTASIA";
    public static String FIELD_NAME_CEP = "CEP";
    public static String FIELD_NAME_NUMERO = "NUM";
    public static String FIELD_NAME_COMPLEMENTO = "COMPL";
    public static String FIELD_NAME_BAIRRO = "BAIRRO";

    public Register0005(Factory factory) {
        super(factory, REGISTER_NAME);
    }

    public void setFantasia(String fantasia) {
        this.getStringField(FIELD_NAME_FANTASIA).setValue(fantasia);
    }

    public void setCep(String cep) {
        this.getStringField(FIELD_NAME_CEP).setValue(cep);
    }

    public void setNumero(String numero) {
        this.getStringField(FIELD_NAME_NUMERO).setValue(numero);
    }

    public void setComplemento(String complemento) {
        this.getStringField(FIELD_NAME_COMPLEMENTO).setValue(complemento);
    }

    public void setBairro(String bairro) {
        this.getStringField(FIELD_NAME_BAIRRO).setValue(bairro);
    }

    public String getFantasia() {
        return this.getStringField(FIELD_NAME_FANTASIA).getValue();
    }

    public String getCep() {
        return this.getStringField(FIELD_NAME_CEP).getValue();
    }

    public String getNumero() {
        return this.getStringField(FIELD_NAME_NUMERO).getValue();
    }

    public String getComplemento() {
        return this.getStringField(FIELD_NAME_COMPLEMENTO).getValue();
    }

    public String getBairro() {
        return this.getStringField(FIELD_NAME_BAIRRO).getValue();
    }
}
