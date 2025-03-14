package dev.bopke.celestIslesSkyblock.config

import eu.okaeri.configs.OkaeriConfig
import eu.okaeri.configs.annotation.Comment
import eu.okaeri.configs.annotation.Header

@Header("## CelestIsles (Main-Config) ##")
class PluginConfig : OkaeriConfig() {

    @Comment("The name of the world where the skyblock islands are located")
    var skyblockWorldNamePrefix: String = "skyblock_"

    @Comment("Schematic name for the island")
    var schematicName: String = "default.schem";

    @Comment("Island border size")
    var islandBorderSize: Double = 100.0;

    @Comment("Uri to database")
    var databaseUri: String = "jdbc:postgresql://localhost:5432/postgres";

    @Comment("Database username")
    var databaseUsername: String = "postgres";

    @Comment("Database password")
    var databasePassword: String = "postgres";

}