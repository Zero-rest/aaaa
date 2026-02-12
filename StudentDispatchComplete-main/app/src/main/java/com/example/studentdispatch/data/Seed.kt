package com.example.studentdispatch.data

object Seed {
    fun sample(): List<Institution> = listOf(
        Institution(
            name = "موسسه مهاجرتی آلفا",
            country = "آلمان",
            city = "برلین",
            phone = "+49 30 000000",
            website = "https://example.com",
            description = "مشاوره پذیرش، ویزا و اپلای.",
            successfulCases = 120,
            isSponsored = true,
            adCost = 900
        ),
        Institution(
            name = "موسسه دانش‌پژوه",
            country = "کانادا",
            city = "تورنتو",
            phone = "+1 416 000000",
            website = "https://example.org",
            description = "اعزام دانشجو و اخذ پذیرش دانشگاهی.",
            successfulCases = 80,
            isSponsored = false,
            adCost = 0
        ),
        Institution(
            name = "موسسه بین‌المللی نوین",
            country = "استرالیا",
            city = "سیدنی",
            phone = "+61 2 000000",
            website = "https://example.net",
            description = "پذیرش، بیمه، اسکان و خدمات پس از ورود.",
            successfulCases = 95,
            isSponsored = true,
            adCost = 500
        )
    )
}
