package com.example.desafiologin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import java.io.*



class SegundaTela : AppCompatActivity() {
    private lateinit var novo_usuario: EditText
    private lateinit var nova_senha: EditText
    private lateinit var bt_salvar: Button
    private lateinit var bt_voltar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_segunda_tela)

        novo_usuario = findViewById(R.id.novo_usuario)
        nova_senha = findViewById(R.id.nova_senha)
        bt_salvar = findViewById(R.id.bt_salvar)
        bt_voltar = findViewById(R.id.bt_voltar)

        bt_salvar.isEnabled = false

        novo_usuario.setOnFocusChangeListener { _, _ -> validarCampos() }
        nova_senha.setOnFocusChangeListener { _, _ -> validarCampos() }

        bt_salvar.setOnClickListener {
            salvarCredenciais()
        }

        bt_voltar.setOnClickListener {
            voltarParaLogin()
        }
    }

    private fun validarCampos() {
        bt_salvar.isEnabled = novo_usuario.text.isNotEmpty() && nova_senha.text.isNotEmpty()
    }

    private fun salvarCredenciais() {
        val novoUsuario = novo_usuario.text.toString()
        val novaSenha = nova_senha.text.toString()

        val diretorio = filesDir
        val nomeArquivo = "usuarios.txt"
        val caminhoArquivo = File(diretorio, nomeArquivo)

        try {
            val linhas = caminhoArquivo.readLines()

            PrintWriter(FileWriter(caminhoArquivo)).use { out ->
                for (linha in linhas) {

                    val dados = linha.split(":")
                    val usuarioAntigo = dados[0]

                    if (usuarioAntigo != novoUsuario) {
                        out.println(linha)
                    }
                }
                out.println("$novoUsuario:$novaSenha")
            }

            exibirDialog("Credenciais atualizadas com sucesso!")

        } catch (e: IOException) {
            exibirDialog("Erro ao atualizar credenciais.")
        }
    }

    private fun exibirDialog(mensagem: String) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Sucesso")
        alertDialog.setMessage(mensagem)
        alertDialog.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
        alertDialog.setOnDismissListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        alertDialog.show()

    }

    private fun voltarParaLogin() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }


}
