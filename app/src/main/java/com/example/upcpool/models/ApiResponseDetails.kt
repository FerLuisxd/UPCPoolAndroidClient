package com.example.upcpool.models

import com.google.gson.annotations.SerializedName

data class ApiResponseDetails(
    @SerializedName("page")
    var page:Int,
    @SerializedName("total_results")
    var total_results:Int,
    @SerializedName("total_pages")
    var total_pages:Int,
    @SerializedName("results")
    var results: List<Room>
) {
}