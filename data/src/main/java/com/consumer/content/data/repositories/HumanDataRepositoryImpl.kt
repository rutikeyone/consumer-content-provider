package com.consumer.content.data.repositories

import android.content.ContentUris
import android.content.Context
import android.database.ContentObserver
import android.provider.BaseColumns
import android.util.Log
import com.consumer.content.core.common.Container
import com.consumer.content.data.HumanDataRepository
import com.consumer.content.data.db.HumanContract
import com.consumer.content.data.di.IODispatcher
import com.consumer.content.data.models.HumanModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

private const val idSelection = "${BaseColumns._ID} = ?"

data class RequiredUpdate(
    val firstTime: Boolean,
    val mustUpdate: Boolean,
)

class HumanDataRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
) : HumanDataRepository {
    override suspend fun getHumans(): List<HumanModel> {
        return with(ioDispatcher) {

            val cursor = context.contentResolver.query(
                HumanContract.CONTENT_URI,
                HumanContract.Entry.COLUMNS,
                null,
                null,
                null,
                null,
            )

            requireNotNull(cursor)

            val idColumn = cursor.getColumnIndexOrThrow(BaseColumns._ID)
            val nameColumn = cursor.getColumnIndexOrThrow(HumanContract.Entry.COLUMN_NAME_TITLE)
            val surnameColumn =
                cursor.getColumnIndexOrThrow(HumanContract.Entry.COLUMN_SURNAME_TITLE)
            val ageColumn = cursor.getColumnIndexOrThrow(HumanContract.Entry.COLUMN_AGE_TITLE)

            val result = mutableListOf<HumanModel>()

            cursor.use {
                while (cursor.moveToNext()) {
                    val idValue = cursor.getLong(idColumn)
                    val name = cursor.getString(nameColumn)
                    val surname = cursor.getString(surnameColumn)
                    val age = cursor.getInt(ageColumn)

                    val human = HumanModel(idValue, name, surname, age)
                    result.add(human)
                }
            }

            return@with result;
        }
    }

    override suspend fun getHuman(id: Long): HumanModel {
        return with(ioDispatcher) {
            val selectionArgs = arrayOf(id.toString())

            val cursor = context.contentResolver.query(
                HumanContract.CONTENT_URI,
                HumanContract.Entry.COLUMNS,
                idSelection,
                selectionArgs,
                null,
                null,
            )

            requireNotNull(cursor)

            cursor.moveToFirst()

            val idColumn = cursor.getColumnIndexOrThrow(BaseColumns._ID)
            val nameColumn = cursor.getColumnIndexOrThrow(HumanContract.Entry.COLUMN_NAME_TITLE)
            val surnameColumn =
                cursor.getColumnIndexOrThrow(HumanContract.Entry.COLUMN_SURNAME_TITLE)
            val ageColumn = cursor.getColumnIndexOrThrow(HumanContract.Entry.COLUMN_AGE_TITLE)

            val idValue = cursor.getLong(idColumn)
            val name = cursor.getString(nameColumn)
            val surname = cursor.getString(surnameColumn)
            val age = cursor.getInt(ageColumn)

            cursor.close()

            val human = HumanModel(
                id = idValue,
                name = name,
                surname = surname,
                age = age,
            )

            return@with human
        }
    }

    override fun observeHumans(silently: Boolean) = callbackFlow {
        requiredReadAll()
            .collect { requiredUpdate ->
                if (requiredUpdate.mustUpdate) {
                    if (!silently) {
                        trySend(Container.Pending)
                    }

                    val data = getAllWithResult()
                    trySend(data)
                }
            }
    }

    override fun observeHuman(
        silently: Boolean,
        id: Long,
        requiredObserver: Boolean,
    ): Flow<Container<HumanModel>> = callbackFlow {
        requiredRead(id, requiredObserver).collect {
            if (!silently) {
                trySend(Container.Pending)
            }

            val data = getWithResult(id)
            trySend(data)
        }
    }

    private fun requiredReadAll(): Flow<RequiredUpdate> = callbackFlow {
        val initialRequiredUpdate = RequiredUpdate(
            firstTime = true,
            mustUpdate = true,
        )

        trySend(initialRequiredUpdate)

        val observer = object : ContentObserver(null) {
            override fun onChange(selfChange: Boolean) {
                val requiredUpdate = RequiredUpdate(
                    firstTime = false,
                    mustUpdate = true,
                )

                trySend(requiredUpdate)
            }
        }

        context.contentResolver.registerContentObserver(
            HumanContract.CONTENT_URI,
            true,
            observer,
        )


        awaitClose {
            context.contentResolver.unregisterContentObserver(observer)
        }
    }

    private fun requiredRead(
        id: Long,
        requiredObserver: Boolean,
    ): Flow<Boolean> = callbackFlow {
        val initialValue = true
        trySend(initialValue)

        val observer = object : ContentObserver(null) {
            override fun onChange(selfChange: Boolean) {
                trySend(true)
            }
        }

        val uri = ContentUris.withAppendedId(
            HumanContract.CONTENT_URI,
            id,
        )

        if (requiredObserver) {
            context.contentResolver.registerContentObserver(
                uri,
                true,
                observer,
            )
        }

        awaitClose {
            if (requiredObserver) {
                context.contentResolver.unregisterContentObserver(observer)
            }
        }
    }

    private suspend fun getAllWithResult(): Container<List<HumanModel>> {
        try {
            delay(1000)

            val data = getHumans()
            val dataEmpty = data.isEmpty()

            if (dataEmpty) {
                return Container.Empty
            }
            return Container.Data(data)
        } catch (e: Exception) {
            return Container.Error(e)
        }
    }

    private suspend fun getWithResult(id: Long): Container<HumanModel> {
        try {
            delay(1000)

            val data = getHuman(id)
            return Container.Data(data)
        } catch (e: Exception) {
            return Container.Error(e)
        }
    }
}