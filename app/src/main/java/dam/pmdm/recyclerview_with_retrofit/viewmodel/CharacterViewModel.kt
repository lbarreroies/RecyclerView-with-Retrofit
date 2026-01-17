package dam.pmdm.recyclerview_with_retrofit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dam.pmdm.recyclerview_with_retrofit.data.model.Item
import dam.pmdm.recyclerview_with_retrofit.data.repository.CharacterRepository
import kotlinx.coroutines.launch

class CharacterViewModel : ViewModel() {

    private val repository = CharacterRepository()

    private val _characters = MutableLiveData<List<Item>>()
    val characters: LiveData<List<Item>> = _characters

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error

    fun loadCharacters() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                _characters.value = repository.getCharacters()
            } catch (e: Exception) {
                _characters.value = emptyList()
                _error.value = e.message ?: "Unknown error"
            } finally {
                _loading.value = false
            }
        }
    }
}