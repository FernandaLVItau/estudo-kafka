package br.com.pessoal.estudokafka;

import java.math.BigDecimal;

public class EventoDeTransacao {

    private String id;
    private BigDecimal valor;

    @Deprecated
    public EventoDeTransacao(){}

    public EventoDeTransacao(String id, BigDecimal valor) {
        this.id = id;
        this.valor = valor;
    }

    public String getId() {
        return id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "EventoDeTransacao{" +
                "id='" + id + '\'' +
                ", valor=" + valor +
                '}';
    }
}
