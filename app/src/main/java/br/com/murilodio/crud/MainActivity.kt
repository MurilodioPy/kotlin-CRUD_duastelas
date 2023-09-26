package br.com.murilodio.crud

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ListView
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    //VIEW
    lateinit var tv_lugares : TextView
    lateinit var tv_capacidade : TextView
    lateinit var lv_empresas : ListView

    //BUTTON
    lateinit var bt_inserir : Button
    lateinit var bt_editar : Button
    lateinit var bt_gerenciar : Button

    //EDITTEXT
    lateinit var et_nome : EditText
    lateinit var et_cnpj : EditText
    lateinit var et_number_caixa : EditText
    lateinit var et_number_qtd : EditText
    lateinit var et_number_cap : EditText
    lateinit var et_item_edit : EditText

    //CHECKBOX
    lateinit var cb_Arcondicionado : CheckBox
    lateinit var radio_Group : RadioGroup

    //ADAPTER
    var adaptador : ArrayAdapter<String>? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TEXTVIEW
        tv_lugares = findViewById(R.id.tv_lugares)
        tv_capacidade = findViewById(R.id.tv_caixa)

        //BUTTON
        bt_inserir = findViewById(R.id.bt_inserir)
        bt_editar = findViewById(R.id.bt_editar)
        bt_gerenciar = findViewById(R.id.bt_gerenciar)

        //EDITTEXT
        et_nome = findViewById(R.id.et_nome_id)
        et_cnpj = findViewById(R.id.et_cnpj)
        et_number_caixa = findViewById(R.id.et_number_caixa)
        et_number_qtd = findViewById(R.id.et_number_qtd)
        et_number_cap = findViewById(R.id.et_number_cap)
        et_item_edit = findViewById(R.id.et_item_edit)

        //CHECKBOX
        cb_Arcondicionado = findViewById(R.id.cb_banheiro_id)
        radio_Group = findViewById(R.id.rd_GrouAuto)

        var listEmpresa = ArrayList<String>()
        adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, listEmpresa)

        var tipo = 0
        radio_Group.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rd_Empresa -> {
                    limpar()
                    et_number_qtd.isEnabled = false
                    cb_Arcondicionado.isEnabled = false
                    et_number_cap.isEnabled = false
                    tipo = 0
                }
                R.id.rd_Super -> {
                    limpar()
                    cb_Arcondicionado.isEnabled = true
                    et_number_qtd.isEnabled = false
                    et_number_cap.isEnabled = false
                    tipo = 1
                }
                R.id.rd_Posto -> {
                    limpar()
                    et_number_cap.isEnabled = true
                    et_number_qtd.isEnabled = false
                    cb_Arcondicionado.isEnabled = false
                    tipo = 2
                }
                R.id.rd_Cine -> {
                    limpar()
                    et_number_qtd.isEnabled = true
                    cb_Arcondicionado.isEnabled = false
                    et_number_cap.isEnabled = false
                    tipo = 3
                }

            }
        }

        bt_inserir.setOnClickListener {
            var inserido = inserir(tipo)
            listEmpresa.add(inserido)
            Tostado(inserido)
        }

        bt_editar.setOnClickListener {
            var editado = ""
            var inserido = ""
            if (testeVazio(et_item_edit)){
                var index = et_item_edit.text.toString().toInt()
                if(index < listEmpresa.size){
                    editado = listEmpresa.get(index)
                    Tostado(editado)
                    listEmpresa.removeAt(index);
                    inserido = inserir(tipo)
                    Tostado(inserido)
                    listEmpresa.add(index , inserido)
                }
            }
        }

        bt_gerenciar.setOnClickListener {
            Intent(this, Manager::class.java).let {
                it.putStringArrayListExtra("lista", listEmpresa)
                register.launch(it)
            }
        }

    }

    fun limpar(){
        et_nome.setText("");
        et_cnpj.setText("");
        et_number_caixa.setText("");
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

    fun inserir(tipo : Number) : String{

        var newEmpresa = when(tipo) {
            1 -> Supermercado(et_nome.text.toString(),et_cnpj.text.toString(), et_number_caixa.text.toString().toFloat(), cb_Arcondicionado.isChecked) ;
            2 -> Posto( et_nome.text.toString(), et_cnpj.text.toString(), et_number_caixa.text.toString().toFloat(), et_number_cap.text.toString().toFloat())
            3 -> Cinema(et_nome.text.toString(), et_cnpj.text.toString(), et_number_caixa.text.toString().toFloat(), et_number_qtd.text.toString().toInt())
            else -> Empresa(et_nome.text.toString(), et_cnpj.text.toString(), et_number_caixa.text.toString().toFloat())
        }
        return newEmpresa.toString()
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