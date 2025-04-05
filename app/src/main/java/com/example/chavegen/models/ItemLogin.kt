package com.example.chavegen.models

import java.util.UUID

data class ItemLogin (
    val documentId: String = UUID.randomUUID().toString(),
    val userId: String? = null,
    val siteName: String? = null,
    val siteUrl: String? = null,
    val siteUser: String? = null,
    val sitePassword: String? = null,
)

