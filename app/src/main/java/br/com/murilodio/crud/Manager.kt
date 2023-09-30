package br.com.murilodio.crud

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts

class Manager : AppCompatActivity() {

    //BUTTON
    lateinit var bt_remover : Button
    lateinit var bt_voltar : Button
    lateinit var bt_calcular : Button


    //VIEW
    lateinit var lv_empresas : ListView
    lateinit var tv_totalCaixa : TextView

    //EDITEXT
    lateinit var et_item_removido : EditText


    //ADAPTER
    var adaptador : ArrayAdapter<String>? = null

//    var chaveAtualizada : String = ""

    val register = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            result.data?.let {
                if (it.hasExtra("listaAtualizada")) {
//                    chaveAtualizada = it.getStringExtra("listaAtualizada") ?: ""
                }
            }
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manager)

        //VIEW
        lv_empresas = findViewById(R.id.lv_empresas)
        tv_totalCaixa = findViewById(R.id.tv_somaCaixa)

        var lista = intent.getStringArrayListExtra("lista") as ArrayList<String>

        adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, lista)
        lv_empresas.adapter = adaptador

        //BUTTON
        bt_remover = findViewById(R.id.bt_remover)
        bt_voltar = findViewById(R.id.bt_voltar)
        bt_calcular = findViewById(R.id.bt_calcular)

        //EDITTEXT
        et_item_removido = findViewById(R.id.et_item_remove)

        bt_calcular.setOnClickListener {
            var caixa = somaCaixa(lista)
            tv_totalCaixa.text = caixa.toString()
        }

        bt_remover.setOnClickListener {
            if (testeVazio(et_item_removido)){
                var index = et_item_removido.text.toString().toInt()
                if(index < lista.size){
                    tostado(lista.get(index))
                    lista.removeAt(index)
                }
            }
            lv_empresas.adapter = adaptador
        }

        bt_voltar.setOnClickListener {
            val voltarTela = Intent()
            voltarTela.putStringArrayListExtra("listaAtualizada", lista)
            setResult(RESULT_OK, voltarTela)
            finish()
        }
    }

    fun somaCaixa(lista : ArrayList<String>) : Float{
        var caixa = 0.0F
        lista.forEach(){
            var objeto = it.split(" - ")
            var caixaObject = objeto[2].split(": ")
            caixa += caixaObject[1].toFloat()
        }
        return caixa
    }

    fun tostado(mensagem: String){
        Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show()
    }

    fun testeVazio(valor : EditText) : Boolean{
        if (valor.getText().isNotEmpty()){
            return true
        }
        return false
    }
}