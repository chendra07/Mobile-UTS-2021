package umn.ac.id.chendradewangga_00000029610_if633_el_uts

class AudioFiles {
    var path: String? = null
        get() = field
        set(value) {
            field = value
        }
    var title: String? = null
        get() = field
        set(value) {
            field = value
        }
    var album: String? = null
        get() = field
        set(value) {
            field = value
        }
    var duration: Int? = null
        get() = field
        set(value) {
            field = value
        }


    constructor(path: String?, title: String?, album: String?, duration: Int) {
        this.path = path
        this.title = title
        this.album = album
        this.duration = duration
    }

}