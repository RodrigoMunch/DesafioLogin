package com.example.desafiologin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bt_login = findViewById(R.id.bt_login) as Button
        val usuario = findViewById(R.id.usuario) as EditText
        val senha = findViewById(R.id.senha) as EditText
        val bt_cadastrar = findViewById(R.id.bt_cadastrar) as Button

        bt_login.isEnabled = false


        usuario.setOnFocusChangeListener { _, _ -> bt_visible() }
        senha.setOnFocusChangeListener { _, _ -> bt_visible() }




        bt_login.setOnClickListener{
            bt_login.setOnClickListener{
                verifyLogin(usuario.text.toString(), senha.text.toString())
            }


        }


        bt_cadastrar.setOnClickListener{
            IrParaCadastroUsuario()
        }


    }

    private fun bt_visible() {
        val bt_login = findViewById(R.id.bt_login) as Button
        val usuario = findViewById(R.id.usuario) as EditText
        val senha = findViewById(R.id.senha) as EditText

        bt_login.isEnabled = usuario.text.isNotEmpty() && senha.text.isNotEmpty()


    }

    private fun IrParaSegundaTela() {
        val segundaTela = Intent(this, SegundaTela::class.java)
        startActivity(segundaTela)

    }

    private fun IrParaCadastroUsuario() {
        val cadastroUsuario = Intent(this, CadastroUsuarioActivity::class.java)
        startActivity(cadastroUsuario)
    }

    private fun verifyLogin(username: String, senha: String) {
        val arquivoCredenciais = "usuarios.txt"

        if (realizarLogin(arquivoCredenciais, username, senha)) {
            IrParaSegundaTela()
        } else {
            showAlertDialog()
        }
    }

    private fun realizarLogin(arquivo: String, username: String, senha: String): Boolean {
        val diretorio = filesDir
        val caminhoArquivo = File(diretorio, arquivo)

        if (!caminhoArquivo.exists()) {
            return false
        }

        BufferedReader(FileReader(caminhoArquivo)).use { reader ->
            var linha: String?
            while (reader.readLine().also { linha = it } != null) {
                val dados = linha!!.split(":")
                val usuarioArquivo = dados[0]
                val senhaArquivo = dados[1]
                
                if (usuarioArquivo == username && senhaArquivo == senha) {
                    return true
                }
            }
        }
        return false
    }

    private fun showAlertDialog() {
        val alerta = AlertDialog.Builder(this)
        alerta.setTitle("Erro")
        alerta.setMessage("Usuário ou senha inválidos")
        alerta.setPositiveButton("OK", null)
        alerta.show()
    }
}