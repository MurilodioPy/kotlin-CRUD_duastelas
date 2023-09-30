package br.com.murilodio.crud

class Posto constructor (nome: String, cnpj: String, caixa: Float, capacidade: Float) : Empresa(nome, cnpj, caixa)
{
    var capacidade : Float

    init {
        this.capacidade = capacidade
    }

    override fun toString(): String {
        return super.toString()+" - Capacidade: "+ capacidade
    }
}