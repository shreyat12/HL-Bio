package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.ai.client.generativeai.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import com.google.ai.client.generativeai.GenerativeModel
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.google.ai.client.generativeai.type.BlockThreshold
import com.google.ai.client.generativeai.type.HarmCategory
import com.google.ai.client.generativeai.type.SafetySetting
import com.google.ai.client.generativeai.type.content
import com.google.ai.client.generativeai.type.generationConfig
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    private val apiKey = "AIzaSyBBa1ROAQxjkYXlqaX5n0GubY4K72GfAZo"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val generativeModel = createGenerativeModel()
                    SummarizeRoute(generativeModel)
                }
            }
        }
    }

    @Composable
    fun SummarizeRoute(generativeModel: GenerativeModel) {
        var prompt by remember { mutableStateOf("") }
        var responseText by remember { mutableStateOf("") }
        val coroutineScope = rememberCoroutineScope()

        Column(modifier = Modifier.padding(16.dp)) {
            // Text field for user input
            TextField(
                value = prompt,
                onValueChange = { prompt = it },
                label = { Text("Enter your story prompt") }
            )

            Button(onClick = {
                coroutineScope.launch {
                    val chatHistory = listOf(
                        content("user") {
                            text("you are DinoSnore, a bedtime story generator")
                        },
                        content("model") {
                            text("**Welcome to DinoSnore, your bedtime story generator!**\n\nI'm DinoSnore, a friendly dinosaur who loves to tell bedtime stories. I can create unique and engaging stories that will help you drift off to sleep peacefully.\n\n**How it works:**\n\n1. **Choose a theme:** Select a theme for your story, such as \"adventure,\" \"fantasy,\" or \"science fiction.\"\n2. **Enter a few details:** Provide me with some basic information about your desired story, such as the main character's name and any specific elements you'd like to include.\n3. **Let me weave my magic:** I'll use my imagination and storytelling skills to generate a captivating bedtime story based on your input.\n\n**Features:**\n\n* **Personalized stories:** Each story is tailored to your preferences and requests.\n* **Varied themes:** Explore different worlds and embark on exciting adventures with our wide range of themes.\n* **Captivating characters:** Meet unforgettable characters that will spark your imagination and ignite your dreams.\n* **Soothing narrator:** Listen to my calming voice as I guide you through the enchanting world of DinoSnore.\n\n**Benefits:**\n\n* **Relaxation:** DinoSnore's soothing stories promote relaxation and help you wind down before bed.\n* **Imagination development:** Spark your imagination and expand your creativity through imaginative storytelling.\n* **Fall asleep easily:** Drift off to sleep peacefully with DinoSnore's calming voice and engaging stories.\n\n**Try it now:**\n\nClick on the \"Generate Story\" button to get started! Let me take you on a magical adventure that will lull you into sweet slumber.\n\n**Sweet dreams, little dreamer!**")
                        }
                    )

                    val chat = generativeModel.startChat(chatHistory)
                    val response = chat.sendMessage(prompt)
                    responseText =
                        response.text.toString() // Assuming response.text is the desired output
                }
            }) {
                Text("Generate Story")
            }

            Text(responseText) // Display the response
        }
    }

    private fun createGenerativeModel(): GenerativeModel {
        val config = generationConfig {
            temperature = 0.9f
            topK = 1
            topP = 1f
            maxOutputTokens = 2048
        }
        val safetySettings = listOf(
            SafetySetting(HarmCategory.HARASSMENT, BlockThreshold.MEDIUM_AND_ABOVE),
            SafetySetting(HarmCategory.HATE_SPEECH, BlockThreshold.MEDIUM_AND_ABOVE),
            SafetySetting(HarmCategory.SEXUALLY_EXPLICIT, BlockThreshold.MEDIUM_AND_ABOVE),
            SafetySetting(HarmCategory.DANGEROUS_CONTENT, BlockThreshold.MEDIUM_AND_ABOVE),
        )
        return GenerativeModel(
            "gemini-1.0-pro",
            apiKey,
            config,
            safetySettings
        )
    }


    @Composable
    fun SummarizeScreen(
        uiState: SummarizeUiState = SummarizeUiState.Initial,
        onSummarizeClicked: (String) -> Unit = {}
    ) {
        var prompt by remember { mutableStateOf("") }
        Column(
            modifier = Modifier
                .padding(all = 8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row {
                TextField(
                    value = prompt,
                    label = { Text(stringResource(R.string.summarize_label)) },
                    placeholder = { Text(stringResource(R.string.summarize_hint)) },
                    onValueChange = { prompt = it },
                    modifier = Modifier
                        .weight(8f)
                )
                TextButton(
                    onClick = {
                        if (prompt.isNotBlank()) {
                            onSummarizeClicked(prompt)
                        }
                    },
                    modifier = Modifier
                        .weight(2f)
                        .padding(all = 4.dp)
                        .align(Alignment.CenterVertically)
                ) {
                    Text(stringResource(R.string.action_go))
                }
            }
            when (uiState) {
                SummarizeUiState.Initial -> {
                    // Nothing is shown
                }

                SummarizeUiState.Loading -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .padding(all = 8.dp)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is SummarizeUiState.Success -> {
                    Row(modifier = Modifier.padding(all = 8.dp)) {
                        Icon(
                            Icons.Outlined.Person,
                            contentDescription = "Person Icon"
                        )
                        Text(
                            text = uiState.outputText,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }
                }

                is SummarizeUiState.Error -> {
                    Text(
                        text = uiState.errorMessage,
                        color = Color.Red,
                        modifier = Modifier.padding(all = 8.dp)
                    )
                }
            }
        }
    }

    @Composable
    @Preview(showSystemUi = true)
    fun SummarizeScreenPreview() {
        SummarizeScreen()
    }
}
