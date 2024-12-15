package br.com.actionsys.entity.ocorren;

import jakarta.xml.bind.annotation.XmlElement;

public class OcorrenciaEntrega {

    private String idRegistroOcorrencia;
    private String cnpjEmissorNota;
    private String serieNota;
    private String numeroNota;
    private String codigoOcorrencia;
    private String dataOcorrencia;
    private String horaOcorrencia;
    private String codigoObsOcorrencia;
    private String idEmbarque;
    private String idEmbarque1;
    private String idEmbarque2;
    private String idEmbarque3;
    private String filialConhecimento;
    private String serieConhecimento;
    private String numeroConhecimento;
    private String tipoEntrega;
    private String codigoEmissora;
    private String codigoFilial;
    private String dataChegadaDestino;
    private String horaChegadaDestino;
    private String dataInicioDescarregamento;
    private String horaInicioDescarregamento;
    private String dataTerminoDescarregamento;
    private String horaTerminoDescarregamento;
    private String dataSaidaDestino;
    private String horaSaidaDestino;
    private String cnpjEmissorDevolucao;
    private String serieNotaDevolucao;
    private String numeroNotaDevolucao;
    private String dataAgendamentoOcorrencia;
    private String dataSolicitacaoAgendaCliente;
    private String dataConfirmAgendamento;

    @XmlElement
    public String getIdRegistroOcorrencia() {
        return idRegistroOcorrencia;
    }

    public void setIdRegistroOcorrencia(String idRegistroOcorrencia) {
        this.idRegistroOcorrencia = idRegistroOcorrencia;
    }

    @XmlElement
    public String getCnpjEmissorNota() {
        return cnpjEmissorNota;
    }

    public void setCnpjEmissorNota(String cnpjEmissorNota) {
        this.cnpjEmissorNota = cnpjEmissorNota;
    }

    @XmlElement
    public String getSerieNota() {
        return serieNota;
    }

    public void setSerieNota(String serieNota) {
        this.serieNota = serieNota;
    }

    @XmlElement
    public String getNumeroNota() {
        return numeroNota;
    }

    public void setNumeroNota(String numeroNota) {
        this.numeroNota = numeroNota;
    }

    @XmlElement
    public String getCodigoOcorrencia() {
        return codigoOcorrencia;
    }

    public void setCodigoOcorrencia(String codigoOcorrencia) {
        this.codigoOcorrencia = codigoOcorrencia;
    }

    @XmlElement
    public String getDataOcorrencia() {
        return dataOcorrencia;
    }

    public void setDataOcorrencia(String dataOcorrencia) {
        this.dataOcorrencia = dataOcorrencia;
    }

    @XmlElement
    public String getHoraOcorrencia() {
        return horaOcorrencia;
    }

    public void setHoraOcorrencia(String horaOcorrencia) {
        this.horaOcorrencia = horaOcorrencia;
    }

    @XmlElement
    public String getCodigoObsOcorrencia() {
        return codigoObsOcorrencia;
    }

    public void setCodigoObsOcorrencia(String codigoObsOcorrencia) {
        this.codigoObsOcorrencia = codigoObsOcorrencia;
    }

    @XmlElement
    public String getIdEmbarque() {
        return idEmbarque;
    }

    public void setIdEmbarque(String idEmbarque) {
        this.idEmbarque = idEmbarque;
    }

    @XmlElement
    public String getIdEmbarque1() {
        return idEmbarque1;
    }

    public void setIdEmbarque1(String idEmbarque1) {
        this.idEmbarque1 = idEmbarque1;
    }

    @XmlElement
    public String getIdEmbarque2() {
        return idEmbarque2;
    }

    public void setIdEmbarque2(String idEmbarque2) {
        this.idEmbarque2 = idEmbarque2;
    }

    @XmlElement
    public String getIdEmbarque3() {
        return idEmbarque3;
    }

    public void setIdEmbarque3(String idEmbarque3) {
        this.idEmbarque3 = idEmbarque3;
    }

    @XmlElement
    public String getFilialConhecimento() {
        return filialConhecimento;
    }

    public void setFilialConhecimento(String filialConhecimento) {
        this.filialConhecimento = filialConhecimento;
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

    @XmlElement
    public String getTipoEntrega() {
        return tipoEntrega;
    }

    public void setTipoEntrega(String tipoEntrega) {
        this.tipoEntrega = tipoEntrega;
    }

    @XmlElement
    public String getCodigoEmissora() {
        return codigoEmissora;
    }

    public void setCodigoEmissora(String codigoEmissora) {
        this.codigoEmissora = codigoEmissora;
    }

    @XmlElement
    public String getCodigoFilial() {
        return codigoFilial;
    }

    public void setCodigoFilial(String codigoFilial) {
        this.codigoFilial = codigoFilial;
    }

    @XmlElement
    public String getDataChegadaDestino() {
        return dataChegadaDestino;
    }

    public void setDataChegadaDestino(String dataChegadaDestino) {
        this.dataChegadaDestino = dataChegadaDestino;
    }

    @XmlElement
    public String getHoraChegadaDestino() {
        return horaChegadaDestino;
    }

    public void setHoraChegadaDestino(String horaChegadaDestino) {
        this.horaChegadaDestino = horaChegadaDestino;
    }

    @XmlElement
    public String getDataInicioDescarregamento() {
        return dataInicioDescarregamento;
    }

    public void setDataInicioDescarregamento(String dataInicioDescarregamento) {
        this.dataInicioDescarregamento = dataInicioDescarregamento;
    }

    @XmlElement
    public String getHoraInicioDescarregamento() {
        return horaInicioDescarregamento;
    }

    public void setHoraInicioDescarregamento(String horaInicioDescarregamento) {
        this.horaInicioDescarregamento = horaInicioDescarregamento;
    }

    @XmlElement
    public String getDataTerminoDescarregamento() {
        return dataTerminoDescarregamento;
    }

    public void setDataTerminoDescarregamento(String dataTerminoDescarregamento) {
        this.dataTerminoDescarregamento = dataTerminoDescarregamento;
    }

    @XmlElement
    public String getHoraTerminoDescarregamento() {
        return horaTerminoDescarregamento;
    }

    public void setHoraTerminoDescarregamento(String horaTerminoDescarregamento) {
        this.horaTerminoDescarregamento = horaTerminoDescarregamento;
    }

    @XmlElement
    public String getDataSaidaDestino() {
        return dataSaidaDestino;
    }

    public void setDataSaidaDestino(String dataSaidaDestino) {
        this.dataSaidaDestino = dataSaidaDestino;
    }

    @XmlElement
    public String getHoraSaidaDestino() {
        return horaSaidaDestino;
    }

    public void setHoraSaidaDestino(String horaSaidaDestino) {
        this.horaSaidaDestino = horaSaidaDestino;
    }

    @XmlElement
    public String getCnpjEmissorDevolucao() {
        return cnpjEmissorDevolucao;
    }

    public void setCnpjEmissorDevolucao(String cnpjEmissorDevolucao) {
        this.cnpjEmissorDevolucao = cnpjEmissorDevolucao;
    }

    @XmlElement
    public String getSerieNotaDevolucao() {
        return serieNotaDevolucao;
    }

    public void setSerieNotaDevolucao(String serieNotaDevolucao) {
        this.serieNotaDevolucao = serieNotaDevolucao;
    }

    @XmlElement
    public String getNumeroNotaDevolucao() {
        return numeroNotaDevolucao;
    }

    public void setNumeroNotaDevolucao(String numeroNotaDevolucao) {
        this.numeroNotaDevolucao = numeroNotaDevolucao;
    }

    @XmlElement
    public String getDataAgendamentoOcorrencia() {
        return dataAgendamentoOcorrencia;
    }

    public void setDataAgendamentoOcorrencia(String dataAgendamentoOcorrencia) {
        this.dataAgendamentoOcorrencia = dataAgendamentoOcorrencia;
    }

    @XmlElement
    public String getDataSolicitacaoAgendaCliente() {
        return dataSolicitacaoAgendaCliente;
    }

    public void setDataSolicitacaoAgendaCliente(String dataSolicitacaoAgendaCliente) {
        this.dataSolicitacaoAgendaCliente = dataSolicitacaoAgendaCliente;
    }

    @XmlElement
    public String getDataConfirmAgendamento() {
        return dataConfirmAgendamento;
    }

    public void setDataConfirmAgendamento(String dataConfirmAgendamento) {
        this.dataConfirmAgendamento = dataConfirmAgendamento;
    }
}
