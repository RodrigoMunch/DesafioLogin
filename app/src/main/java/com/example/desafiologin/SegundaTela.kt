package com.example.desafiologin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.desafiologin.AppGlobals


class SegundaTela : AppCompatActivity() {
    private lateinit var novo_usuario: EditText
    private lateinit var nova_senha: EditText
    private lateinit var bt_salvar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_segunda_tela)

        novo_usuario = findViewById(R.id.novo_usuario)
        nova_senha = findViewById(R.id.nova_senha)
        bt_salvar = findViewById(R.id.bt_salvar)

        bt_salvar.isEnabled = false

        novo_usuario.setOnFocusChangeListener { _, _ -> validarCampos() }
        nova_senha.setOnFocusChangeListener { _, _ -> validarCampos() }

        bt_salvar.setOnClickListener {
            salvarCredenciais()
        }
    }

    private fun validarCampos() {
        bt_salvar.isEnabled = novo_usuario.text.isNotEmpty() && nova_senha.text.isNotEmpty()
    }

    private fun salvarCredenciais() {
        // Aqui você irá obter os novos valores do usuário e senha e atualizar as variáveis padrão
        val novoUsuario = novo_usuario.text.toString()
        val novaSenha = nova_senha.text.toString()

        // Atualizar os valores padrão com os novos valores obtidos
        AppGlobals.usuarioPadrao = novoUsuario
        AppGlobals.senhaPadrao = novaSenha

        // Exibir mensagem de sucesso
        exibirDialog("Credenciais atualizadas com sucesso!")

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


}