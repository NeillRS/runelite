package net.runelite.client.plugins.playerindicators;

import java.awt.Color;
import java.util.function.BiConsumer;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.client.Notifier;

@Singleton
public class PlayerIndicatorsService
{
	private final Client client;
	private final PlayerIndicatorsConfig config;
	private Player[] cashedPlayers;
	private int playerCount = 0;
	private boolean isAreaEmpty;

	@Inject
	private Notifier notifier;


	@Inject
	private PlayerIndicatorsService(Client client, PlayerIndicatorsConfig config)
	{
		this.config = config;
		this.client = client;
		this.cashedPlayers = client.getCachedPlayers();
	}

	public void forEachPlayer(final BiConsumer<Player, Color> consumer)
	{
		if (!config.highlightOwnPlayer() && !config.drawClanMemberNames()
			&& !config.highlightFriends() && !config.highlightNonClanMembers() && !config.drawCombatLevel() && !config.drawCombatColors())
		{
			return;
		}

		final Player localPlayer = client.getLocalPlayer();

		for (Player player : client.getPlayers()){

			if (player == null || player.getName() == null){
				continue;
			}

			boolean isClanMember = player.isClanMember();

			if (player == localPlayer)
			{
				if (config.highlightOwnPlayer())
				{
					consumer.accept(player, config.getOwnPlayerColor());
				}
			}
			else if (config.highlightFriends() && (player.isFriend() || client.isFriended(player.getName(), false)))
			{
				consumer.accept(player, config.getFriendColor());
			}
			else if (config.drawClanMemberNames() && isClanMember)
			{
				consumer.accept(player, config.getClanMemberColor());
			}
			else if (config.highlightTeamMembers() && localPlayer.getTeam() > 0 && localPlayer.getTeam() == player.getTeam())
			{
				consumer.accept(player, config.getTeamMemberColor());
			}
			else if (config.highlightNonClanMembers() && !isClanMember)
			{
				consumer.accept(player, config.getNonClanMemberColor());
			}
			else if (config.drawCombatColors())
			{
				int playerCombatLevel = player.getCombatLevel();
				int localPlayerCombatLevel = localPlayer.getCombatLevel();
				Color color;
				if (localPlayerCombatLevel -  playerCombatLevel > 15){
					color = Color.GREEN;
				} else if (localPlayerCombatLevel -  playerCombatLevel < 5){
					color = Color.RED;
				} else {
					color = Color.YELLOW;
				}
				consumer.accept(player, color);
			}
		}
	}

	protected void checkForPlayers(){
		for (Player player : cashedPlayers){
			if (player == null || player.getName() == null){
				continue;
			} else if (playerCount < 2){
				playerCount++;
			} else {
				break;
			}
		}
		if (playerCount == 1){
			isAreaEmpty = true;
		} else if (isAreaEmpty){ //Was empty and now not
			notifier.notify("Player is approaching");
			isAreaEmpty = false;
		}
		playerCount = 0;
	}
}
