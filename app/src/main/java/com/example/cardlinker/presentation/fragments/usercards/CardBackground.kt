package com.example.cardlinker.presentation.fragments.usercards

import com.example.cardlinker.R

class CardBackground {
    companion object {
        private val backgroundList = listOf(
            Triple(arrayOf("7824861", "708337"), R.drawable.lukoil, "Lukoil"),
            Triple(arrayOf("7825682", "8825882", "6820"), R.drawable.gaz_prom, "GazProm"),
            Triple(arrayOf("700436"), R.drawable.shellsmart, "Shell"),
            Triple(arrayOf("DK"), R.drawable.maxidom, "Maxidom"),
            Triple(arrayOf("700676", "708"), R.drawable.bp_gift, "British Petroleum"),
            Triple(arrayOf("627598", "600456"), R.drawable.ikea_family, "Ikea Family"),
            Triple(arrayOf("93010"), R.drawable.leroy_merlin, "Leroy Merlin"),
            Triple(arrayOf("31602"), R.drawable.obi, "OBI"),
            Triple(arrayOf("275"), R.drawable.malina, "Malina"),
            Triple(arrayOf("4285"), R.drawable.petrovich, "Petrovich"),
            Triple(arrayOf("3100"), R.drawable.bud_zdorov, "Bud Zdorov"),
            Triple(arrayOf("9016"), R.drawable.chelni, "Vazhen Kazhdiy"),
            Triple(
                arrayOf("7800", "8000", "8025", "9000", "4600", "1610", "4700"),
                R.drawable.lenta,
                "Lenta"
            ),
            Triple(arrayOf("110"), R.drawable.hoff, "Hoff"),
            Triple(arrayOf("2444", "2440", "2460"), R.drawable.okei, "O'KEI"),
            Triple(arrayOf("778977", "77890071", "267"), R.drawable.perekrestok, "Perekrestok"),
            Triple(arrayOf("3660", "2805"), R.drawable.tr6_6, "36'6"),
            Triple(
                arrayOf("381", "306", "361", "427", "305", "343", "321", "370"),
                R.drawable.sportmaster,
                "Sportmaster"
            ),
            Triple(arrayOf("2020"), R.drawable.kb, "Krasnoye & Beloye"),
            Triple(arrayOf("2041"), R.drawable.fix_price, "Fix Price"),
            Triple(
                arrayOf("2643", "7789004", "7789000", "2691", "260"),
                R.drawable.viruchai_pyat,
                "Pyaterochka"
            ),
            Triple(arrayOf("2611", "6149", "2612"), R.drawable.carusel, "Carusel"),
            Triple(arrayOf(";21", ";18"), R.drawable.reebok, "Reebok"),
            Triple(arrayOf("041", "0507", "040"), R.drawable.letual, "L'Etoile"),
            Triple(arrayOf("9990"), R.drawable.riv_gosh, "Rive Gauche"),
            Triple(arrayOf("2400"), R.drawable.m_video, "M.Video"),
            Triple(arrayOf("1100", "1150"), R.drawable.chitai_gorod, "Chitay Gorod"),
            Triple(arrayOf("7780", "7760"), R.drawable.golden_apple, "Zolotoye Yabloko"),
            Triple(arrayOf("7000", "5000"), R.drawable.kanzler, "Kanzler"),
            Triple(arrayOf(";17", ";15", ";20"), R.drawable.adidas, "Adidas"),
            Triple(arrayOf("9000", "933"), R.drawable.gloria_jeans, "Gloria Jeans"),
            Triple(arrayOf("3330"), R.drawable.nike, "Nike"),

            )

        fun getSrcAndNameIfExist(code: String): Pair<Int, String>? {
            for (backgroundTriple in backgroundList) {
                backgroundTriple.first.forEach { startCode ->
                    if (code.startsWith(startCode)) return backgroundTriple.second to backgroundTriple.third
                }
            }
            return null
        }
    }
}