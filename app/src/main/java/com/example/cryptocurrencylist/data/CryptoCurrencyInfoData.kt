package com.example.cryptocurrencylist

data class CryptoCurrencyInfoData(
    val description: String,
    val first_data_at: String,
    val hash_algorithm: String,
    val id: String,
    val last_data_at: String,
    val links: Links,
    val name: String,
    val org_structure: String,
    val proof_type: String,
    val rank: Int,
    val started_at: String,
    val type: String,
    var symbol: String,
    val whitepaper: Whitepaper
)

data class Links(
    val explorer: List<String>,
    val facebook: List<String>,
    val reddit: List<String>,
    val source_code: List<String>,
    val website: List<String>,
    val youtube: List<String>
)



data class Whitepaper(
    val link: String,
    val thumbnail: String
)

