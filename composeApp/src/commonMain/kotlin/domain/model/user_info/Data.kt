package domain.model.user_info

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val credit: List<Credit>?,
    val domofon: Domofon?,
    val dvr: List<Dvr>?,
    val profile: ProfileDomain,
    val services: List<Service>?,
    val additionalAddresses: List<AdditionalAddresses>? = null
)