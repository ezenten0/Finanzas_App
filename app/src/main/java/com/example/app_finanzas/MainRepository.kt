package com.example.app_finanzas

import com.example.app_finanzas.Domain.BudgetDomain
import com.example.app_finanzas.Domain.ExpenseDomain

class MainRepository{
    val items=mutableListOf(
        ExpenseDomain("Resturant",6000,"resturant","17 jun 2025 19:15"),
        ExpenseDomain("McDonald's",8990,"mcdonald","16 jun 2025 13:00"),
        ExpenseDomain("Cinemax",6900,"cinema","16 jun 2025 15:30"),
        ExpenseDomain("Restaurant",6000,"resturant","15 jun 2025 18:59")
    )

    val budget=mutableListOf(
        BudgetDomain("Carga familiar",1000.0,80.8),
        BudgetDomain("Suscripcion",1000.0,80.8),
        BudgetDomain("Auto",1000.0,80.8),
        BudgetDomain("Comida",1000.0,80.8)
    )
}