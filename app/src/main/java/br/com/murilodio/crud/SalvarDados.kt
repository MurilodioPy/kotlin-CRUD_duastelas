package br.com.murilodio.crud

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader

class SalvarDados : AppCompatActivity() {
    //ARQUIVO
    private val caminhoDoArquivo = "MeuArquivo"

    //VIEW
    lateinit var lv_empresas : ListView
    lateinit var lv_DadoSalvo : TextView

    //EDITTEXT
    lateinit var et_nomeDoArquivo : EditText

    //BUTTON
    lateinit var bt_salvar : Button
    lateinit var bt_ValorSalvo : Button
    lateinit var bt_voltar : Button

    //ADAPTER
    var adaptador : ArrayAdapter<String>? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salvar_dados)

        //VIEW
        lv_empresas = findViewById(R.id.lv_empresas_salvar)
        var lista = intent.getStringArrayListExtra("lista") as ArrayList<String>
        adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, lista)
        lv_empresas.adapter = adaptador
        lv_DadoSalvo = findViewById(R.id.tv_DadoSalvo)

        //EDITTEXT
        et_nomeDoArquivo = findViewById(R.id.et_nomeDoArquivo)

        //BUTTON
        bt_salvar = findViewById(R.id.bt_salvar)
        bt_ValorSalvo = findViewById(R.id.bt_ValorSalvo)
        bt_voltar = findViewById(R.id.bt_voltar_salvar)

        //limpa campo nome do arquivo
        et_nomeDoArquivo.setOnClickListener {
            et_nomeDoArquivo.text.clear()
        }

        bt_ValorSalvo.setOnClickListener{
            if(testeNaoVazio(et_nomeDoArquivo)) {
                val nomeDoArquivo = et_nomeDoArquivo.text.toString()
                val arquivoExterno = File(getExternalFilesDir(caminhoDoArquivo), nomeDoArquivo)
                if (nomeDoArquivo.trim() != "") {
                    val fileInputStream = FileInputStream(arquivoExterno)
                    val inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
                    val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
                    val stringBuilder: StringBuilder = StringBuilder()
                    var text: String? = null

                    //while ((linha = br.readLine()) != null) - Java
                    while ((bufferedReader.readLine().also { text = it }) != null) {
                        stringBuilder.append(text)
                    }
                    fileInputStream.close()
                    lv_DadoSalvo.text = stringBuilder.toString()
                    et_nomeDoArquivo.text.clear()
                }
            }
        }

        bt_salvar.setOnClickListener {
            if(testeNaoVazio(et_nomeDoArquivo)){
                var arquivoExterno = File(getExternalFilesDir(caminhoDoArquivo), et_nomeDoArquivo.text.toString())
                try {
                    val fileOutPutStream = FileOutputStream(arquivoExterno)
                    fileOutPutStream.write(lista.toString().toByteArray())
                    fileOutPutStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                Toast.makeText(applicationContext,"Texto salvo com sucesso!", Toast.LENGTH_SHORT).show()
            }
        }

        bt_voltar.setOnClickListener {
            finish()
        }
    }

    //Testa EditText vazio
    fun testeNaoVazio(valor : EditText) : Boolean{
        if (valor.getText().isNotEmpty()){
            return true
        }
        return false
    }
}