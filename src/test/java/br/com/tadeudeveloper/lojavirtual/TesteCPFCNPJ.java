package br.com.tadeudeveloper.lojavirtual;

import br.com.tadeudeveloper.lojavirtual.util.ValidaCNPJ;
import br.com.tadeudeveloper.lojavirtual.util.ValidaCPF;

public class TesteCPFCNPJ {

    public static void main(String[] args) {
        boolean isCnpj = ValidaCNPJ.isCNPJ("66.347.536/0001-96");
        System.out.println("CNPJ válido: " + isCnpj);

        boolean isCpf = ValidaCPF.isCPF("443.374.340-20");
        System.out.println("CPF válido: " + isCpf);
    }
}
