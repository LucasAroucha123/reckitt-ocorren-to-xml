package br.com.actionsys.entity.ocoren;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;

import java.util.List;

public class TxtOcoren {

    // Cabeçalho Intercâmbio
    private String idRegistroIntercambio;
    private String nomeRemetente;
    private String nomeDestinatario;
    private String dataIntercambio;
    private String horaIntercambio;
    private String idIntercambio;

    // Cabeçalho Documento
    private String idRegistroDocumento;
    private String idDocumento;

    // Dados Transportadora
    private String idRegistroTransportadora;
    private String cnpjTransportadora;
    private String razaoSocialTransportadora;

    // Ocorrência Entrega
    private List<OcorrenciaEntrega> ocorrenciasEntregaList;

    // Complemento Ocorrência
    private List<ComplOcorrencia> complOcorrenciaList;

    // Dados do item da Nota
    private List<DadosItemNota> dadosItemNotasList;

    // Despacho
    private List<Despacho> despachoList;

    // Finalizador de Ocorrência
    private List<FinalizadorOcorrencia> finalizadorOcorrenciaList;

    public String getIdRegistroIntercambio() {
        return idRegistroIntercambio;
    }

    public void setIdRegistroIntercambio(String idRegistroIntercambio) {
        this.idRegistroIntercambio = idRegistroIntercambio;
    }


    public String getNomeRemetente() {
        return nomeRemetente;
    }

    public void setNomeRemetente(String nomeRemetente) {
        this.nomeRemetente = nomeRemetente;
    }


    public String getNomeDestinatario() {
        return nomeDestinatario;
    }

    public void setNomeDestinatario(String nomeDestinatario) {
        this.nomeDestinatario = nomeDestinatario;
    }


    public String getDataIntercambio() {
        return dataIntercambio;
    }

    public void setDataIntercambio(String dataIntercambio) {
        this.dataIntercambio = dataIntercambio;
    }


    public String getHoraIntercambio() {
        return horaIntercambio;
    }

    public void setHoraIntercambio(String horaIntercambio) {
        this.horaIntercambio = horaIntercambio;
    }


    public String getIdIntercambio() {
        return idIntercambio;
    }

    public void setIdIntercambio(String idIntercambio) {
        this.idIntercambio = idIntercambio;
    }


    public String getIdRegistroDocumento() {
        return idRegistroDocumento;
    }

    public void setIdRegistroDocumento(String idRegistroDocumento) {
        this.idRegistroDocumento = idRegistroDocumento;
    }


    public String getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(String idDocumento) {
        this.idDocumento = idDocumento;
    }


    public String getIdRegistroTransportadora() {
        return idRegistroTransportadora;
    }

    public void setIdRegistroTransportadora(String idRegistroTransportadora) {
        this.idRegistroTransportadora = idRegistroTransportadora;
    }


    public String getCnpjTransportadora() {
        return cnpjTransportadora;
    }

    public void setCnpjTransportadora(String cnpjTransportadora) {
        this.cnpjTransportadora = cnpjTransportadora;
    }


    public String getRazaoSocialTransportadora() {
        return razaoSocialTransportadora;
    }

    public void setRazaoSocialTransportadora(String razaoSocialTransportadora) {
        this.razaoSocialTransportadora = razaoSocialTransportadora;
    }

    public List<OcorrenciaEntrega> getOcorrenciasEntregaList() {
        return ocorrenciasEntregaList;
    }

    public void setOcorrenciasEntregaList(List<OcorrenciaEntrega> ocorrenciasEntregaList) {
        this.ocorrenciasEntregaList = ocorrenciasEntregaList;
    }

    public List<ComplOcorrencia> getComplOcorrenciaList() {
        return complOcorrenciaList;
    }

    public void setComplOcorrenciaList(List<ComplOcorrencia> complOcorrenciaList) {
        this.complOcorrenciaList = complOcorrenciaList;
    }

    public List<DadosItemNota> getDadosItemNotasList() {
        return dadosItemNotasList;
    }

    public void setDadosItemNotasList(List<DadosItemNota> dadosItemNota) {
        this.dadosItemNotasList = dadosItemNota;
    }

    public List<Despacho> getDespachoList() {
        return despachoList;
    }

    public void setDespachoList(List<Despacho> despachoList) {
        this.despachoList = despachoList;
    }

    public List<FinalizadorOcorrencia> getFinalizadorOcorrenciaList() {
        return finalizadorOcorrenciaList;
    }

    public void setFinalizadorOcorrenciaList(List<FinalizadorOcorrencia> finalizadorOcorrenciaList) {
        this.finalizadorOcorrenciaList = finalizadorOcorrenciaList;
    }
}
