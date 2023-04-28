package net.deechael.genshincraft.gacha

import com.google.gson.JsonObject
import net.deechael.dutil.gson.JOBuilder
import java.util.*

// Based on https://zhuanlan.zhihu.com/p/522246996

class CharacterUpBanner(val id: String) {

    val fiveStars: MutableList<Item> = mutableListOf()
    val noUpFiveStars: MutableList<Item> = mutableListOf()
    val upFiveStars: MutableList<Item> = mutableListOf()
    val fourStars: MutableList<Item> = mutableListOf()
    val noUpFourStars: MutableList<Item> = mutableListOf()
    val upFourStars: MutableList<Item> = mutableListOf()
    val threeStars: MutableList<Item> = mutableListOf()

    fun addFiveStar(item: Item): CharacterUpBanner {
        this.noUpFiveStars.add(item)
        this.fiveStars.add(item)
        return this
    }

    fun addUpFiveStar(item: Item): CharacterUpBanner {
        this.upFiveStars.add(item)
        this.fiveStars.add(item)
        return this
    }

    fun addFourStar(item: Item): CharacterUpBanner {
        this.noUpFourStars.add(item)
        this.fourStars.add(item)
        return this
    }

    fun addUpFourStar(item: Item): CharacterUpBanner {
        this.upFourStars.add(item)
        this.fourStars.add(item)
        return this
    }

    fun addThreeStar(item: Item): CharacterUpBanner {
        this.threeStars.add(item)
        return this
    }

    fun randomFiveStar(): Item {
        val tempList = mutableListOf<Item>()
        tempList.add(upFiveStars.random())
        tempList.add(noUpFiveStars.random())
        return tempList.random()
    }

    fun randomUpFiveStar(): Item {
        return this.upFiveStars.random()
    }

    fun randomFourStar(): Item {
        val tempList = mutableListOf<Item>()
        tempList.add(upFourStars.random())
        tempList.add(noUpFourStars.random())
        return tempList.random()
    }

    fun randomUpFourStar(): Item {
        return this.upFourStars.random()
    }

    fun randomThreeStar(): Item {
        return this.threeStars.random()
    }

}

class CharacterUpBannerData(val banner: CharacterUpBanner) {

    private val random: Random = Random()

    var fiveStarChance: Int = 60
    private var fourStarChance: Int = 510 // (10000 - 510 = 9490) / 9 ~= 1055

    private var lastFiveStar: Int = 0
    private var isLastFiveStarUp: Boolean = true
    private var lastFourStar: Int = 0
    private var isLastFourStarUp: Boolean = true

    fun drawOnce(): Item {
        for (i in 0..(10000 - fiveStarChance)) {
            if (random.nextInt(10000) > fiveStarChance)
                break
            lastFiveStar = 0
            lastFourStar = 0
            this.fiveStarChance = 60
            this.fourStarChance = 510
            return if (this.isLastFiveStarUp) {
                val fiveStar = this.banner.randomFiveStar()
                this.isLastFiveStarUp = this.banner.upFiveStars.contains(fiveStar)
                fiveStar
            } else {
                this.isLastFiveStarUp = true
                this.banner.randomUpFiveStar()
            }
        }
        for (i in 0..(10000 - fourStarChance)) {
            if (random.nextInt(10000) > fourStarChance)
                break
            lastFourStar = 0
            this.fourStarChance = 510
            return if (this.isLastFourStarUp) {
                val fourStar = this.banner.randomFourStar()
                this.isLastFourStarUp = this.banner.upFourStars.contains(fourStar)
                fourStar
            } else {
                this.isLastFourStarUp = true
                this.banner.randomFourStar()
            }
        }
        if (lastFourStar >= 9) {
            this.lastFourStar = 0
            if (this.lastFiveStar >= 74) {
                this.fiveStarChance += 658
            }
            return if (this.isLastFourStarUp) {
                val fourStar = this.banner.randomFourStar()
                this.isLastFourStarUp = this.banner.upFourStars.contains(fourStar)
                fourStar
            } else {
                this.isLastFourStarUp = true
                this.banner.randomFourStar()
            }
        }
        this.lastFiveStar += 1
        this.lastFourStar += 1
        if (this.lastFiveStar >= 74) {
            this.fiveStarChance += 658
        }
        return this.banner.randomThreeStar()
    }

    fun drawTen(): List<Item> {
        val items = mutableListOf<Item>()
        for (i in 1..10) {
            items.add(drawOnce())
        }
        return items.toList()
    }

    /**
     * Example:
     *
     * {
     *   "type": "banner-data"
     *   "data": {
     *     "banner-id": "nahida-v3.2",
     *     "five-star-chance": 2034,
     *     "four-star-chance": 510,
     *     "last-five-star": 76,
     *     "last-four-star": 3,
     *     "is-last-five-star-up": true,
     *     "is-last-four-star-up": false
     *   }
     * }
     */
    fun serialize(): JsonObject {
        return JOBuilder.of()
            .string("type", "banner-data")
            .`object`("data")
            .string("banner-id", this.banner.id)
            .number("five-star-chance", this.fiveStarChance)
            .number("four-star-chance", this.fourStarChance)
            .number("last-five-star", this.fiveStarChance)
            .number("last-four-star", this.fourStarChance)
            .bool("is-last-five-star-up", this.isLastFiveStarUp)
            .bool("is-last-four-star-up", this.isLastFourStarUp)
            .done()!!
            .build()
    }

}