package br.com.murilodio.crud

class Cinema constructor (nome: String, cnpj: String, caixa: Float,  QtdAssento: Int) : Empresa(nome, cnpj, caixa)
{
    var QtdAssento:Int = 0

    init {
        this.QtdAssento = QtdAssento
    }

    override fun toString(): String {
        return super.toString()+" - Quantiidade de Assentos:"+ this.QtdAssento
    }
}