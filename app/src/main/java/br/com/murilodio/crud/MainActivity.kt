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
    lateinit var bt_tela_dados : Button

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

    var listEmpresa = ArrayList<String>()

    val register = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            result.data?.let {
                if (it.hasExtra("listaAtualizada")) {
                    listEmpresa = it.getStringArrayListExtra("listaAtualizada") ?: ArrayList()
                }
            }
        }
    }

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
        bt_gerenciar = findViewById(R.id.bt_tela_remover)
        bt_tela_dados = findViewById(R.id.bt_tela_dados)

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
        //Inserir item na lista
        bt_inserir.setOnClickListener {
            var inserido = inserir(tipo, listEmpresa)
            if(inserido != "null"){
                listEmpresa.add(inserido)
                tostado(inserido)
            }
        }
        //Editar item da lista
        bt_editar.setOnClickListener {
            if (testeNaoVazio(et_item_edit)){
                var index = et_item_edit.text.toString().toInt()
                if(index < listEmpresa.size){
                    listEmpresa.removeAt(index)
                    listEmpresa.add(index , inserir(tipo, listEmpresa))
                }
            }
        }
        //Entrar na tela de gerenciamento
        bt_gerenciar.setOnClickListener {
            Intent(this, Manager::class.java).let {
                it.putStringArrayListExtra("lista", listEmpresa)
                register.launch(it)
            }
        }
        //Entrar na tela de salvamento de dados
        bt_tela_dados.setOnClickListener {
            Intent(this, SalvarDados::class.java).let {
                it.putStringArrayListExtra("lista", listEmpresa)
                register.launch(it)
            }
        }
    }
    //Limpar campos EditText
    fun limpar(){
        et_nome.setText("");
        et_cnpj.setText("");
        et_number_caixa.setText("");
    }

    //Cria Toast
    fun tostado(mensagem: String){
        Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show()
    }

    //Testa EditText vazio
    fun testeNaoVazio(valor : EditText) : Boolean{
        if (valor.getText().isNotEmpty()){
            return true
        }
        return false
    }

    //
    fun inserir(tipo : Number, listaEmpresa: ArrayList<String>) : String{
        // Dados inseridos pelo usuário
        val nome = et_nome.text.toString()
        val cnpj = et_cnpj.text.toString()
        val caixa = et_number_caixa.text.toString().toFloat()

        // Valida os dados
        if (!testeNaoVazio(et_nome)) {
            tostado("O nome é obrigatório")
        } else if (!testeNaoVazio(et_cnpj)) {
            tostado("O CNPJ é obrigatório")
        }else if (testeCNPJ(cnpj, listaEmpresa)) {
                tostado("O CNPJ já existe")
        } else {
            // Cria uma nova empresa
            var newEmpresa = when(tipo) {
                1 -> Supermercado(nome, cnpj, caixa, cb_Arcondicionado.isChecked) ;
                2 -> Posto( nome, cnpj, caixa, et_number_cap.text.toString().toFloat())
                3 -> Cinema(nome, cnpj, caixa, et_number_qtd.text.toString().toInt())
                else -> Empresa(nome, cnpj, caixa)
            }

            // Adiciona a empresa à lista
            return newEmpresa.toString()
        }
        return "null"
    }
    //testa se CNPJ existe
    fun testeCNPJ(cnpj : String, listaEmpresa : ArrayList<String>) : Boolean{
        //verificar se o cnpj já existe
        listaEmpresa.forEach(){
            var objeto = it.split(" - ")
            var cnpjObject = objeto[1].split(": ")
            if(cnpjObject[1].equals(cnpj)){
                return true
            }
        }
        return false
    }
}