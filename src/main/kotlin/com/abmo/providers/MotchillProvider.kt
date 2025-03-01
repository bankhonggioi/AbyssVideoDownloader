package com.abmo.providers

import com.abmo.util.fetchDocument
import com.abmo.util.getParameter
import com.abmo.util.toJsoupDocument
import com.mashape.unirest.http.Unirest


class MotchillProvider: Provider {

    override fun getVideoID(url: String): String? {
        val queryParam = url.fetchDocument()
            .select("div.block-wrapper.text-center a")
            .find { it.toString().contains("type=hii") }
            ?.attr("data-href")

        val response = Unirest.post("https://motchill.taxi$queryParam")
            .header("x-requested-with", "XMLHttpRequest")
            .header("Referer", url)
            .asString().body

        val videoID = response.toJsoupDocument()
            .getElementById("playerIframe")
            ?.attr("src")?.getParameter("v")


        return videoID
    }

}