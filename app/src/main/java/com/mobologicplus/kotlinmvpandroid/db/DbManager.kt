package com.mobologicplus.kotlinmvpandroid.db

import android.content.Context
import com.mobologicplus.kotlinmvpandroid.MainApplication
import com.mobologicplus.kotlinmvpandroid.model.Friends
import io.reactivex.Observable
import org.jetbrains.anko.db.*
import timber.log.Timber
import java.io.File
import java.util.function.BiConsumer
import java.util.stream.Collectors

/**
 * Created by sunil on 5/30/2017.
 */
class DbManager(var dbHelper: DbHelper = DbHelper(MainApplication.instance)):  DbSource{

    override fun detailFriend(friendsId: Int): Observable<Friends.User> {
        var user : Friends.User = null!!
        val post = dbHelper.use {
            select(FriendsTable.TABLE_NAME)
                    .whereSimple("${FriendsTable.ID} = ?", friendsId.toString())
                    .parseOpt(object : RowParser<Friends.User> {
                        override fun parseRow(columns: Array<Any?>): Friends.User {
                            if (columns.size > 0) {
                                user = columns[0] as Friends.User
                            }
                            return user;
                        }
                    })
        }
        return Observable.just(user)
    }


    override fun deleteFriend(friends: Friends.User): Observable<Boolean> {

        return Observable.create { subscriber -> dbHelper.use {
                try {
                    beginTransaction()
                    val result = delete(FriendsTable.TABLE_NAME, "${FriendsTable.ID} = {friendId}", "friendId" to friends.id!!.toInt()) > 0

                    if (result) {
                        setTransactionSuccessful()
                        subscriber.onNext(true)

                    } else {
                        subscriber.onError(RuntimeException("Fail to delete post"))
                    }

                } catch (e : Throwable) {
                    Timber.e(e)
                    subscriber.onError(e)

                } finally {
                    endTransaction()
                }
            }
        }
    }

    override fun loadFriends(): Observable<List<Friends.User>> {
        val friends = dbHelper.use {
            select(FriendsTable.TABLE_NAME).parseList(object : MapRowParser<List<Friends.User>> {

                override fun parseRow(columns : Map<String, Any?>) : ArrayList<Friends.User> {
                    val listValues = ArrayList<Any>(columns.values)
                    var listFrindsUser = ArrayList<Friends.User>()
                    listValues?.let {
                        for (users in it) {
                            var costingUser = users as Friends.User
                            listFrindsUser.add(costingUser)
                        }
                    }

                    return listFrindsUser
                }
            })
        }
        return Observable.fromIterable(friends)
    }

    fun getCount(): Int{
        var listFrindsUser = ArrayList<Friends.User>()
        val friends = dbHelper.use {
            select(FriendsTable.TABLE_NAME).parseList(object : MapRowParser<List<Friends.User>> {

                override fun parseRow(columns : Map<String, Any?>) : ArrayList<Friends.User> {

                    listFrindsUser.add(FriendsMapper(HashMap(columns)).toDomain())

                   /* for ((key, value) in columns) {
                      //  var users = Friends.User()
                        println("Map[$key] = $value")
                    }*/


                /*    val listValues = ArrayList<Any>(columns.values)

                    listValues?.let {
                        for (users in it) {
                            var costingUser = users as Friends.User
                            listFrindsUser.add(costingUser)
                        }
                    }*/

                    return listFrindsUser
                }
            })
        }
        return listFrindsUser.size
    }


    override fun saveFriendsList(friends: Friends.User){
       // return Observable.create { subscriber ->

            try {
                val context = dbHelper.context

                /*if (friends.id!!.toLong()== 0L) {
                    insertPost(context, friends)

                }*/

                insertPost(context, friends)

               // subscriber.onNext(friends.id!!.toLong())

            } catch (e : Throwable) {
                Timber.e(e)
                //subscriber.onError(e)
            }
        //}
    }

    private fun insertPost(context: Context, friends: Friends.User) : Boolean = dbHelper.use {
        try {
            beginTransaction()
            val postMapper = FriendsMapper(friends)
            postMapper.map.remove(FriendsTable.ID)
            var id : Int = friends.id!!.toInt()
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