package dev.bopke.celestIslesSkyblock.island

import com.dzikoysk.sqiffy.definition.*

@Definition(
    [
        DefinitionVersion(
            version = "v1.0.0",
            name = "islands",
            properties = [
                Property(name = "id", type = DataType.SERIAL),
                Property(name = "creator_uuid", type = DataType.UUID_TYPE),
                Property(name = "world", type = DataType.TEXT)
            ],
            constraints = [
                Constraint(type = ConstraintType.PRIMARY_KEY, name = "primary_key_id", on = ["id"])
            ],
            indices = [
                Index(type = IndexType.INDEX, name = "index_creator_uuid", columns = ["creator_uuid"]),
                Index(type = IndexType.INDEX, name = "index_world", columns = ["world"])
            ]
        ),
        DefinitionVersion(
            version = "v1.0.1",
            properties = [
                Property(operation = PropertyDefinitionOperation.ADD, name = "name", type = DataType.TEXT)
            ],
            indices = [
                Index(type = IndexType.INDEX, name = "index_name", columns = ["name"])
            ]
        )
    ]
)
object IslandDefinition