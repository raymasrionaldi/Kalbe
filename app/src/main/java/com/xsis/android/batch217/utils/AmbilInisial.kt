package com.xsis.android.batch217.utils

fun ambilDuaInisial(nama: String): String {
    var inisial = ""
    if (nama.isNotBlank()) {
        val arrayKata = nama.split(" ")
        for (i in 0 until arrayKata.size) {
            if (i > 1 || !arrayKata[i][0].isLetter()) {
                break
            }
            inisial += "${arrayKata[i][0]}".toUpperCase()
        }
    }
    return inisial
}

fun ambilSemuaInisial(nama: String): String {
    var inisial = ""
    if (nama.isNotBlank()) {
        val arrayKata = nama.split(" ")
        for (i in 0 until arrayKata.size) {
            if (!arrayKata[i][0].isLetter()) {
                break
            }
            inisial += "${arrayKata[i][0]}".toUpperCase()
        }
    }
    return inisial
}