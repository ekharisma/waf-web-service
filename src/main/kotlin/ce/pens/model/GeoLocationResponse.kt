package ce.pens.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GeoLocationResponse(
    @SerialName("ip")
    val ip: String,
    @SerialName("success")
    val success: Boolean,
    @SerialName("type")
    val type: String,
    @SerialName("continent")
    val continent: String,
    @SerialName("continent_code")
    val continentCode: String,
    @SerialName("country")
    val country: String,
    @SerialName("country_code")
    val countryCode: String,
    @SerialName("country_flag")
    val countryFlag: String,
    @SerialName("country_capital")
    val countryCapital: String,
    @SerialName("country_phone")
    val countryPhone: String,
    @SerialName("country_neighbours")
    val country_neighbours: String,
    @SerialName("region")
    val region: String,
    @SerialName("city")
    val city: String,
    @SerialName("latitude")
    val latitude: Float,
    @SerialName("longitude")
    val longitude : Float,
    @SerialName("asn")
    val asn: String,
    @SerialName("org")
    val org: String,
    @SerialName("isp")
    val isp: String,
    @SerialName("timezone")
    val timezone: String,
    @SerialName("timezone_name")
    val timezoneName: String,
    @SerialName("timezone_dstOffset")
    val timezoneDstOffset: Int,
    @SerialName("timezone_gmtOffset")
    val timezoneGmtOffset: Int,
    @SerialName("timezone_gmt")
    val timezoneGmt: String,
    @SerialName("currency")
    val currency: String,
    @SerialName("currency_code")
    val currency_code: String,
    @SerialName("currency_symbol")
    val currencySymbol: String,
    @SerialName("currency_rates")
    val currencyRates: Float,
    @SerialName("currency_plural")
    val currencyPlural: String
)
