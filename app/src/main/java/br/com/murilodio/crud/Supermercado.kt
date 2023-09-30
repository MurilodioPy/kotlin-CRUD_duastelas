package br.com.murilodio.crud

class Supermercado constructor (nome: String, cnpj: String, caixa : Float, arCondicionado: Boolean ) : Empresa(nome, cnpj, caixa)
{
    var arCondicionado : Boolean = false

    init {
        this.arCondicionado = arCondicionado
    }

    private fun ComAr(arCondicionado : Boolean) : String{
        if (arCondicionado){
            return "Com Ar Condicionado"
        }else{
            return "Sem Ar Condicionado"
        }
    }

    override fun toString(): String {
        return super.toString()+ " - " + ComAr(this.arCondicionado)
    }



}