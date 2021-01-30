package com.ricardoteixeira.app.framework.db.mappers.city

import com.neovisionaries.i18n.CountryCode
import com.ricardoteixeira.app.framework.db.model.city.CityDatabaseModel
import com.ricardoteixeira.app.framework.db.model.city.CityModel

fun CityModel.toDatabaseModel(): CityDatabaseModel {
    return CityDatabaseModel(
        cityId = id,
        cityName = name,
        countryCode = getName(country)
    )
}

fun getName(country: String?): String {
    return if (country.isNullOrBlank()) {
        ""
    } else {
        CountryCode.getByCode(country).getName()
    }

}
