package account_ledger_library.utils

import common_utils_library.utils.GistUtilsCommonNative
import common_utils_library.utils.GistUtilsInteractiveCommonNative
import kotlin.jvm.JvmStatic

class GistUtilsNative {

    companion object {

        @JvmStatic
        fun getGistContent(

            gitHubAccessToken: String,
            gistId: String,
            isDevelopmentMode: Boolean

        ): List<String> {

            // TODO: inline use of serialization : for file name in gist
            return GistUtilsInteractiveCommonNative.getGistContents(

                client = GistUtilsCommonNative.getHttpClientForGitHub(

                    accessToken = gitHubAccessToken,
                    isDevelopmentMode = isDevelopmentMode
                ),
                gistId = gistId,
                isDevelopmentMode = isDevelopmentMode

            ).files.mainTxt.content.lines()
        }
    }
}
