package com.ricardoteixeira.app.utils

sealed class UIComponentType {

    class Toast : UIComponentType()

    class Dialog : UIComponentType()

    class None : UIComponentType()
}

interface SnackbarUndoCallback {

    fun undo()
}