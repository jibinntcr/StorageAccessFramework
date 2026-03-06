package com.example.storageaccessframework

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.storageaccessframework.ui.theme.StorageAccessFrameworkTheme

class MainActivity : ComponentActivity() {

    // 1. Define a state variable to track the selected file name in the UI
    private var selectedFileName by mutableStateOf("No file selected")

    // 2. The Launcher: Handles the secure handoff (URI) from the system [cite: 353, 355]
    private val openDocLauncher = registerForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        if (uri != null) {
            // Persist the permission so the app can access the file even after a restart [cite: 357, 358]
            contentResolver.takePersistableUriPermission(
                uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )

            // Update UI with the secure URI link [cite: 362]
            selectedFileName = "File Linked: ${uri.lastPathSegment}"
            Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StorageAccessFrameworkTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    StorageLabScreen(
                        fileName = selectedFileName,
                        modifier = Modifier.padding(innerPadding),
                        onPickFile = {
                            // 3. Launch the picker for PDFs or Images [cite: 370]
                            openDocLauncher.launch(arrayOf("application/pdf", "image/*"))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun StorageLabScreen(fileName: String, modifier: Modifier = Modifier, onPickFile: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Self-Explanatory UI Headers
        Text(
            text = "BITS Pilani SDPD Lab",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(text = "External File Access (SAF)", fontSize = 14.sp)

        Spacer(modifier = Modifier.height(40.dp))

        // Status Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Text(
                text = fileName,
                modifier = Modifier.padding(16.dp),
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Main Action Button
        Button(
            onClick = onPickFile,
            modifier = Modifier.height(56.dp).fillMaxWidth(0.7f)
        ) {
            Text("Browse & Select File")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Note: No special manifest permissions required.",
            fontSize = 10.sp,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}