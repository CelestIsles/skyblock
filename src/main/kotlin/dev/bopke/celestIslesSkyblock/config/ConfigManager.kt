package dev.bopke.celestIslesSkyblock.config

import com.eternalcode.multification.notice.resolver.NoticeResolverRegistry
import com.eternalcode.multification.okaeri.MultificationSerdesPack
import eu.okaeri.configs.ConfigManager
import eu.okaeri.configs.OkaeriConfig
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit
import java.io.File
import kotlin.reflect.KClass

class ConfigManager(private val noticeResolverRegistry: NoticeResolverRegistry) {

    fun loadConfig(configClass: KClass<out OkaeriConfig>, path: File, configName: String): OkaeriConfig {
        return ConfigManager.create(configClass.java) {
            it.withConfigurer(YamlBukkitConfigurer(), SerdesBukkit(), MultificationSerdesPack(this.noticeResolverRegistry))
            it.withBindFile(File(path, configName))
            it.withRemoveOrphans(true)
            it.saveDefaults()
            it.load(true)
        }
    }

}