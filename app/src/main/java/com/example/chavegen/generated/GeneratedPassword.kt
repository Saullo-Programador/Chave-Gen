package com.example.chavegen.generated

import com.example.chavegen.ui.state.GeneratedPasswordUiState


fun generatedPassword(
    uiState: GeneratedPasswordUiState = GeneratedPasswordUiState(),
): String {
    val lowerCase = "abcdefghijklmnopqrstuvwxyz"
    val upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    val numbers = "0123456789"
    val special = "!@#$%^&*()+?><:{}[]"

    var charPool = ""
    val passwordChars = mutableListOf<Char>()

    if (uiState.useLowercase) {
        charPool += lowerCase
        passwordChars.add(lowerCase.random())
    }
    if (uiState.useUppercase) {
        charPool += upperCase
        passwordChars.add(upperCase.random())
    }
    if (uiState.useNumbers) {
        charPool += numbers
        passwordChars.add(numbers.random())
    }
    if (uiState.useSymbols) {
        charPool += special
        passwordChars.add(special.random())
    }

    if (charPool.isEmpty()) return "Selecione pelo menos uma opção"


    val remainingChars = uiState.length - passwordChars.size
    repeat(remainingChars) {
        passwordChars.add(charPool.random())
    }

    val password = passwordChars.shuffled().joinToString("")
    return password

}