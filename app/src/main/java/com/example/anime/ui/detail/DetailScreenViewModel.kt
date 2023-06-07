package com.example.anime.ui.detail

import androidx.lifecycle.ViewModel
import com.example.data.usecases.local.getanimebyidusecase.GetAnimeByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(val getAnimeByIdUseCase: GetAnimeByIdUseCase) : ViewModel(){
    init {
        val something = getAnimeByIdUseCase("id")
    }

}