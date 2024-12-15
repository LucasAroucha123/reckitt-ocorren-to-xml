package br.com.actionsys.entity.ocorren;

import java.util.ArrayList;
import java.util.List;

public class Ocorren {

    private List<TxtOcoren> dados = new ArrayList<>();

    public List<TxtOcoren> getOcorrencias() {
        return dados;
    }

    public void setOcorrencias(List<TxtOcoren> dados) {
        this.dados = dados;
    }

}
