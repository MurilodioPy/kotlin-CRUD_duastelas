package br.com.murilodio.crud

open class Empresa constructor (nome : String, cnpj : String, caixa : Float)
{
    var nome : String
    var cnpj : String
    var caixa : Float

    init {
        this.nome = nome
        this.cnpj = cnpj
        this.caixa = caixa
    }

    override fun toString(): String {
        return "Nome: " +this.nome+" - CNPJ: " +this.cnpj + " - Caixa: " + this.caixa
    }
}