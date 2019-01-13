package notepadkotlin.bsrakdg.com.notepad.data

import java.util.*

public class Note {
    var id = -1
    var text: String? = null
    var isPinned = false
    var createdAt = Date()
    var updatedAt: Date? = null
}