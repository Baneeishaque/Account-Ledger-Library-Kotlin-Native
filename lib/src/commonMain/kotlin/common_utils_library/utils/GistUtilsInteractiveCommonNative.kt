package common_utils_library.utils

import common_utils_library.models.Files
import common_utils_library.models.MainTxt
import common_utils_library.models.Root
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import kotlinx.coroutines.runBlocking
import kotlin.jvm.JvmStatic

object GistUtilsInteractiveCommonNative {

    @JvmStatic
    fun getGistContents(

        client: HttpClient,
        gistId: String,
        isDevelopmentMode: Boolean

    ): Root {

        var gistResponse = Root(

            files = Files(

                mainTxt = MainTxt(

                    content = ""
                )
            )
        )

        runBlocking {

            gistResponse = client.get(urlString = "https://api.github.com/gists/$gistId") {

                onDownload { bytesSentTotal: Long, contentLength: Long? ->

                    if (isDevelopmentMode) {

                        println("Received $bytesSentTotal bytes from $contentLength")
                    }
                }
            }.body()

        }
        return gistResponse
    }
}
