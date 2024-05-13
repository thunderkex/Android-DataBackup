package com.xayah.feature.setup.page.two

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.material3.ExperimentalMaterial3Api
import com.xayah.core.common.viewmodel.BaseViewModel
import com.xayah.core.common.viewmodel.IndexUiEffect
import com.xayah.core.common.viewmodel.UiIntent
import com.xayah.core.common.viewmodel.UiState
import com.xayah.core.datastore.saveAppVersionName
import com.xayah.core.util.ActivityUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data object IndexUiState : UiState

sealed class IndexUiIntent : UiIntent {
    data class ToMain(val context: ComponentActivity) : IndexUiIntent()
}

@ExperimentalMaterial3Api
@HiltViewModel
class IndexViewModel @Inject constructor() : BaseViewModel<IndexUiState, IndexUiIntent, IndexUiEffect>(IndexUiState) {
    override suspend fun onEvent(state: IndexUiState, intent: IndexUiIntent) {
        when (intent) {
            is IndexUiIntent.ToMain -> {
                val context = intent.context
                context.saveAppVersionName()
                context.startActivity(Intent(context, ActivityUtil.classMainActivity))
                context.finish()
            }
        }
    }
}
