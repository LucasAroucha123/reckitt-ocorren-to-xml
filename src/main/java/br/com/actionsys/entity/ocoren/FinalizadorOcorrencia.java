package br.com.actionsys.entity.ocoren;

import jakarta.xml.bind.annotation.XmlElement;

public class FinalizadorOcorrencia {

    private String idRegistro;
    private String numeroRegistro;

    @XmlElement
    public String getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(String idRegistro) {
        this.idRegistro = idRegistro;
    }

    @XmlElement
    public String getNumeroRegistro() {
        return numeroRegistro;
    }

    public void setNumeroRegistro(String numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }
}
