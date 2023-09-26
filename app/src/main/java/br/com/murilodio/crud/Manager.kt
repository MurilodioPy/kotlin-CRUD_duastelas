package br.com.murilodio.crud

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts

class Manager : AppCompatActivity() {
    //BUTTON
    lateinit var bt_remover : Button
    lateinit var bt_voltar : Button

    //VIEW
    lateinit var lv_empresas : ListView

    //EDITEXT
    lateinit var et_item_removido : EditText

    //ADAPTER
    var adaptador : ArrayAdapter<String>? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manager)

        //VIEW
        lv_empresas = findViewById(R.id.lv_empresas)

        var lista = intent.getStringArrayListExtra("lista") as ArrayList<String>
        adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, lista)
        lv_empresas.adapter = adaptador

        //BUTTON
        bt_remover = findViewById(R.id.bt_remover)
        bt_voltar = findViewById(R.id.bt_voltar)




        //EDITTEXT
        et_item_removido = findViewById(R.id.et_item_remove)

        bt_remover.setOnClickListener {
            if (testeVazio(et_item_removido)){
                var index = et_item_removido.text.toString().toInt()
                if(index < lista.size){
                    Tostado(lista.get(index))
                    lista.removeAt(index)
                }
            }
            lv_empresas.adapter = adaptador
        }

        bt_voltar.setOnClickListener {
            Intent(this, MainActivity::class.java).let {
                it.putStringArrayListExtra("lista", lista)
                register.launch(it)
            }
            this.finish()
        }
    }

    fun Tostado(mensagem: String){
        Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show()
    }

    fun testeVazio(valor : EditText) : Boolean{
        if (valor.getText().isNotEmpty()){
            return true
        }
        return false
    }

    val register = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            result.data?.let {
                if (!it.hasExtra("lista")) {

                }
            }
        }
    }
}