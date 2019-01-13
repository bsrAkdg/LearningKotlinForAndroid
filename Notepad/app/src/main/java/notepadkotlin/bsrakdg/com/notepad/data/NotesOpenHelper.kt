package notepadkotlin.bsrakdg.com.notepad.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import notepadkotlin.bsrakdg.com.notepad.data.NotesContract.SQL_CREATE_ENTRIES
import notepadkotlin.bsrakdg.com.notepad.data.NotesContract.SQL_DELETE_ENTRIES

class NotesOpenHelper(context: Context) : SQLiteOpenHelper(context, "notes.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }
}