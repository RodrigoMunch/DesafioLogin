package com.example.desafiologin

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class CadastroUsuarioActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var senhaEditText: EditText
    private lateinit var confirmarSenhaEditText: EditText
    private lateinit var tipoUsuarioSpinner: Spinner
    private lateinit var cadastrarButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_usuario)

        // Inicialize os componentes
        usernameEditText = findViewById(R.id.usernameEditText)
        senhaEditText = findViewById(R.id.senhaEditText)
        confirmarSenhaEditText = findViewById(R.id.confirmarSenhaEditText)
        tipoUsuarioSpinner = findViewById(R.id.tipoUsuarioSpinner)
        cadastrarButton = findViewById(R.id.cadastrarButton)

        // Inicialize o Spinner com as opções de tipo de usuário
        val tiposUsuarios = arrayOf("user", "adm", "mkt")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tiposUsuarios)
        tipoUsuarioSpinner.adapter = adapter

        // Defina um listener para o botão de cadastro
        cadastrarButton.setOnClickListener {
            cadastrarUsuario()
        }
    }

    private fun cadastrarUsuario() {
        val username = usernameEditText.text.toString()
        val senha = senhaEditText.text.toString()
        val confirmarSenha = confirmarSenhaEditText.text.toString()
        val tipoUsuario = tipoUsuarioSpinner.selectedItem.toString()

        // Verifique se todos os campos estão preenchidos e as senhas coincidem
        if (username.isNotEmpty() && senha.isNotEmpty() && senha == confirmarSenha) {
            // Verifique se o usuário já existe (você precisa implementar esta lógica)
            if (usuarioExiste(username)) {
                exibirDialog("Erro", "O usuário já existe.")
            } else {
                // Salve as credenciais do novo usuário (você precisa implementar esta lógica)
                salvarCredenciais(username, senha, tipoUsuario)

                // Exiba uma mensagem de sucesso
                exibirDialog("Sucesso", "Usuário cadastrado com sucesso.")

                // Redirecione o usuário de volta para a Tela de Login
                finish()
            }
        } else {
            // Caso contrário, exiba uma mensagem de erro
            exibirDialog("Erro", "Por favor, preencha todos os campos corretamente.")
        }
    }

    private fun usuarioExiste(username: String): Boolean {
        // Aqui você implementará a lógica para verificar se o usuário já existe
        // Pode ser verificando em um banco de dados, em um arquivo de persistência local, etc.
        // Retorne true se o usuário já existir, false caso contrário
        return false
    }

    private fun salvarCredenciais(username: String, senha: String, tipoUsuario: String) {
        // Aqui você implementará a lógica para salvar as credenciais do novo usuário
        // Isso pode envolver escrever em um arquivo de preferências compartilhadas, em um banco de dados local, etc.
    }

    private fun exibirDialog(titulo: String, mensagem: String) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle(titulo)
        alertDialog.setMessage(mensagem)
        alertDialog.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
        alertDialog.show()
    }
}
