package com.sonicblue.myprojectwithleo

import android.content.ComponentName
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sonicblue.myprojectwithleo.ui.theme.MyProjectWithLeoTheme

class MainActivity : ComponentActivity() {
    private val iconManager = IconManager()

    // Definição dos nomes completos dos aliases
    private val defaultIconAlias = "com.sonicblue.myprojectwithleo.DefaultIconActivity"
    private val devIconAlias = "com.sonicblue.myprojectwithleo.FlaIconActivity"
    private val chelseaIconAlias = "com.sonicblue.myprojectwithleo.ChelseaIconActivity"

    // Lista com TODOS os aliases para facilitar o gerenciamento
    private val allAliases by lazy { listOf(defaultIconAlias, devIconAlias, chelseaIconAlias) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Garante que o ícone correto esteja ativo na inicialização
        ensureCorrectIcon()

        setContent {
            MyProjectWithLeoTheme {
                // Estado único para controlar o alias selecionado
                // Inicializa com o valor salvo ou o padrão
                val initialAlias = IconPersistence.getSavedIconAlias(this) ?: defaultIconAlias
                var selectedAlias by remember { mutableStateOf(initialAlias) }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    IconSelectionScreen(
                        modifier = Modifier.padding(innerPadding),
                        aliases = mapOf(
                            "Ícone Padrão" to defaultIconAlias,
                            "Ícone Dev" to devIconAlias,
                            "Ícone Chelsea" to chelseaIconAlias
                        ),
                        currentAlias = selectedAlias,
                        onIconSelected = { newAlias ->
                            // Atualiza o estado da UI
                            selectedAlias = newAlias

                            // Salva a escolha permanentemente
                            IconPersistence.saveIconAlias(this, newAlias)
                            Toast.makeText(
                                this@MainActivity,
                                "Ícone do app será atualizado.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val savedAlias = IconPersistence.getSavedIconAlias(this)
        if (savedAlias != null) {
            iconManager.setAppIcon(this, savedAlias)
            Toast.makeText(
                this@MainActivity,
                "Atualizando ícone do app.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun ensureCorrectIcon() {
        val savedAlias = IconPersistence.getSavedIconAlias(this)
        if (savedAlias != null) {
            val pm = packageManager

            allAliases.forEach { alias ->
                val state = if (alias == savedAlias) {
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED
                } else {
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                }
                pm.setComponentEnabledSetting(
                    ComponentName(this, alias),
                    state,
                    PackageManager.DONT_KILL_APP
                )
            }
        }
    }
}

@Composable
fun IconSelectionScreen(
    modifier: Modifier = Modifier,
    aliases: Map<String, String>,
    currentAlias: String,
    onIconSelected: (String) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Escolha o ícone do seu time:", modifier = Modifier.padding(bottom = 24.dp))

        aliases.forEach { (buttonLabel, aliasName) ->
            Button(
                onClick = { onIconSelected(aliasName) },
                enabled = currentAlias != aliasName,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text(buttonLabel)
            }
        }
    }
}