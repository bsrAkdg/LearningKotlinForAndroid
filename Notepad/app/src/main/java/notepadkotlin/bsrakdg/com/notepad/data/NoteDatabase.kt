package notepadkotlin.bsrakdg.com.notepad.data

import android.content.Context
import android.database.Cursor
import notepadkotlin.bsrakdg.com.notepad.data.NotesContract.NoteTable.CREATED_AT
import notepadkotlin.bsrakdg.com.notepad.data.NotesContract.NoteTable._TABLE_NAME
import java.util.*

class NoteDatabase(context: Context) {
    private val helper: NotesOpenHelper = NotesOpenHelper(context)

    val all: List<Note>
        get() {
            val cursor = helper.readableDatabase.query(
                _TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                CREATED_AT
            )
            return cursor.use { allFromCursor(cursor) }
            /* other option
            val retval = allFromCursor(cursor)
            cursor.close()
            return retval
            */
        }

    private fun allFromCursor(cursor: Cursor): List<Note> {
        val retval = ArrayList<Note>()
        while (cursor.moveToNext()) {
            retval.add(fromCursor(cursor))
        }
        return retval
    }

    private fun fromCursor(cursor: Cursor): Note {
        var col = 0
        val note = Note().apply {
            id = cursor.getInt(col++)
            text = cursor.getString(col++)
            isPinned = cursor.getInt(col++) != 0
            createdAt = Date(cursor.getLong(col++))
            updatedAt = Date(cursor.getLong(col++))
        }
        return note
    }
}