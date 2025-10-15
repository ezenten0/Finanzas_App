package com.example.app_finanzas.Domain

import java.io.Serializable


data class ExpenseDomain(
    val title: String="",
    val price: Int=0,
    val pic: String="",
    val time: String=""
): Serializable
