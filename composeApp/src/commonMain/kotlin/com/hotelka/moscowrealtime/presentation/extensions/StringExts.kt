package com.hotelka.moscowrealtime.presentation.extensions

import androidx.compose.runtime.Composable
import moscowrealtime.composeapp.generated.resources.Res
import moscowrealtime.composeapp.generated.resources.h
import moscowrealtime.composeapp.generated.resources.km
import moscowrealtime.composeapp.generated.resources.m
import org.jetbrains.compose.resources.stringResource

fun String.validateEmail(): Boolean {
    val emailRegex = """^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$""".toRegex()
    return matches(emailRegex)
}

fun getRandomString(length: Int) : String {
    val allowedChars = ('a'..'z') + ('0'..'9')
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}

fun String.filterEmptyChars(): String {
    return this.filterNot { char ->
        val emptyChars = setOf(
            '\u2800',
            '\u200B',
            '\u2060',
            '\uFEFF'
        )
        char in emptyChars
    }
}

fun String.filterPasswordChars(): String {
    return this.filter { char ->
        char in 'a'..'z' || char in 'A'..'Z' ||
                char in '0'..'9' ||
                char in "!@#$%^&*()_+-=[]{}|;:,.<>?~"
    }
}
fun String.filterLatinsAndSymbols(): String {
    return this.filter { char ->
        char in 'a'..'z' || char in 'A'..'Z' ||
                char in '0'..'9' || char == '_'
    }
}
fun String.cyrillicToLatinic(): String {
    val mapping = mapOf(
        'а' to "a", 'б' to "b", 'в' to "v", 'г' to "g", 'д' to "d",
        'е' to "e", 'ё' to "yo", 'ж' to "zh", 'з' to "z", 'и' to "i",
        'й' to "y", 'к' to "k", 'л' to "l", 'м' to "m", 'н' to "n",
        'о' to "o", 'п' to "p", 'р' to "r", 'с' to "s", 'т' to "t",
        'у' to "u", 'ф' to "f", 'х' to "kh", 'ц' to "ts", 'ч' to "ch",
        'ш' to "sh", 'щ' to "shch", 'ъ' to "", 'ы' to "y", 'ь' to "",
        'э' to "e", 'ю' to "yu", 'я' to "ya",
        'А' to "A", 'Б' to "B", 'В' to "V", 'Г' to "G", 'Д' to "D",
        'Е' to "E", 'Ё' to "Yo", 'Ж' to "Zh", 'З' to "Z", 'И' to "I",
        'Й' to "Y", 'К' to "K", 'Л' to "L", 'М' to "M", 'Н' to "N",
        'О' to "O", 'П' to "P", 'Р' to "R", 'С' to "S", 'Т' to "T",
        'У' to "U", 'Ф' to "F", 'Х' to "Kh", 'Ц' to "Ts", 'Ч' to "Ch",
        'Ш' to "Sh", 'Щ' to "Shch", 'Ъ' to "", 'Ы' to "Y", 'Ь' to "",
        'Э' to "E", 'Ю' to "Yu", 'Я' to "Ya"
    )

    return this.map { char ->
        when {
            char == ' ' -> '_'
            mapping.containsKey(char) -> mapping[char]!!.first()
            else -> char
        }
    }.joinToString("")
}

fun String.removeHTMLTags(): String {
    return this.replace(Regex("<.*?>"), "")
}

@Composable
fun Float.formatDuration():String{
    val totalMinutes = (this / 60).toInt()
    val hours = totalMinutes / 60
    val minutes = totalMinutes % 60

    return when {
        hours > 0 && minutes > 0 -> "${hours}${stringResource(Res.string.h)} ${minutes}${stringResource(Res.string.m)}"
        hours > 0 -> "${hours}${stringResource(Res.string.h)}"
        else -> "${minutes}${stringResource(Res.string.m)}"
    }
}

@Composable
fun Float.formatDistance():String{
    val kilometers = this / 1000
    val rounded = (kilometers * 10).toInt() / 10.0
    val integerPart = rounded.toInt()
    val decimalPart = ((rounded - integerPart) * 10).toInt()

    return if (decimalPart == 0) {
        "$integerPart.0${stringResource(Res.string.km)}"
    } else {
        "$integerPart.$decimalPart${stringResource(Res.string.km)}"
    }
}