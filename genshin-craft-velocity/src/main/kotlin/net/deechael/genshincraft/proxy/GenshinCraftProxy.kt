package net.deechael.genshincraft.proxy

import com.google.inject.Inject
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.ProxyServer
import org.slf4j.Logger
import java.nio.file.Path


@Plugin(
    id = "genshin-craft-proxy",
    name = "Genshin Craft Proxy",
    version = "0.1.0",
    url = "https://github.com/DeeChael/GenshinCraft",
    description = "The controller of Genshin Craft",
    authors = ["DeeChael"]
)
class GenshinCraftProxy @Inject constructor(val server: ProxyServer, val logger: Logger, @DataDirectory val dataDirectory: Path) {

    @Subscribe
    fun onProxyInitialization(event: ProxyInitializeEvent) {

    }

}