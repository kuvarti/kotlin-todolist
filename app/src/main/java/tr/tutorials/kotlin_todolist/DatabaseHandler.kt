package tr.tutorials.kotlin_todolist

import android.annotation.SuppressLint //(?)
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import java.util.ArrayList

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
		addUser(usersModelClass(1, "Not Login", "Elements"))
	}

	@SuppressLint("Range")
	fun viewTODO(id: Int = 1): ArrayList<contentModelClass> {
		val	ret: ArrayList<contentModelClass> = ArrayList<contentModelClass>()

		val db = this.writableDatabase
		var cursor: Cursor? = null

		val viewlist = "SELECT content FROM contents WHERE fromuser == $id"

		try {
			cursor = db.rawQuery(viewlist,null)
		} catch (e: SQLiteException) {
			db.execSQL(viewlist)
			return ArrayList()
		}

		var fromuser: Int
		var text: String
		var td: contentModelClass

		if (cursor.moveToFirst()) {
			do {
				fromuser = cursor.getInt(cursor.getColumnIndex("fromuser"))
				text = cursor.getString(cursor.getColumnIndex("content"))

				td = contentModelClass(fromuser, text)
				ret.add(td)
			} while (cursor.moveToNext())
		}
		return  ret
	}

	fun deleteTODO(con: contentModelClass): Int{
		val db = this.writableDatabase
		val contnt = ContentValues()
		contnt.put("content", con.content)

		val success = db.delete("contents", "content = ${con.content}", null)
		db.close()
		return success 
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