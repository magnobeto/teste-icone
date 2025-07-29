package com.sonicblue.myprojectwithleo

import android.content.ComponentName
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.sonicblue.myprojectwithleo.ui.theme.MyProjectWithLeoTheme

class MainActivity : ComponentActivity() {
    private val iconManager = IconManager()
    private val defaultIconAlias = "com.sonicblue.myprojectwithleo.DefaultIconActivity"
    private val devIconAlias = "com.sonicblue.myprojectwithleo.FlaIconActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Garante que o ícone correto esteja ativo na inicialização do app
        ensureCorrectIcon()

        setContent {
            MyProjectWithLeoTheme {
                // Lê a preferência salva para definir o estado inicial do botão
                val savedAlias = IconPersistence.getSavedIconAlias(this)
                var isDevIcon by remember {
                    mutableStateOf(savedAlias == devIconAlias)
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Leo",
                        modifier = Modifier.padding(innerPadding),
                        onClick = {
                            // Alterna o estado
                            isDevIcon = !isDevIcon

                            val aliasToEnable = if (isDevIcon) devIconAlias else defaultIconAlias

                            // 1. Salva a nova escolha permanentemente
                            IconPersistence.saveIconAlias(this, aliasToEnable)

                            // 2. Chama o método que troca o ícone com atraso
                            iconManager.setAppIcon(this, aliasToEnable)
                        }
                    )
                }
            }
        }
    }

    private fun ensureCorrectIcon() {
        // Esta função é útil caso o app seja fechado antes da troca ser completada.
        // Na próxima vez que o usuário abrir, ela garante que o ícone correto será exibido.
        val savedAlias = IconPersistence.getSavedIconAlias(this)
        if (savedAlias != null) {
            // Se houver uma preferência salva, chama o IconManager para garantir o estado.
            // Atraso não é necessário aqui, pois não há interação de UI.
            val pm = packageManager
            val allAliases = listOf(defaultIconAlias, devIconAlias)
            allAliases.forEach { alias ->
                val state = if (alias == savedAlias) {
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED
                } else {
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                }
                pm.setComponentEnabledSetting(ComponentName(this, alias), state, PackageManager.DONT_KILL_APP)
            }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    Text(
        text = "Clique para trocar o ícone, $name!",
        modifier = modifier.clickable { onClick() }
    )
}