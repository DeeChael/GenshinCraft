package net.deechael.genshincraft.gacha

enum class ItemType {

    CHARACTER, WEAPON

}

class Item(val type: ItemType, val id: String) {

    override fun toString(): String {
        return this.id
    }

}