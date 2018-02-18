package com.ucasoft.money.listeners

interface AdapterChangeModeListener {

    fun editModeStart()
    fun editMode()
    fun normalModeStart()
    fun normalMode()
}