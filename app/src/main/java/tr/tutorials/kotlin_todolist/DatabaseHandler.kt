package tr.tutorials.kotlin_todolist

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(Context: Context) :
		SQLiteOpenHelper(Context, DATABASE_NAME, null, DATABASE_VERSION){

	companion object {
		private const val DATABASE_NAME = "todoDB"
		private const val DATABASE_VERSION = 1
	}

	override fun onCreate(db: SQLiteDatabase?) {
		val UsersTableCreate = ("CREATE TABLE users ( " +
		 	"id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, passw text )")
		db?.execSQL(UsersTableCreate)
		val ContentTableCreate = ("CREATE TABLE contents ( " +
		 	"fromuser INTEGER, content TEXT NOT NULL, FOREIGN KEY(fromuser) REFERENCES users (id))")
		db?.execSQL(ContentTableCreate)
	}
}