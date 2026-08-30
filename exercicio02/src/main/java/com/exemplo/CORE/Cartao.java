package com.exemplo.CORE;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cartao {
    private boolean credito_aprovado;
    private boolean debito_aprovado;
    private double limite;
}
