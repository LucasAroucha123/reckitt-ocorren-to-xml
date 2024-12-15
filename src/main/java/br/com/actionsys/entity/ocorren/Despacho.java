package br.com.actionsys.entity.ocorren;

import jakarta.xml.bind.annotation.XmlElement;

public class Despacho {

    private String idRegistro;
    private String cnpjContratante;
    private String cnpjEmissoraConhecimento;
    private String filialEmissor;
    private String serieConhecimento;
    private String numeroConhecimento;

    @XmlElement
    public String getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(String idRegistro) {
        this.idRegistro = idRegistro;
    }

    @XmlElement
    public String getCnpjContratante() {
        return cnpjContratante;
    }

    public void setCnpjContratante(String cnpjContratante) {
        this.cnpjContratante = cnpjContratante;
    }

    @XmlElement
    public String getCnpjEmissoraConhecimento() {
        return cnpjEmissoraConhecimento;
    }

    public void setCnpjEmissoraConhecimento(String cnpjEmissoraConhecimento) {
        this.cnpjEmissoraConhecimento = cnpjEmissoraConhecimento;
    }

    @XmlElement
    public String getFilialEmissor() {
        return filialEmissor;
    }

    public void setFilialEmissor(String filialEmissor) {
        this.filialEmissor = filialEmissor;
    }

    @XmlElement
    public String getSerieConhecimento() {
        return serieConhecimento;
    }

    public void setSerieConhecimento(String serieConhecimento) {
        this.serieConhecimento = serieConhecimento;
    }

    @XmlElement
    public String getNumeroConhecimento() {
        return numeroConhecimento;
    }

    public void setNumeroConhecimento(String numeroConhecimento) {
        this.numeroConhecimento = numeroConhecimento;
    }
}
