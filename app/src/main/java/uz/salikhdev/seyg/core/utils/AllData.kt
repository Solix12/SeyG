package uz.salikhdev.seyg.core.utils

import uz.salikhdev.seyg.R
import uz.salikhdev.seyg.core.models.ContactModel

fun getAllContact(): ArrayList<ContactModel>{
    val data = ArrayList<ContactModel>()

    data.add(ContactModel(1, "Murod", R.drawable.placeholder))
    data.add(ContactModel(2, "Muhammadsolih", R.drawable.placeholder))
    data.add(ContactModel(3, "Fazliddin", R.drawable.placeholder))
    data.add(ContactModel(4, "Nodir", R.drawable.placeholder))

    return data
}