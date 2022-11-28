package com.example.spedfiscallibexamplefx;

import sped.lib.Factory;
import sped.lib.NamedRegister;

public class Register0005 extends NamedRegister {
    public static String REGISTER_NAME = "0005";
    public static String FIELD_FANTASIA = "FANTASIA";
    public static String FIELD_CEP = "CEP";
    public static String FIELD_NUMERO = "NUM";
    public static String FIELD_COMPLEMENTO = "COMPL";
    public static String FIELD_BAIRRO = "BAIRRO";

    public Register0005(Factory factory) {
        super(factory, REGISTER_NAME);
    }

    public void setFantasia(String fantasia) {
        this.getStringField(FIELD_FANTASIA).setValue(fantasia);
    }

    public void setCep(String cep) {
        this.getStringField(FIELD_CEP).setValue(cep);
    }

    public void setNumero(String numero) {
        this.getStringField(FIELD_NUMERO).setValue(numero);
    }

    public void setComplemento(String complemento) {
        this.getStringField(FIELD_COMPLEMENTO).setValue(complemento);
    }

    public void setBairro(String bairro) {
        this.getStringField(FIELD_BAIRRO).setValue(bairro);
    }

    public String getFantasia() {
        return this.getStringField(FIELD_FANTASIA).getValue();
    }

    public String getCep() {
        return this.getStringField(FIELD_CEP).getValue();
    }

    public String getNumero() {
        return this.getStringField(FIELD_NUMERO).getValue();
    }

    public String getComplemento() {
        return this.getStringField(FIELD_COMPLEMENTO).getValue();
    }

    public String getBairro() {
        return this.getStringField(FIELD_BAIRRO).getValue();
    }
}
