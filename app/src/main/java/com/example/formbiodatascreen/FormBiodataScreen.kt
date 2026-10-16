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
                    Text(
                        text = stringResource(id = R.string.title_form_biodata),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.title_purple)
                    )

                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_small)))

                    Text(
                        text = stringResource(id = R.string.subtitle_form_biodata),
                        fontSize = 12.sp,
                        color = colorResource(id = R.color.text_sub_gray)
                    )

                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_large)))

                    // Input Nama Depan & Belakang
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.space_small))
                    ) {
                        OutlinedTextField(
                            value = firstName,
                            onValueChange = { firstName = it },
                            placeholder = { Text(stringResource(id = R.string.hint_nama_depan)) },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(dimensionResource(id = R.dimen.input_corner_radius)),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = colorResource(id = R.color.text_field_border)
                            )
                        )
                        OutlinedTextField(
                            value = lastName,
                            onValueChange = { lastName = it },
                            placeholder = { Text(stringResource(id = R.string.hint_nama_belakang)) },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(dimensionResource(id = R.dimen.input_corner_radius)),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = colorResource(id = R.color.text_field_border)
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_medium)))

                    // Input Email
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = { Text(stringResource(id = R.string.hint_email)) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.input_corner_radius)),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = colorResource(id = R.color.text_field_border)
                        )
                    )

                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_medium)))

                    // Input Tgl Lahir & Dropdown Experience
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.space_small))
                    ) {
                        // Tanggal Lahir (bisa diketik manual / via kalender)
                        OutlinedTextField(
                            value = birthDate,
                            onValueChange = { birthDate = it },
                            placeholder = { Text(stringResource(id = R.string.hint_tgl_lahir)) },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(dimensionResource(id = R.dimen.input_corner_radius)),
                            singleLine = true,
                            trailingIcon = {
                                IconButton(onClick = { datePickerDialog.show() }) {
                                    Text(text = "📅")
                                }
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = colorResource(id = R.color.text_field_border)
                            )
                        )

                        // Experience Dropdown
                        ExposedDropdownMenuBox(
                            expanded = isDropdownExpanded,
                            onExpandedChange = { isDropdownExpanded = !isDropdownExpanded },
                            modifier = Modifier.weight(1f)
                        ) {
                            OutlinedTextField(
                                value = experience,
                                onValueChange = {},
                                readOnly = true,
                                placeholder = { Text(stringResource(id = R.string.hint_experience)) },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropdownExpanded)
                                },
                                shape = RoundedCornerShape(dimensionResource(id = R.dimen.input_corner_radius)),
                                modifier = Modifier.menuAnchor(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedBorderColor = colorResource(id = R.color.text_field_border)
                                )
                            )
                            ExposedDropdownMenu(
                                expanded = isDropdownExpanded,
                                onDismissRequest = { isDropdownExpanded = false }
                            ) {
                                experienceOptions.forEach { option ->
                                    DropdownMenuItem(
                                        text = { Text(option) },
                                        onClick = {
                                            experience = option
                                            isDropdownExpanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_medium)))

                    // Pilihan Skill
                    Text(
                        text = stringResource(id = R.string.label_pilih_skill),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.text_black),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = dimensionResource(id = R.dimen.space_small))
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = isAndroidChecked,
                                onCheckedChange = { isAndroidChecked = it }
                            )
                            Text(stringResource(id = R.string.skill_android), fontSize = 12.sp)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = isUiUxChecked,
                                onCheckedChange = { isUiUxChecked = it }
                            )
                            Text(stringResource(id = R.string.skill_uiux), fontSize = 12.sp)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = isWebChecked,
                                onCheckedChange = { isWebChecked = it }
                            )
                            Text(stringResource(id = R.string.skill_web), fontSize = 12.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_large)))

                    // Tombol Proceed
                    Button(
                        onClick = { isSubmitted = true },
                        enabled = isButtonEnabled,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(dimensionResource(id = R.dimen.button_height)),
                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.input_corner_radius)),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.btn_active_bg),
                            contentColor = colorResource(id = R.color.btn_active_text),
                            disabledContainerColor = colorResource(id = R.color.btn_disabled_bg),
                            disabledContentColor = colorResource(id = R.color.btn_disabled_text)
                        )
                    ) {
                        Text(
                            text = stringResource(id = R.string.btn_proceed),
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    // Card Hasil Input Muncul di Bawah Tombol Proceed (Hilang Otomatis setelah 5 Detik)
                    AnimatedVisibility(
                        visible = isSubmitted,
                        enter = fadeIn(),
                        exit = fadeOut(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = dimensionResource(id = R.dimen.space_medium))
                    ) {
                        Card(
                            shape = RoundedCornerShape(dimensionResource(id = R.dimen.input_corner_radius)),
                            colors = CardDefaults.cardColors(
                                containerColor = colorResource(id = R.color.popup_bg)
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = dimensionResource(id = R.dimen.card_elevation)
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(dimensionResource(id = R.dimen.card_inner_padding)),
                                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.space_small))
                            ) {
                                val skills = buildList {
                                    if (isAndroidChecked) add(stringResource(id = R.string.skill_android))
                                    if (isUiUxChecked) add(stringResource(id = R.string.skill_uiux))
                                    if (isWebChecked) add(stringResource(id = R.string.skill_web))
                                }.joinToString(", ")

                                Text(
                                    text = "Nama: $firstName $lastName",
                                    color = colorResource(id = R.color.popup_text),
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Email: $email",
                                    color = colorResource(id = R.color.popup_text)
                                )
                                Text(
                                    text = "Tgl Lahir: $birthDate",
                                    color = colorResource(id = R.color.popup_text)
                                )
                                Text(
                                    text = "Experience: $experience",
                                    color = colorResource(id = R.color.popup_text)
                                )
                                Text(
                                    text = "Skills: $skills",
                                    color = colorResource(id = R.color.popup_text)
                                )
                            }
                        }
                    }


                }
            }
        }
    }
}