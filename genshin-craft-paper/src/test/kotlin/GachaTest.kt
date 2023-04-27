import NihidaItems.amber
import NihidaItems.barbara
import NihidaItems.beidou
import NihidaItems.bennett
import NihidaItems.candace
import NihidaItems.chongyun
import NihidaItems.collei
import NihidaItems.diluc
import NihidaItems.diona
import NihidaItems.dori
import NihidaItems.fischl
import NihidaItems.gorou
import NihidaItems.jean
import NihidaItems.kaeya
import NihidaItems.keqing
import NihidaItems.kujou_sara
import NihidaItems.kuki_shinobu
import NihidaItems.layla
import NihidaItems.lisa
import NihidaItems.mona
import NihidaItems.nahida
import NihidaItems.ningguang
import NihidaItems.noelle
import NihidaItems.placeholder_3star_weapon
import NihidaItems.placeholder_4star_weapon
import NihidaItems.qiqi
import NihidaItems.razor
import NihidaItems.rosaria
import NihidaItems.sayu
import NihidaItems.shikanoin_heizou
import NihidaItems.sucrose
import NihidaItems.thoma
import NihidaItems.tighnari
import NihidaItems.xiangling
import NihidaItems.xingqiu
import NihidaItems.xinyan
import NihidaItems.yanfei
import NihidaItems.yunjun
import net.deechael.genshincraft.gacha.CharacterUpBannerData
import net.deechael.genshincraft.gacha.Item
import net.deechael.genshincraft.gacha.ItemType
import net.deechael.genshincraft.gacha.CharacterUpBanner
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GachaTest {

    lateinit var banner: CharacterUpBanner

    @BeforeAll
    fun buildBanner() {
        this.banner = CharacterUpBanner("nahida-v3.2")
            .addUpFiveStar(nahida)

            .addUpFourStar(razor)
            .addUpFourStar(noelle)
            .addUpFourStar(bennett)

            .addFiveStar(qiqi)
            .addFiveStar(keqing)
            .addFiveStar(diluc)
            .addFiveStar(mona)
            .addFiveStar(tighnari)
            .addFiveStar(jean)

            .addFourStar(amber)
            .addFourStar(barbara)
            .addFourStar(beidou)
            .addFourStar(candace)
            .addFourStar(chongyun)
            .addFourStar(collei)
            .addFourStar(diona)
            .addFourStar(dori)
            .addFourStar(fischl)
            .addFourStar(gorou)
            .addFourStar(kaeya)
            .addFourStar(kujou_sara)
            .addFourStar(kuki_shinobu)
            .addFourStar(layla)
            .addFourStar(lisa)
            .addFourStar(ningguang)
            .addFourStar(rosaria)
            .addFourStar(sayu)
            .addFourStar(shikanoin_heizou)
            .addFourStar(sucrose)
            .addFourStar(thoma)
            .addFourStar(xiangling)
            .addFourStar(xingqiu)
            .addFourStar(xinyan)
            .addFourStar(yanfei)
            .addFourStar(yunjun)

        for (i in 1..18) { // counted in game
            this.banner.addFourStar(placeholder_4star_weapon)
        }
        for (i in 1..13) { // counted in game
            this.banner.addThreeStar(placeholder_3star_weapon)
        }
    }

    @Test
    fun pityOnce() {
        println("====== [ Pity Once ] ======")
        var fiveStars = 0
        var fourStars = 0
        val bannerData = CharacterUpBannerData(this.banner)
        for (i in 1..9) {
            val items = bannerData.drawTen()
            var fiveStar = false
            for (item in items) {
                if (this.banner.fourStars.contains(item))
                    fourStars += 1
                else if (this.banner.fiveStars.contains(item)) {
                    fiveStars += 1
                    fiveStar = true
                }
            }
            if (fiveStar)
                println("* $i time: $items")
            else
                println("$i time: $items")
        }
        println("===========")
        println("five stars: $fiveStars, four stars: $fourStars")
    }


    @Test
    fun pityTwice() {
        println("====== [ Pity Twice ] ======")
        var fiveStars = 0
        var fourStars = 0
        val bannerData = CharacterUpBannerData(this.banner)
        for (i in 1..18) {
            val items = bannerData.drawTen()
            var fiveStar = false
            for (item in items) {
                if (this.banner.fourStars.contains(item))
                    fourStars += 1
                else if (this.banner.fiveStars.contains(item)) {
                    fiveStars += 1
                    fiveStar = true
                }
            }
            if (fiveStar)
                println("* $i time: $items")
            else
                println("$i time: $items")
        }
        println("===========")
        println("five stars: $fiveStars, four stars: $fourStars")
    }

}

// v3.2 banner
object NihidaItems {

    // up 5 stars
    val nahida = Item(ItemType.CHARACTER, "* nahida *")

    // up 4 stars
    val razor = Item(ItemType.CHARACTER, "& razor &")
    val noelle = Item(ItemType.CHARACTER, "& noelle &")
    val bennett = Item(ItemType.CHARACTER, "& bennett &")

    // 5 stars
    val qiqi = Item(ItemType.CHARACTER, "* qiqi *")
    val keqing = Item(ItemType.CHARACTER, "* keqing *")
    val diluc = Item(ItemType.CHARACTER, "* diluc *")
    val mona = Item(ItemType.CHARACTER, "* mona *")
    val tighnari = Item(ItemType.CHARACTER, "* tighnari *")
    val jean = Item(ItemType.CHARACTER, "* jean *")

    // 4 stars
    val amber = Item(ItemType.CHARACTER, "& amber &")
    val barbara = Item(ItemType.CHARACTER, "& barbara &")
    val beidou = Item(ItemType.CHARACTER, "& beidou &")
    val candace = Item(ItemType.CHARACTER, "& candace &")
    val chongyun = Item(ItemType.CHARACTER, "& chongyun &")
    val collei = Item(ItemType.CHARACTER, "& collei &")
    val diona = Item(ItemType.CHARACTER, "& diona &")
    val dori = Item(ItemType.CHARACTER, "& dori &")
    val fischl = Item(ItemType.CHARACTER, "& fischl &")
    val gorou = Item(ItemType.CHARACTER, "& gorou &")
    val kaeya = Item(ItemType.CHARACTER, "& kaeya &")
    val kujou_sara = Item(ItemType.CHARACTER, "& kujou_sara &")
    val kuki_shinobu = Item(ItemType.CHARACTER, "& kuki_shinobu &")
    val layla = Item(ItemType.CHARACTER, "& layla &")
    val lisa = Item(ItemType.CHARACTER, "& lisa &")
    val ningguang = Item(ItemType.CHARACTER, "& ningguang &")
    val rosaria = Item(ItemType.CHARACTER, "& rosaria &")
    val sayu = Item(ItemType.CHARACTER, "& sayu &")
    val shikanoin_heizou = Item(ItemType.CHARACTER, "& shikanoin_heizou &")
    val sucrose = Item(ItemType.CHARACTER, "& sucrose &")
    val thoma = Item(ItemType.CHARACTER, "& thoma &")
    val xiangling = Item(ItemType.CHARACTER, "& xiangling &")
    val xingqiu = Item(ItemType.CHARACTER, "& xingqiu &")
    val xinyan = Item(ItemType.CHARACTER, "& xinyan &")
    val yanfei = Item(ItemType.CHARACTER, "& yanfei &")
    val yunjun = Item(ItemType.CHARACTER, "& yunjun &")

    val placeholder_4star_weapon = Item(ItemType.WEAPON, "^ (4 star weapon) ^")

    val placeholder_3star_weapon = Item(ItemType.WEAPON, "(3 star weapon)")


}