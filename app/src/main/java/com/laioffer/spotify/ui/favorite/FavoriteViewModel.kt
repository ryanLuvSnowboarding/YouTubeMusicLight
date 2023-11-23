package com.laioffer.spotify.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.laioffer.spotify.datamodel.Album
import com.laioffer.spotify.repository.FavoriteAlbumRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteAlbumRepository: FavoriteAlbumRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoriteUiState(emptyList()))
    val uiState: StateFlow<FavoriteUiState> = _uiState
    //为什么要写两遍？=> 在MVVM中给UI使用的uiState是immutable的。反过来说，如果ui使用的是mutable的，那么ui就可以
    //自己更新自己的state，which against MVVM pattern since every state updates have to be done by
    //viewModel.


    init {
        viewModelScope.launch {
            favoriteAlbumRepository.fetchFavoriteAlbums().collect {
                _uiState.value = FavoriteUiState(it)
            }
        }
    }
    //init里面的所有操作在viewModel完成onViewCreated的时候执行
}

data class FavoriteUiState(
    val albums: List<Album>
)