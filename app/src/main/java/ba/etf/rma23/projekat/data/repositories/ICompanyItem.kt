package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.data.repositories.CompanyItem
import com.google.gson.annotations.SerializedName

data class ICompanyItem(
    @SerializedName("publisher") val publisher : Boolean,
    @SerializedName("company") val company : CompanyItem
)
