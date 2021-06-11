package com.mokgethisi.letshegoassesment

import java.io.InputStreamReader

class MockResponseFileReader(path: String) {

    val content: String

    init {
        val fileInputStream = javaClass.classLoader?.getResourceAsStream(path)
        content = fileInputStream?.bufferedReader()?.readText() ?: ""
    }

}