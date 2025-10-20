package com.example.app_finanzas

import com.example.app_finanzas.Domain.ExpenseDomain

class MainRepository{
    val items=mutableListOf(
        ExpenseDomain("Resturant",6000,"resturant","17 jun 2025 19:15"),
        ExpenseDomain("McDonald's",8990,"mcdonald","16 jun 2025 13:00"),
        ExpenseDomain("Cinemax",6900,"cinema","16 jun 2025 15:30"),
        ExpenseDomain("Restaurant",6000,"resturant","15 jun 2025 18:59")
    )
}