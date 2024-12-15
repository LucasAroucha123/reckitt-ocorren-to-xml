package br.com.actionsys.entity.ocorren;

import jakarta.xml.bind.annotation.XmlElement;

public class DadosItemNota {

    private String idRegistro;
    private String qtdVolumeNota;
    private String qtdVolumeEntregue;
    private String codItemNota;
    private String descricaoItem;

    @XmlElement
    public String getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(String idRegistro) {
        this.idRegistro = idRegistro;
    }

    @XmlElement
    public String getQtdVolumeNota() {
        return qtdVolumeNota;
    }

    public void setQtdVolumeNota(String qtdVolumeNota) {
        this.qtdVolumeNota = qtdVolumeNota;
    }

    @XmlElement
    public String getQtdVolumeEntregue() {
        return qtdVolumeEntregue;
    }

    public void setQtdVolumeEntregue(String qtdVolumeEntregue) {
        this.qtdVolumeEntregue = qtdVolumeEntregue;
    }

    @XmlElement
    public String getCodItemNota() {
        return codItemNota;
    }

    public void setCodItemNota(String codItemNota) {
        this.codItemNota = codItemNota;
    }

    @XmlElement
    public String getDescricaoItem() {
        return descricaoItem;
    }

    public void setDescricaoItem(String descricaoItem) {
        this.descricaoItem = descricaoItem;
    }
}
