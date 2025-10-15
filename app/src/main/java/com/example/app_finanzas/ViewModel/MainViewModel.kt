package com.example.app_finanzas.ViewModel

import androidx.lifecycle.ViewModel
import com.example.app_finanzas.MainRepository

class MainViewModel (val repository: MainRepository) : ViewModel() {
    constructor() : this(MainRepository())

    fun loadData() = repository.items
}