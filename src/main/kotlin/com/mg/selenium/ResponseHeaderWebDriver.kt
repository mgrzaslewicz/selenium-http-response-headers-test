package com.mg.selenium

import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions

class ResponseHeaderWebDriver(chromeOptions: ChromeOptions) : ChromeDriver(chromeOptions) {
    @Suppress("UNCHECKED_CAST")
    fun navigateTo(url: String): Map<String, String> {
        navigate().to(url)
        // https://developer.mozilla.org/en-US/docs/Web/API/XMLHttpRequest/getAllResponseHeaders
        // No request is sent, browser uses cache. Checked on chrome 119.0.6045.105, firefox 119.0 and edge 119.0.2151.44
        val jsResult = executeScript(
            """
                var r = new XMLHttpRequest();
                r.open('HEAD', window.location, false);
                r.send(null);
                var headersString = r.getAllResponseHeaders();
                const arr = headersString.trim().split(/[\r\n]+/);
                const headerMap = {};
                arr.forEach((line) => {
                  const parts = line.split(": ");
                  const header = parts.shift();
                  const value = parts.join(": ");
                  headerMap[header] = value;
                });
                return headerMap;
        """.trimIndent()
        )
        return (jsResult as Map<String, String>).mapKeys { it.key.lowercase() }
    }
}
