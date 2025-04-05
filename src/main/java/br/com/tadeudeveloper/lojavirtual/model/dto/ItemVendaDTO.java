package br.com.tadeudeveloper.lojavirtual.model.dto;

import br.com.tadeudeveloper.lojavirtual.model.Produto;

public class ItemVendaDTO {

    private Double quantidade;

    // TODO comentado porque o produto gerou recursividade
    //private Produto produto;

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

//    public Produto getProduto() {
//        return produto;
//    }
//
//    public void setProduto(Produto produto) {
//        this.produto = produto;
//    }
}
