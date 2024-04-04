package com.example.desafiologin

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter
import java.io.*

class CadastroUsuarioActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var senhaEditText: EditText
    private lateinit var confirmarSenhaEditText: EditText
    private lateinit var tipoUsuarioSpinner: Spinner
    private lateinit var cadastrarButton: Button
    private lateinit var bt_sair: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_usuario)

        usernameEditText = findViewById(R.id.usernameEditText)
        senhaEditText = findViewById(R.id.senhaEditText)
        confirmarSenhaEditText = findViewById(R.id.confirmarSenhaEditText)
        tipoUsuarioSpinner = findViewById(R.id.tipoUsuarioSpinner)
        cadastrarButton = findViewById(R.id.cadastrarButton)
        bt_sair = findViewById(R.id.bt_sair)

        cadastrarButton.isEnabled = false

        usernameEditText.setOnFocusChangeListener { _, _ -> bt_visible() }
        senhaEditText.setOnFocusChangeListener { _, _ -> bt_visible() }
        confirmarSenhaEditText.setOnFocusChangeListener { _, _ -> bt_visible() }


        val tiposUsuarios = arrayOf("user", "adm", "mkt")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tiposUsuarios)
        tipoUsuarioSpinner.adapter = adapter

        cadastrarButton.setOnClickListener {
            cadastrarUsuario()
        }

        bt_sair.setOnClickListener {
            irParaTelaLogin()
        }
    }

    private fun bt_visible() {
        val bt_cadastrar = findViewById(R.id.cadastrarButton) as Button
        val username = findViewById(R.id.usernameEditText) as EditText
        val senha = findViewById(R.id.senhaEditText) as EditText
        val confirmarSenha = findViewById(R.id.confirmarSenhaEditText) as EditText

        bt_cadastrar.isEnabled = username.text.isNotEmpty() && senha.text.isNotEmpty() && confirmarSenha.text.isNotEmpty()
    }

    private fun cadastrarUsuario() {
        val username = usernameEditText.text.toString()
        val senha = senhaEditText.text.toString()
        val confirmarSenha = confirmarSenhaEditText.text.toString()
        val tipoUsuario = tipoUsuarioSpinner.selectedItem.toString()

        if (username.isNotEmpty() && senha.isNotEmpty() && senha == confirmarSenha) {
            if (usuarioExiste(username)) {
                exibirDialog("Erro", "O usuário já existe.")
            } else {
                salvarCredenciais(username, senha, tipoUsuario)
                exibirDialog("Sucesso", "Usuário cadastrado com sucesso.")

            }
        } else {
            exibirDialog("Erro", "Por favor, preencha todos os campos corretamente.")
        }
    }

    private fun usuarioExiste(username: String): Boolean {
        val arquivoCredenciais = "usuarios.txt"

        val diretorio = filesDir
        val caminhoArquivo = File(diretorio, arquivoCredenciais)

        if (!caminhoArquivo.exists()) {
            return false
        }

        BufferedReader(FileReader(caminhoArquivo)).use { reader ->
            var linha: String?
            while (reader.readLine().also { linha = it } != null) {
                val dados = linha!!.split(":")
                val usuarioArquivo = dados[0]

                if (usuarioArquivo == username) {
                    return true
                }
            }
        }
        return false
    }

    private fun salvarCredenciais(username: String, senha: String, tipoUsuario: String) {
        val dadosUsuario = "$username:$senha:$tipoUsuario"

        val diretorio = filesDir

        val nomeArquivo = "usuarios.txt"

        val caminhoArquivo = File(diretorio, nomeArquivo)

        PrintWriter(FileWriter(caminhoArquivo, true)).use { out ->
            out.println(dadosUsuario)
        }
    }

    private fun exibirDialog(titulo: String, mensagem: String) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle(titulo)
        alertDialog.setMessage(mensagem)
        alertDialog.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
        alertDialog.setOnDismissListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        alertDialog.show()
    }

    private fun irParaTelaLogin() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
