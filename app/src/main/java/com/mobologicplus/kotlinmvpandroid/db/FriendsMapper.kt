package com.mobologicplus.kotlinmvpandroid.db

import com.mobologicplus.kotlinmvpandroid.model.Friends
import com.mobologicplus.kotlinmvpandroid.model.Friends.User
import java.util.*

/**
 * Created by sunil on 5/30/2017.
 */
class FriendsMapper(var map : MutableMap<String, Any?>){

    var _id: Long by map
    var friendId : Int by map
    var name : String by map
    var email : String by map

    constructor(user : Friends.User): this(HashMap()){
        this._id = user.id!!.toLong()
        this.friendId = user.id!!.toInt()
        this.name = user.name!!
        this.email =user.email!!
    }


    fun toDomain(): Friends.User = Friends.User(_id.toString(), name, email)
}