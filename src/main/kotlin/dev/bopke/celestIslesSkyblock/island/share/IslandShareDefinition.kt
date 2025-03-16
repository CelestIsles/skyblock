package dev.bopke.celestIslesSkyblock.island.share

import com.dzikoysk.sqiffy.definition.*
import dev.bopke.celestIslesSkyblock.island.IslandDefinition


@Definition(
    [
        DefinitionVersion(
            version = "v1.0.0",
            name = "island_shares",
            properties = [
                Property(name = "id", type = DataType.SERIAL),
                Property(name = "island_id", type = DataType.INT),
                Property(name = "player_uuid", type = DataType.UUID_TYPE),
                Property(name = "permission", type = DataType.TEXT)
            ],
            constraints = [
                Constraint(type = ConstraintType.PRIMARY_KEY, name = "pk_id", on = ["id"]),
                Constraint(
                    type = ConstraintType.FOREIGN_KEY,
                    on = ["island_id"],
                    name = "fk_id",
                    referenced = IslandDefinition::class,
                    references = "id"
                )
            ],
            indices = [
                Index(type = IndexType.INDEX, name = "index_island_id", columns = ["island_id"]),
                Index(type = IndexType.INDEX, name = "index_player_uuid", columns = ["player_uuid"])
            ]
        ),
        DefinitionVersion(
            version = "v1.1.0",
            properties = [
                Property(operation = PropertyDefinitionOperation.REMOVE, name = "permission")
            ]
        )
    ]
)
object IslandShareDefinition