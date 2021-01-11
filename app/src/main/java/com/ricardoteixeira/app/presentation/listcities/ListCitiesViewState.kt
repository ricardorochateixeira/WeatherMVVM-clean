package com.ricardoteixeira.app.presentation.listcities

import android.view.View
import com.ricardoteixeira.domain.models.WeatherCityEntity

data class ListCitiesViewState(
    val isShowingSnackBar: Boolean,
    val error: String?,
    val result: List<WeatherCityEntity>,
    val responseType: ResponseType
)

data class ResponseType(
    val uiComponentType: UIComponentType,
    val messageType: MessageType
)

sealed class UIComponentType {

    class Toast(val message: String?) : UIComponentType()

    class Dialog: UIComponentType()

    class AreYouSureDialog(val callback: AreYouSureCallback): UIComponentType()

    class SnackBar (val message: String? = null,  val undoCallback: Boolean = false, val onDismissCallback: ToDoCallback? = null): UIComponentType()

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

