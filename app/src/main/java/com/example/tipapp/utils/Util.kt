package com.example.tipapp.utils

fun calculateTotalTip(
    totalBill: Double,
    tipPercentage:Int): Double {
    return if (totalBill.toInt() > 1) {
        (totalBill*tipPercentage)/100
    }else{
        0.0
    }
}

fun calculateTotalAmountPerPerson(
    totalBill: Double,
    tipPercentage:Int,
    persons:Int) : Double{
    val billWithTip=totalBill+ calculateTotalTip(totalBill = totalBill,tipPercentage = tipPercentage)
    return if (totalBill.toInt()>1){
        (billWithTip)/persons
    }else{
        0.0
    }
}