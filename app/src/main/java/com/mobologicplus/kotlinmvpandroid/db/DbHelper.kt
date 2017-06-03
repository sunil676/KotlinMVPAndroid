package com.mobologicplus.kotlinmvpandroid.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.mobologicplus.kotlinmvpandroid.MainApplication
import org.jetbrains.anko.db.*

/**
 * Created by sunil on 5/30/2017.
 */
class DbHelper(var context : Context = MainApplication.instance) : ManagedSQLiteOpenHelper(context, DB_NAME, null, DB_VERSION){

    override fun onCreate(db: SQLiteDatabase?) {
       // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        db!!.createTable(FriendsTable.TABLE_NAME, true, FriendsTable.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT, FriendsTable.FRIEND_ID to TEXT  ,
                FriendsTable.NAME to TEXT, FriendsTable.EMAIL to TEXT
                )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        @JvmField
        val DB_VERSION = 1
        @JvmField
        val DB_NAME = "dbFriends"
    }
}