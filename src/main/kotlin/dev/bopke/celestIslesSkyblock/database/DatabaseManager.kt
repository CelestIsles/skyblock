package dev.bopke.celestIslesSkyblock.database

import com.dzikoysk.sqiffy.Slf4JSqiffyLogger
import com.dzikoysk.sqiffy.Sqiffy
import com.dzikoysk.sqiffy.SqiffyDatabase
import com.dzikoysk.sqiffy.migrator.SqiffyMigrator
import com.dzikoysk.sqiffy.shared.createHikariDataSource
import dev.bopke.celestIslesSkyblock.config.PluginConfig
import dev.bopke.celestIslesSkyblock.island.IslandDefinition
import org.slf4j.LoggerFactory

class DatabaseManager(
    private val pluginConfig: PluginConfig
) {

    private lateinit var database: SqiffyDatabase

    fun connect() {
        val database = Sqiffy.createDatabase<SqiffyDatabase>(
            dataSource = createHikariDataSource(
                driver = "org.postgresql.Driver",
                url = pluginConfig.databaseUri,
                username = pluginConfig.databaseUsername,
                password = pluginConfig.databasePassword
            ),
            logger = Slf4JSqiffyLogger(LoggerFactory.getLogger(SqiffyDatabase::class.java))
        )
        val changeLog = database.generateChangeLog(listOf(
            IslandDefinition::class
        ))

        database.runMigrations(SqiffyMigrator(changeLog))
        this.database = database
    }

    fun getDatabase(): SqiffyDatabase {
        return this.database
    }

}