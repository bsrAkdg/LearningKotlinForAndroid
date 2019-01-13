package notepadkotlin.bsrakdg.com.notepad.data

import android.content.Context
import java.util.concurrent.Executor
import java.util.concurrent.Executors

object DataStore {
    val EXEC: Executor = Executors.newSingleThreadExecutor()

    //If you use JvmStatic annotation, the compiler will generate both a static method in the enclosing class of
    // the object and an instance method in the object itself.
    @JvmStatic
    lateinit var notes: NoteDatabase
        private set

    fun init(context: Context) {
        notes = NoteDatabase(context)
    }

    fun execute(runnable: Runnable) {
        EXEC.execute(runnable)
    }
}