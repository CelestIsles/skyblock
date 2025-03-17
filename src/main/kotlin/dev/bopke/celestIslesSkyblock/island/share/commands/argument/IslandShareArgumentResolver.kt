package dev.bopke.celestIslesSkyblock.island.share.commands.argument

import dev.bopke.celestIslesSkyblock.config.subconfig.IslandMessagesSubConfig
import dev.bopke.celestIslesSkyblock.island.IslandRepository
import dev.bopke.celestIslesSkyblock.island.share.IslandShareRepository
import dev.rollczi.litecommands.argument.Argument
import dev.rollczi.litecommands.argument.parser.ParseResult
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver
import dev.rollczi.litecommands.invocation.Invocation
import dev.rollczi.litecommands.shared.FailedReason
import dev.rollczi.litecommands.suggestion.Suggestion
import dev.rollczi.litecommands.suggestion.SuggestionContext
import dev.rollczi.litecommands.suggestion.SuggestionResult
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

class IslandShareArgumentResolver(
    private val islandRepository: IslandRepository,
    private val messagesSubConfig: IslandMessagesSubConfig,
    private val islandShareRepository: IslandShareRepository
) : ArgumentResolver<CommandSender, IslandShareArgument>() {

    private val noSharesFound: String = "No shares found"

    override fun parse(
        invocation: Invocation<CommandSender>?,
        context: Argument<IslandShareArgument>?,
        argument: String?
    ): ParseResult<IslandShareArgument> {
        if (invocation!!.sender() !is Player) {
            return ParseResult.failure(FailedReason.of(this.messagesSubConfig.hasToBePlayer))
        }

        val player: Player = invocation.sender() as Player;
        val optionalIsland = this.islandRepository.getByCreatorUuid(player.uniqueId).exceptionally {
            it.printStackTrace()
            Optional.empty()
        }.join()

        if (optionalIsland.isEmpty) {
            return ParseResult.failure(this.messagesSubConfig.youNeedToHaveIsland)
        }

        if (argument == this.noSharesFound) {
            return ParseResult.failure(this.messagesSubConfig.noShares)
        }

        val island = optionalIsland.get()
        val shareOptional = this.islandShareRepository.get(island.id, argument!!).exceptionally {
            it.printStackTrace()
            Optional.empty()
        }.join()

        if (shareOptional.isEmpty) {
            return ParseResult.failure(this.messagesSubConfig.islandSharePlayerNotFound)
        }

        val share = shareOptional.get()
        return ParseResult.success(IslandShareArgument(share.player_name, share.player_uuid))
    }

    override fun suggest(
        invocation: Invocation<CommandSender>?,
        argument: Argument<IslandShareArgument>?,
        context: SuggestionContext?
    ): SuggestionResult {
        val commandSender = invocation!!.sender()

        if (commandSender !is Player) {
            return SuggestionResult.empty()
        }

        val player: Player = commandSender;
        val optionalIsland = this.islandRepository.getByCreatorUuid(player.uniqueId).exceptionally {
            it.printStackTrace()
            Optional.empty()
        }.join()

        if (optionalIsland.isEmpty) {
            return SuggestionResult.of("You don't have an island")
        }

        val island = optionalIsland.get()
        val islandShares = this.islandShareRepository.getAll(island.id).exceptionally {
            it.printStackTrace()
            emptyList()
        }.join()

        val suggestions = islandShares
            .map { it.player_name }
        return SuggestionResult.of(suggestions.ifEmpty { listOf(this.noSharesFound) })
    }

}