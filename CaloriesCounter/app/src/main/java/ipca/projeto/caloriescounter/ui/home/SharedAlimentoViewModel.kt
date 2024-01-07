package ipca.projeto.caloriescounter.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ipca.projeto.caloriescounter.Alimento

class SharedAlimentoViewModel : ViewModel() {
    private val _selectedAlimentos = MutableLiveData<MutableList<Alimento>>()
    val selectedAlimentos: LiveData<MutableList<Alimento>> = _selectedAlimentos

    init {
        _selectedAlimentos.value = mutableListOf() // Initialize an empty list
    }

    fun addSelectedAlimento(alimento: Alimento) {
        _selectedAlimentos.value?.add(alimento)
        _selectedAlimentos.postValue(_selectedAlimentos.value) // Notify observers about the change
    }

    fun removeSelectedAlimento(alimento: Alimento) {
        _selectedAlimentos.value?.remove(alimento)
        _selectedAlimentos.postValue(_selectedAlimentos.value) // Notify observers about the change
    }
}