package com.ucasoft.money.listeners

interface AdapterChangeModeListener {

    fun editModeStart(position: Int)
    fun editMode(position: Int)
    fun normalModeStart(position: Int)
    fun normalMode(position: Int)
}