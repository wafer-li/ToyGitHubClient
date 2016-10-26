package com.wafer.toy.github_client.model.responses

import com.google.gson.annotations.Expose
import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class App(
	@field:SerializedName("name")
	@field:Expose
	val name: String? = null,

	@field:SerializedName("url")
	@field:Expose
	val url: String? = null,

	@field:SerializedName("client_id")
	@field:Expose
	val clientId: String? = null)