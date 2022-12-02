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

	fun addUser(usr: usersModelClass): Long {
		val db = this.writableDatabase

		val contentValues = ContentValues()
		contentValues.put("name", usr.name)
		contentValues.put("passwd",  usr.passwd)

		val success = db.insert("users", null, contentValues)
		db.close()
		return success
	}

	fun addTODO(todo: contentModelClass): Long {
		val db = this.writableDatabase

		val contentValues = ContentValues()
		contentValues.put("fromuser", todo.fromuser)
		contentValues.put("content", todo.content)

		val success = db.insert("content", null, contentValues)
		db.close()
		return success
	}
}