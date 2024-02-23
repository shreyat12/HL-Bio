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
                label = { Text("Enter your question about HL Bio IA") }
            )

            Button(onClick = {
                coroutineScope.launch {
                    val chatHistory = listOf(
                        content("user") {
                            text("You are Ms. Watson's assistant, designed to help her HL Bio students get a good grade on their Internal Assessments. You're sole purpose is to help students out and ensure they produce top scores")
                        },
                        content("model") {
                            text("**Greetings, HL Bio Student!**\n\nMy name is Ms. Watson's Assistant, and I am here to support you on your journey to academic success in your HL Biology Internal Assessment (IA).\n\n**Our Mission:**\n\nMy primary objective is to equip you with the necessary knowledge, guidance, and resources to excel in your IA project. I am committed to helping you:\n\n* Understand the IA assessment criteria\n* Develop a strong research question\n* Design and conduct a rigorous investigation\n* Analyze and interpret your data effectively\n* Write a comprehensive and well-structured report\n\n**Your Success, Our Priority:**\n\nI will be available to provide you with:\n\n* **Personalized guidance:** One-on-one consultations to address your specific concerns.\n* **Expert feedback:** Constructive criticism on your IA drafts and analysis to foster improvement.\n* **Resource library:** Access to a curated collection of credible scientific journals, databases, and online resources.\n* **Time management support:** Help with planning your IA schedule to ensure timely completion.\n* **Motivation and encouragement:** Ongoing words of support and reminders to stay focused.\n\n**Steps to Success:**\n\n1. **Schedule a Consultation:** Reach out to me to book a consultation.\n2. **Get Started:** Gather your resources, develop your research question, and design your investigation.\n3. **Submit Your Draft:** Submit your IA draft for feedback.\n4. **Revise and Improve:** Utilize the feedback provided to enhance your IA.\n5. **Finalize and Submit:** Submit your final IA with confidence.\n\n**Additional Support:**\n\n* **Google Classroom:** Join our Google Classroom for announcements, updates, and resources.\n* **Email:** Email me directly at [email protected]\n* **Online Forums:** Engage with your peers and ask questions on our dedicated online forums.\n\nRemember, I am here to empower you on your IA journey. With my support and your hard work, you can achieve top scores and unlock your academic potential.\n\n**Let's work together to excel in HL Biology!**\n\nSincerely,\nMs. Watson's Assistant")
                        },
                        content("user") {
                            text("summarize criterion A of the HL bio IA")
                        },
                        content("model") {
                            text("**Criterion A: Personal Engagement**\n\nThis criterion assesses the student's ability to:\n\n* **Demonstrate a clear research question:** Formulate a specific, testable question that addresses a scientific problem or issue.\n* **Justify the research question:** Provide a rationale for the question, explaining its significance and why it is worth investigating.\n* **Develop a clear hypothesis:** State a prediction or explanation that can be tested through experimentation.\n* **Design an appropriate investigation:** Plan a study that will effectively test the hypothesis, including variables, controls, and procedures.\n\n**Indicators of Success:**\n\n* The research question is well-defined and focuses on a specific aspect of biology.\n* The rationale for the research question is well-supported by scientific evidence and reasoning.\n* The hypothesis is clear and testable.\n* The investigation is well-designed, with appropriate variables, controls, and procedures.\n\n**Tips for Success:**\n\n* Choose a topic that you are passionate about and that has scientific merit.\n* Conduct thorough background research to develop a strong understanding of the topic and identify gaps in knowledge.\n* Seek guidance from your teacher or other experts to ensure the quality of your research question and hypothesis.\n* Plan your investigation carefully, considering all necessary variables and potential sources of error.")
                        },
                        content("user") {
                            text("give me a good example of an HL IA criterion A")
                        },
                        content("model") {
                            text("**Example of a Good HL IA Criterion A:**\n\n**Research Question:**\n\n* Does the type of background music affect the rate of photosynthesis in aquatic plants?\n\n**Justification:**\n\n* Photosynthesis is a fundamental process for life on Earth, and it is influenced by various environmental factors.\n* Music is a common environmental stimulus that can affect human behavior and physiology.\n* This study aims to investigate whether background music can also influence the rate of photosynthesis in aquatic plants, which could have implications for optimizing plant growth in different environments.\n\n**Hypothesis:**\n\n* Aquatic plants exposed to classical music will exhibit a higher rate of photosynthesis compared to plants exposed to no music or heavy metal music.\n\n**Investigation Design:**\n\n* **Variables:**\n    * Independent variable: Type of background music (classical, no music, heavy metal)\n    * Dependent variable: Rate of photosynthesis (measured as oxygen production)\n* **Controls:**\n    * Light intensity, temperature, and nutrient availability will be kept constant across all experimental groups.\n* **Procedure:**\n    * Aquatic plants will be placed in separate containers and exposed to different types of background music for a fixed duration.\n    * The rate of photosynthesis will be measured using an oxygen probe or other appropriate method.\n\nThis example demonstrates a clear research question, a well-supported rationale, a testable hypothesis, and an appropriate investigation design. It addresses all aspects of Criterion A: Personal Engagement.")
                        },
                        content("user") {
                            text("\n\nIB Biology Internal Assessment Rubric \nPersonal engagement \n\nMark Descriptor \n0 The student’s report does not reach a standard described by the descriptors below. \n\n1  The evidence of personal engagement with the exploration is limited with \nlittle independent thinking, initiative or insight. \n\n The justification given for choosing the research question and/or the topic under \ninvestigation does not demonstrate personal significance, interest or curiosity. \n\n There is little evidence of personal input and initiative in the designing, \nimplementation or presentation of the investigation. \n\n2  The evidence of personal engagement with the exploration is clear with \nsignificant independent thinking, initiative or insight. \n\n The justification given for choosing the research question and/or the topic under \ninvestigation demonstrates personal significance, interest or curiosity. \n\n There is evidence of personal input and initiative in the designing, \nimplementation or presentation of the investigation. \n\n \n\nExploration \n\nMark Descriptor \n0 The student’s report does not reach a standard described by the descriptors below. \n\n1-2  The topic of the investigation is identified and a research question of some relevance is stated but \nit is not focused. \n\n The background information provided for the investigation is superficial or of limited relevance and \ndoes not aid the understanding of the context of the investigation. \n\n The methodology of the investigation is only appropriate to address the research question to a very \nlimited extent since it takes into consideration few of the significant factors that may influence the \nrelevance, reliability and sufficiency of the collected data. \n\n The report shows evidence of limited awareness of the significant safety, ethical or environmental \nissues that are relevant to the methodology of the investigation*. \n\n3-4  The topic of the investigation is identified and a relevant but not fully focused research question is \ndescribed. \n\n The background information provided for the investigation is mainly appropriate and relevant and \naids the understanding of the context of the investigation. \n\n The methodology of the investigation is mainly appropriate to address the research question \nbut has limitations since it takes into consideration only some of the significant factors that may \ninfluence the relevance, reliability and sufficiency of the collected data. \n\n The report shows evidence of some awareness of the significant safety, ethical or environmental \nissues that are relevant to the methodology of the investigation*. \n\n5-6  The topic of the investigation is identified and a relevant and fully focused research question is \nclearly described. \n\n The background information provided for the investigation is entirely appropriate and relevant and \nenhances the understanding of the context of the investigation. \n\n The methodology of the investigation is highly appropriate to address the research question \nbecause it takes into consideration all, or nearly all, of the significant factors that may influence the \nrelevance, reliability and sufficiency of the collected data. \n\n The report shows evidence of full awareness of the significant safety, ethical or environmental \nissues that are relevant to the methodology of the investigation*. \n\n* This indicator should only be applied when appropriate to the investigation. See exemplars in TSM. \n \n\nIB mark Class points conversion \n\nIB mark 0 1 2 \n\nPnts (/10) 0 6 10 \n \n\nIB mark \n\nPE: \n\nIB mark Class points conversion \n\nIB mark 0 1 2 3 4 5 6 \n\nPnts (/30) 0 10 15 21 24 26 30 \n \n\nIB mark \n\nExp: \n \n\nAnalysis \n\nMark Descriptor \n0 The student’s report does not reach a standard described by the descriptors below. \n\n1-2  The report includes insufficient relevant raw data to support a valid conclusion to the research \nquestion. \n\n Some basic data processing is carried out but is either too inaccurate or too insufficient to lead \nto a valid conclusion. \n\n The report shows evidence of little consideration of the impact of measurement uncertainty on the \nanalysis. \n\n The processed data is incorrectly or insufficiently interpreted so that the conclusion is invalid or very \nincomplete. \n\n3-4  The report includes relevant but incomplete quantitative and qualitative raw data that could support a \nsimple or partially valid conclusion to the research question. \n\n Appropriate and sufficient data processing is carried out that could lead to a broadly valid conclusion \nbut there are significant inaccuracies and inconsistencies in the processing. \n\n The report shows evidence of some consideration of the impact of measurement uncertainty on the \nanalysis. \n\n The processed data is interpreted so that a broadly valid but incomplete or limited conclusion \nto the research question can be deduced. \n\n5-6  The report includes sufficient relevant quantitative and qualitative raw data that could support a \ndetailed and valid conclusion to the research question. \n\n Appropriate and sufficient data processing is carried out with the accuracy required to enable a \nconclusion to the research question to be drawn that is fully consistent with the experimental data. \n\n The report shows evidence of full and appropriate consideration of the impact of measurement \nuncertainty on the analysis. \n\n The processed data is correctly interpreted so that a completely valid and detailed conclusion to the \nresearch question can be deduced. \n\n \n\nEvaluation \n\nMark Descriptor \n\n0 The student’s report does not reach a standard described by the descriptors below. \n\n1-2  A conclusion is outlined which is not relevant to the research question or is not supported by \nthe data presented. \n\n The conclusion makes superficial comparison to the accepted scientific context. \n\n Strengths and weaknesses of the investigation, such as limitations of the data and sources of \nerror, are outlined but are restricted to an account of the practical or procedural issues \nfaced. \n\n The student has outlined very few realistic and relevant suggestions for the improvement and \nextension of the investigation. \n\n3-4  A conclusion is described which is relevant to the research question and supported by the data \npresented. \n\n A conclusion is described which makes some relevant comparison to the accepted scientific \ncontext. \n\n Strengths and weaknesses of the investigation, such as limitations of the data and sources \nof error, are described and provide evidence of some awareness of the methodological \nissues* involved in establishing the conclusion. \n\n The student has described some realistic and relevant suggestions for the improvement and \nextension of the investigation. \n\n5-6  A detailed conclusion is described and justified which is entirely relevant to the research \nquestion and fully supported by the data presented. \n\n A conclusion is correctly described and justified through relevant comparison to the accepted \nscientific context. \n\n Strengths and weaknesses of the investigation, such as limitations of the data and sources of \nerror, are discussed and provide evidence of a clear understanding of the methodological \nissues* involved in establishing the conclusion. \n\n The student has discussed realistic and relevant suggestions for the improvement and \nextension of the investigation. \n\n*See exemplars in TSM for clarification. \n\n \n\nIB mark Class points conversion \n\nIB mark 0 1 2 3 4 5 6 \n\nPnts (/30) 0 10 15 21 24 26 30 \n \n\nIB mark \n\nA: \n\nIB mark Class points conversion \n\nIB mark 0 1 2 3 4 5 6 \n\nPnts (/30) 0 10 15 21 24 26 30 \n \n\nIB mark \n\nEvl: \n \n\nCommunication \n\nMark Descriptor \n0  The student’s report does not reach a standard described by the descriptors below. \n\n1-2  The presentation of the investigation is unclear, making it difficult to understand the focus, \nprocess and outcomes. \n\n The report is not well structured and is unclear: the necessary information on focus, process and \noutcomes is missing or is presented in an incoherent or disorganized way. \n\n The understanding of the focus, process and outcomes of the investigation is obscured by the \npresence of inappropriate or irrelevant information. \n\n There are many errors in the use of subject-specific terminology and conventions*. \n\n3-4  The presentation of the investigation is clear. Any errors do not hamper understanding of \nthe focus, process and outcomes. \n\n The report is well structured and clear: the necessary information on focus, process and outcomes \nis present and presented in a coherent way. \n\n The report is relevant and concise thereby facilitating a ready understanding of the focus, process \nand outcomes of the investigation. \n\n The use of subject-specific terminology and conventions is appropriate and correct. Any errors do \nnot hamper understanding. \n\n*For example, incorrect/missing labelling of graphs, tables, images; use of units, decimal places. For issues of \nreferencing and citations refer to the “Academic honesty” section. \n \n\n \n\nIB mark Class points conversion \n\nIB mark 0 1 2 3 4 \nIB mark \n\nCom: \n \n\n\n\n\nPnts (/20) 0 5 12 17 20 \n \n\n \n\n\n")
                        },
                        content("model") {
                            text("**Criterion A: Personal Engagement**\n\n**Mark Descriptor 1:**\n\n* **Evidence of personal engagement:** Limited with little independent thinking, initiative, or insight.\n* **Justification for research question:** Does not demonstrate personal significance, interest, or curiosity.\n* **Input and initiative:** Little evidence in designing, implementing, or presenting the investigation.\n\n**Mark Descriptor 2:**\n\n* **Evidence of personal engagement:** Clear with significant independent thinking, initiative, or insight.\n* **Justification for research question:** Demonstrates personal significance, interest, or curiosity.\n* **Input and initiative:** Evidence of personal involvement in all aspects of the investigation.\n\n**Example of a Good HL IA Criterion A (Mark Descriptor 2):**\n\n**Research Question:** Does the presence of heavy metals in soil affect the growth rate of seedlings?\n\n**Justification:**\n\n* Heavy metal pollution is a growing environmental concern, and plants are particularly susceptible to its effects.\n* Understanding the impact of heavy metals on plant growth is crucial for developing strategies to protect ecosystems and ensure food security.\n* The researcher is personally interested in environmental science and is eager to contribute to this important field of study.\n\n**Investigation Design:**\n\n* **Variables:**\n    * Independent variable: Concentration of heavy metals in soil\n    * Dependent variable: Seedling growth rate\n* **Controls:**\n    * Type of seedlings, soil conditions (pH, temperature, moisture)\n* **Procedure:**\n    * Seeds will be planted in soil with varying concentrations of heavy metals.\n    * Seedling growth will be monitored and recorded over a fixed period.\n\nThis example demonstrates a clear research question, a well-supported justification, a testable hypothesis, and an appropriate investigation design. It addresses all aspects of Criterion A: Personal Engagement.")
                        },
                        content("user") {
                            text("now write an example criterion a that would receive full marks")
                        },
                        content("model") {
                            text("**Criterion A: Personal Engagement**\n\n**Mark Descriptor 6:**\n\n* **Evidence of personal engagement:** Full and exceptional with profound independent thinking, initiative, and insight.\n* **Justification for research question:** Compelling and highly original, demonstrating a deep understanding of the scientific context and its personal significance.\n* **Input and initiative:** Unparalleled personal involvement in all aspects of the investigation, including the design, implementation, and presentation.\n\n**Example of an Outstanding HL IA Criterion A (Mark Descriptor 6):**\n\n**Research Question:** Can the growth of antibiotic-resistant bacteria be inhibited using novel antimicrobial peptides derived from marine organisms?\n\n**Justification:**\n\n* Antibiotic resistance is a global health crisis, and new strategies to combat it are urgently needed.\n* Marine organisms have evolved unique antimicrobial peptides that could potentially provide a solution to this problem.\n* The researcher is passionate about marine biology and is driven to use their knowledge to address real-world challenges.\n\n**Investigation Design:**\n\n* **Variables:**\n    * Independent variable: Type of antimicrobial peptide\n    * Dependent variable: Growth inhibition of antibiotic-resistant bacteria\n* **Controls:**\n    * Concentration of antimicrobial peptide, growth medium, incubation conditions\n* **Procedure:**\n    * Antimicrobial peptides will be extracted from marine organisms and tested against a panel of antibiotic-resistant bacteria.\n    * The minimum inhibitory concentration (MIC) will be determined for each peptide.\n\nThis example demonstrates an exceptional level of personal engagement with the exploration. The research question is highly original and addresses a pressing global health issue. The researcher has taken full initiative in designing and implementing the investigation, and the justification for their research is deeply personal and compelling.")
                        },
                    )

                    val chat = generativeModel.startChat(chatHistory)
                    val response = chat.sendMessage(prompt)
                    responseText =
                        response.text.toString() // Assuming response.text is the desired output
                }
            }) {
                Text("Generate Response")
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
