package common_utils_library.models

import kotlinx.serialization.Required
import kotlinx.serialization.Serializable

@Serializable
data class IsOkModel<T>(@Required val isOK: Boolean, val data: T? = null, val error: String? = null)
