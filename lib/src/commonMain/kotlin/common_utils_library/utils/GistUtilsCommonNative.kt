package common_utils_library.utils

import io.ktor.client.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import kotlin.jvm.JvmStatic

object GistUtilsCommonNative {

    @JvmStatic
    fun getHttpClientForGitHub(

        accessToken: String = "",
        isDevelopmentMode: Boolean

    ): HttpClient {

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
}
