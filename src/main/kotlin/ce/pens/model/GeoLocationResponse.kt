package ce.pens.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class GeoLocationResponse(
    val ip: String,
    val success: Boolean,
    val type: String,
    val continent: String,
    val continentCode: String,
    val country: String,
    val country_code: String,
    val country_flag: String,
    val country_capital: String,
    val country_phone: String,
    val country_neighbours: String,
    val region: String,
    val city: String,
    val latitude: Float,
    val longitude : Float,
    val asn: String,
    val org: String,
    val isp: String,
    val timezone: String,
    val timezone_name: String,
    val timezone_dstOffset: Int,
    val timezone_gmtOffset: Int,
    val timezone_gmt: String,
    val currency: String,
    val currency_code: String,
    val currency_symbol: String,
    val currency_rates: Float,
    val currency_plural: String
)
