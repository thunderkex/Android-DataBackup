package com.xayah.databackup.ui.component

import android.app.Activity
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import com.xayah.databackup.R
import com.xayah.databackup.util.CustomSuFile
import com.xayah.databackup.util.KeyCustomSuFile
import com.xayah.databackup.util.ProcessHelper
import com.xayah.databackup.util.readString
import com.xayah.databackup.util.saveString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Composable
fun CustomSUFileDialog(
    onDismissRequest: () -> Unit,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var text by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(runBlocking { context.readString(CustomSuFile).first() }))
    }
    var isError by rememberSaveable { mutableStateOf(false) }
    AlertDialog(
        title = { Text(text = stringResource(R.string.custom_su_file)) },
        text = {
            OutlinedTextField(
                value = text,
                onValueChange = {
                    isError = it.text.isBlank()
                    text = it
                },
                isError = isError,
                label = { Text(text = stringResource(R.string.file)) },
                supportingText = { Text(text = stringResource(R.string.restart_to_take_effect)) }
            )
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                enabled = isError.not(),
                onClick = {
                    scope.launch(Dispatchers.Default) {
                        context.saveString(KeyCustomSuFile, text.text)
                        onDismissRequest()
                        ProcessHelper.killSelf(context as Activity)
                    }
                }
            ) {
                Text(text = stringResource(R.string.confirm))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(text = stringResource(R.string.dismiss))
            }
        }
    )
}
