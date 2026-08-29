package com.exemplo.CORE;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Conta{
    private double renda_mensal;
    private boolean nome_sujo;
    private double score;
    private Cartao cartao;

    public void avaliacao_limite(){

        if(nome_sujo){
            this.cartao.setLimite(0.0);
            this.cartao.setCredito_aprovado(false);
            this.cartao.setDebito_aprovado(true);
        }else{
            if(renda_mensal < 1500){
                this.cartao.setLimite(0.0);
                this.cartao.setCredito_aprovado(false);
                this.cartao.setDebito_aprovado(true);
            }else{
                this.cartao.setLimite(this.renda_mensal*0.3);
                this.cartao.setCredito_aprovado(true);
                this.cartao.setDebito_aprovado(true);
            }
            if(score > 800){
                this.cartao.setLimite(this.cartao.getLimite()*1.5);
            }
        }


    }
}