package com.mobologicplus.kotlinmvpandroid.db

import android.content.Context
import com.mobologicplus.kotlinmvpandroid.MainApplication
import com.mobologicplus.kotlinmvpandroid.model.Friends
import io.reactivex.Observable
import org.jetbrains.anko.db.*
import timber.log.Timber

/**
 * Created by sunil on 5/30/2017.
 */
class DbManager(var dbHelper: DbHelper = DbHelper(MainApplication.instance)):  DbSource{

    override fun detailFriend(friendsId: String): Observable<Friends.User> {
        var user : Friends.User? = null
        dbHelper.use {
            select(FriendsTable.TABLE_NAME)
                    .whereSimple("${FriendsTable.FRIEND_ID} = ?", friendsId)
                    .parseOpt(object : MapRowParser<Friends.User> {
                        override fun parseRow(columns: Map<String, Any?>): Friends.User {

                                val id = columns.getValue("friend_id")
                                val name = columns.getValue("name")
                                val email = columns.getValue("email")
                                user = Friends.User(id = id.toString(), name = name.toString(), email = email.toString())

                              return user!!
                        }
                    })
        }
        return Observable.just(user)
    }


    override fun deleteFriend(friends: Friends.User): Observable<Boolean> {
       var isDeleted : Boolean? = null
       dbHelper.use {
                try {
                    beginTransaction()
                    val result = delete(FriendsTable.TABLE_NAME, "${FriendsTable.FRIEND_ID} = {friend_id}", "friend_id" to friends.id!!) > 0

                    if (result) {
                        setTransactionSuccessful()
                        isDeleted = true
                    } else {
                        isDeleted = false
                    }

                } catch (e : Throwable) {
                    Timber.e(e)
                    isDeleted = true

                } finally {
                    endTransaction()
                }
            }
       return Observable.just(isDeleted)
    }

    override fun loadFriends(): Observable<List<Friends.User>> {
        var listFrindsUser = ArrayList<Friends.User>()
        dbHelper.use {
            select(FriendsTable.TABLE_NAME).parseList(object : MapRowParser<List<Friends.User>> {

                override fun parseRow(columns : Map<String, Any?>) : ArrayList<Friends.User> {

                    val id = columns.getValue("friend_id")
                    val name = columns.getValue("name")
                    val email = columns.getValue("email")
                    val users = Friends.User(id = id.toString(), name = name.toString(), email = email.toString())
                    listFrindsUser.add(users)

                    return listFrindsUser
                }
            })
        }
        return Observable.just(listFrindsUser)
    }

    fun getCount(): Int{
        var listFrindsUser = ArrayList<Friends.User>()
        dbHelper.use {
            select(FriendsTable.TABLE_NAME).parseList(object : MapRowParser<List<Friends.User>> {

                override fun parseRow(columns : Map<String, Any?>) : ArrayList<Friends.User> {

                    val id = columns.getValue("friend_id")
                    val name = columns.getValue("name")
                    val email = columns.getValue("email")
                    val users = Friends.User(id = id.toString(), name = name.toString(), email = email.toString())
                    listFrindsUser.add(users)


                    return listFrindsUser
                }
            })
        }
        return listFrindsUser.size
    }


    override fun saveFriendsList(friends: Friends.User){
            try {
                val context = dbHelper.context
                insertPost(context, friends)

            } catch (e : Throwable) {
                Timber.e(e)
            }
    }

    private fun insertPost(context: Context, friends: Friends.User) : Boolean = dbHelper.use {
        try {
            beginTransaction()
            val insertedId = insert(FriendsTable.TABLE_NAME, FriendsTable.FRIEND_ID to friends.id, FriendsTable.NAME to friends.name, FriendsTable.EMAIL to friends.email)

            if (insertedId != -1L) {
                setTransactionSuccessful()
                true
            } else {
                false
                throw RuntimeException("Fail to insert")
            }

        } finally {
            endTransaction()
        }
    }

}