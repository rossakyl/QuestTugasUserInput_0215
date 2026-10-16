package com.example.formbiodatascreen

import android.app.DatePickerDialog
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormBiodataScreen() {
    val context = LocalContext.current

    // State Input Form
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("") }

    // State Checkbox Skill
    var isAndroidChecked by remember { mutableStateOf(false) }
    var isUiUxChecked by remember { mutableStateOf(false) }
    var isWebChecked by remember { mutableStateOf(false) }

    // State Dropdown
    var isDropdownExpanded by remember { mutableStateOf(false) }
    val experienceOptions = listOf("Beginner", "Intermediate", "Advanced")

    // State Notifikasi Muncul 5 Detik
    var isSubmitted by remember { mutableStateOf(false) }

    if (isSubmitted) {
        LaunchedEffect(isSubmitted) {
            delay(5000L)
            isSubmitted = false
        }
    }

    // DatePickerDialog Logic
    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            birthDate = "$dayOfMonth/${month + 1}/$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    // Validasi tombol Proceed aktif jika seluruh input terisi
    val isButtonEnabled = firstName.isNotBlank() &&
            lastName.isNotBlank() &&
            email.isNotBlank() &&
            birthDate.isNotBlank() &&
            experience.isNotBlank() &&
            (isAndroidChecked || isUiUxChecked || isWebChecked)

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        // Root Container Box: Layer floating di atas Scaffold
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(colorResource(id = R.color.bg_screen))
        ) {
            // Header Background Neon
            Image(
                painter = painterResource(id = R.drawable.bg_header_neon),
                contentDescription = stringResource(id = R.string.content_desc_header),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(id = R.dimen.header_height)),
                contentScale = ContentScale.Crop
            )

            // Form Box Mengapung (Floating Card)
            Card(
                shape = RoundedCornerShape(
                    topStart = dimensionResource(id = R.dimen.card_corner_radius),
                    topEnd = dimensionResource(id = R.dimen.card_corner_radius)
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = dimensionResource(id = R.dimen.card_elevation)
                ),
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(id = R.color.card_background)
                ),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = dimensionResource(id = R.dimen.floating_card_top_margin))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(id = R.dimen.card_inner_padding))
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                }
            }
        }
    }
}