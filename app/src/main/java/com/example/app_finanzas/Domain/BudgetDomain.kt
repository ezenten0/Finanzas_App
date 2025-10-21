package com.example.app_finanzas.Domain

import java.io.Serializable

data class BudgetDomain(
    val title:String,
    val price: Double=0.0,
    val percent: Double= 0.0
): Serializable
