package fr.thebrisingers.slotmachinegame.data.machine

import com.badlogic.gdx.math.Rectangle

data class SymbolRect(
    val title: String,
    val zone: Rectangle,
    var value: Int = 20
)
