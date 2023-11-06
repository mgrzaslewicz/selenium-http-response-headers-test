package com.mg.selenium

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.openqa.selenium.chrome.ChromeOptions


class ResponseHeaderFromWebDriverTest {
    @Test
    fun shouldGetRequestHeaderFromWebDriver() {
        val webDriver = ResponseHeaderWebDriver(ChromeOptions().addArguments("headless"))
        val response = webDriver.navigateTo("https://jira.atlassian.com")
        assertThat(response["x-anodeid"]).isNotEmpty
    }
}
