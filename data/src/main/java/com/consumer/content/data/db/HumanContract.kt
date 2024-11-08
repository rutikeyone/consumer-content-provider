package com.consumer.content.data.db

import android.net.Uri
import android.provider.BaseColumns

object HumanContract {

    private const val AUTHORITY: String = "com.producer.human.provider"
    private const val PATH_CONTACTS: String = "humans"

    val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/$PATH_CONTACTS")

    object Entry : BaseColumns {
        const val TABLE_NAME = "humans"
        const val COLUMN_NAME_TITLE = "name"
        const val COLUMN_SURNAME_TITLE = "surname"
        const val COLUMN_AGE_TITLE = "age"

        val COLUMNS = arrayOf(
            BaseColumns._ID,
            COLUMN_NAME_TITLE,
            COLUMN_SURNAME_TITLE,
            COLUMN_AGE_TITLE,
        )
    }

}