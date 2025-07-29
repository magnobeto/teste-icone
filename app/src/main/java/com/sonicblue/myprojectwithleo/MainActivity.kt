package com.sonicblue.myprojectwithleo

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
import androidx.compose.ui.tooling.preview.Preview
import com.sonicblue.myprojectwithleo.ui.theme.MyProjectWithLeoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val iconManager = IconManager()
        setContent {
            MyProjectWithLeoTheme {
                var isClicked by remember { mutableStateOf(false) }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Leo",
                        modifier = Modifier.padding(innerPadding),
                        onClick = {
                            isClicked = !isClicked
                            if (isClicked) {
                                iconManager.setAppIcon(this, R.drawable.ic_dev_tranquilao)
                            } else {
                                iconManager.setAppIcon(this, R.drawable.ic_launcher_foreground)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    Text(
        text = "Hello $name!",
        modifier = modifier.clickable { onClick() }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyProjectWithLeoTheme {
        Greeting("Android")
    }
}