package common_utils_library.utils

import common_utils_library.models.Files
import common_utils_library.models.MainTxt
import common_utils_library.models.Root
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

object GistUtils {

//    @JvmStatic
    fun getHttpClientForGitHub(accessToken: String = "", isDevelopmentMode: Boolean): HttpClient {

        return HttpClient {
            expectSuccess = true
            install(Logging) {

                logger = Logger.DEFAULT
                level = if (isDevelopmentMode) LogLevel.ALL else LogLevel.NONE
            }
            install(Auth) {
                bearer {
                    BearerTokens(
                        accessToken = accessToken,
                        refreshToken = ""
                    )
                }
            }
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    ignoreUnknownKeys = true
                })
            }
        }
    }

//    @JvmStatic
    fun getGistContents(client: HttpClient, gistId: String, isDevelopmentMode: Boolean): Root {

        var gistResponse: Root = Root(files = Files(mainTxt = MainTxt(content = "")))

        runBlocking {

            gistResponse = client.get(urlString = "https://api.github.com/gists/$gistId") {

                onDownload { bytesSentTotal, contentLength ->

                    if (isDevelopmentMode) {

                        println("Received $bytesSentTotal bytes from $contentLength")
                    }
                }
            }.body()

        }
        return gistResponse
    }
}
