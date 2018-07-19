package net.runelite.client.plugins.playerindicators;

import java.awt.Color;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("playerindicators")
public interface PlayerIndicatorsConfig extends Config
{
	@ConfigItem(
		position = 0,
		keyName = "drawOwnName",
		name = "Highlight own player",
		description = "Configures whether or not your own player should be highlighted"
	)
	default boolean highlightOwnPlayer()
	{
		return false;
	}

	@ConfigItem(
		position = 1,
		keyName = "ownNameColor",
		name = "Own player color",
		description = "Color of your own player"
	)
	default Color getOwnPlayerColor()
	{
		return new Color(0, 184, 212);
	}

	@ConfigItem(
		position = 2,
		keyName = "drawFriendNames",
		name = "Highlight friends",
		description = "Configures whether or not friends should be highlighted"
	)
	default boolean highlightFriends()
	{
		return true;
	}

	@ConfigItem(
		position = 3,
		keyName = "friendNameColor",
		name = "Friend color",
		description = "Color of friend names"
	)
	default Color getFriendColor()
	{
		return new Color(0, 200, 83);
	}

	@ConfigItem(
		position = 4,
		keyName = "drawClanMemberNames",
		name = "Highlight clan members",
		description = "Configures whether or clan members should be highlighted"
	)
	default boolean drawClanMemberNames()
	{
		return true;
	}

	@ConfigItem(
		position = 5,
		keyName = "clanMemberColor",
		name = "Clan member color",
		description = "Color of clan members"
	)
	default Color getClanMemberColor()
	{
		return new Color(170, 0, 255);
	}

	@ConfigItem(
		position = 6,
		keyName = "drawTeamMemberNames",
		name = "Highlight team members",
		description = "Configures whether or not team members should be highlighted"
	)
	default boolean highlightTeamMembers()
	{
		return true;
	}

	@ConfigItem(
		position = 7,
		keyName = "teamMemberColor",
		name = "Team member color",
		description = "Color of team members"
	)
	default Color getTeamMemberColor()
	{
		return new Color(19, 110, 247);
	}

	@ConfigItem(
		position = 8,
		keyName = "drawNonClanMemberNames",
		name = "Highlight non-clan members",
		description = "Configures whether or not non-clan members should be highlighted"
	)
	default boolean highlightNonClanMembers()
	{
		return false;
	}

	@ConfigItem(
		position = 9,
		keyName = "nonClanMemberColor",
		name = "Non-clan member color",
		description = "Color of non-clan member names"
	)
	default Color getNonClanMemberColor()
	{
		return Color.RED;
	}

	@ConfigItem(
		position = 10,
		keyName = "drawPlayerTiles",
		name = "Draw tiles under players",
		description = "Configures whether or not tiles under highlighted players should be drawn"
	)
	default boolean drawTiles()
	{
		return false;
	}

	@ConfigItem(
		position = 11,
		keyName = "drawOverheadPlayerNames",
		name = "Draw names above players",
		description = "Configures whether or not player names should be drawn above players"
	)
	default boolean drawOverheadPlayerNames()
	{
		return true;
	}

	@ConfigItem(
		position = 12,
		keyName = "drawMinimapNames",
		name = "Draw names on minimap",
		description = "Configures whether or not minimap names for players with rendered names should be drawn"
	)
	default boolean drawMinimapNames()
	{
		return false;
	}

	@ConfigItem(
		position = 13,
		keyName = "colorPlayerMenu",
		name = "Colorize player menu",
		description = "Color right click menu for players"
	)
	default boolean colorPlayerMenu()
	{
		return true;
	}

	@ConfigItem(
		position = 14,
		keyName = "clanMenuIcons",
		name = "Show clan ranks",
		description = "Add clan rank to right click menu and next to player names"
	)
	default boolean showClanRanks()
	{
		return true;
	}

	@ConfigItem(
			position = 15,
			keyName = "drawCombatLevel",
			name = "Draw combat levels",
			description = "Draw combat level Instead of name"
	)
	default boolean drawCombatLevel()
	{
		return false;
	}

	@ConfigItem(
			position = 16,
			keyName = "drawCombatColors",
			name = "Draw combat relevant colors",
			description = "Draw combat relevant colors"
	)
	default boolean drawCombatColors()
	{
		return false;
	}

	@ConfigItem(
			position = 17,
			keyName = "sendPlayerNotification",
			name = "Send Player Notification",
			description = "Send notification when a player is approaching"
	)
	default boolean sendPlayerNotification()
	{
		return false;
	}


}
