package uz.salikhdev.seyg.core.cache

import com.securepreferences.SecurePreferences
import uz.salikhdev.seyg.core.app.App
import uz.salikhdev.seyg.core.utils.BooleanPreference
import uz.salikhdev.seyg.core.utils.IntPreference
import uz.salikhdev.seyg.core.utils.StringPreference


class LocalStorage {
    private val KEY = "SKAOJXJDSFIJDSDSF342s4j4dsfadsasg"
    private val securePref = SecurePreferences(App.instance, KEY, "local_storage.xml")

    var isFirst: Boolean by BooleanPreference(securePref, true)
    var token: String by StringPreference(securePref, "")
    var userID: Int by IntPreference(securePref, 0)
    var userName: String by StringPreference(securePref, "")


}