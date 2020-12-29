package com.ricardoteixeira.app.ui.listcities

import android.view.View
import androidx.annotation.StringRes
import com.ricardoteixeira.app.framework.db.model.WeatherCityDatabaseModel
import com.ricardoteixeira.domain.models.WeatherCityEntity

data class ListCitiesViewState(
    val isLoading: Boolean,
    val error: String?,
    val result: List<WeatherCityEntity?>?,
    val responseType: ResponseType
)

data class ResponseType(
    val message: String?,
    val uiComponentType: UIComponentType,
    val messageType: MessageType
)

sealed class UIComponentType {

    class Toast : UIComponentType()

    class Dialog: UIComponentType()

    class AreYouSureDialog(val callback: AreYouSureCallback): UIComponentType()

    class SnackBar (val message: String? = null,  val undoCallback: SnackBarUndoCallback? = null, val onDismissCallback: ToDoCallback? = null): UIComponentType()

    class None: UIComponentType()

}

sealed class MessageType {
    class Success: MessageType()

    class Error: MessageType()

    class Info: MessageType()

    class None: MessageType()
}

interface AreYouSureCallback {

    fun proceed()

    fun cancel()
}

interface SnackBarUndoCallback {

    fun undo()
}

class SnackbarUndoListener
constructor(
    private val snackbarUndoCallback: SnackBarUndoCallback?
) : View.OnClickListener {

    override fun onClick(v: View?) {
        snackbarUndoCallback?.undo()
    }

}

interface ToDoCallback {

    fun execute()
}

