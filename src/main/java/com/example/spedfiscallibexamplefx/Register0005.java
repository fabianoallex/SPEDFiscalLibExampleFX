package com.example.spedfiscallibexamplefx;

import SPEDFiscalLib.Factory;
import SPEDFiscalLib.Field;
import SPEDFiscalLib.FieldNotFoundException;
import SPEDFiscalLib.NamedRegister;

public class Register0005 extends NamedRegister {
    public static String REGISTER_NAME = "0005";
    public static String FIELD_NAME_FANTASIA = "FANTASIA";
    public static String FIELD_NAME_CEP = "CEP";
    public static String FIELD_NAME_NUMERO = "NUM";
    public static String FIELD_NAME_COMPLEMENTO = "COMPL";
    public static String FIELD_NAME_BAIRRO = "BAIRRO";

    private final Field<String> fieldFantasia;
    private final Field<String> fieldCep;
    private final Field<String> fieldNumero;
    private final Field<String> fieldComplemento;
    private final Field<String> fieldBairro;

    public Register0005(Factory factory) {
        super(factory, REGISTER_NAME);
        try {
            fieldFantasia = this.getRegister().getField(FIELD_NAME_FANTASIA);
            fieldCep = this.getRegister().getField(FIELD_NAME_CEP);
            fieldNumero = this.getRegister().getField(FIELD_NAME_NUMERO);
            fieldComplemento = this.getRegister().getField(FIELD_NAME_COMPLEMENTO);
            fieldBairro = this.getRegister().getField(FIELD_NAME_BAIRRO);
        } catch (FieldNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void setFantasia(String fantasia) {
        fieldFantasia.setValue(fantasia);
    }

    public void setCep(String cep) {
        fieldCep.setValue(cep);
    }

    public void setNumero(String numero) {
        fieldNumero.setValue(numero);
    }

    public void setComplemento(String complemento) {
        fieldComplemento.setValue(complemento);
    }

    public void setBairro(String bairro) {
        fieldBairro.setValue(bairro);
    }
}
