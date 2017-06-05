package com.mobologicplus.kotlinmvpandroid.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



/**
 * Created by sunil on 5/30/2017.
 */
class Friends {

      @SerializedName("user")
      @Expose
      var user: List<User>? = null

      class User {

            @SerializedName("id")
            @Expose
            var id: String? = null
            @SerializedName("name")
            @Expose
            var name: String? = null
            @SerializedName("email")
            @Expose
            var email: String? = null

            constructor(id: String?, name: String?, email: String?) {
                  this.id = id
                  this.name = name
                  this.email = email
            }
      }

}