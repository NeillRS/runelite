package net.runelite.client.plugins.playerindicators;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.runelite.api.ClanMemberRank;
import net.runelite.api.Player;
import net.runelite.api.Point;
import net.runelite.client.game.ClanManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.OverlayUtil;

@Singleton
public class PlayerIndicatorsOverlay extends Overlay
{
	private final PlayerIndicatorsService playerIndicatorsService;
	private final PlayerIndicatorsConfig config;
	private final ClanManager clanManager;

	@Inject
	private PlayerIndicatorsOverlay(PlayerIndicatorsConfig config, PlayerIndicatorsService playerIndicatorsService,
		ClanManager clanManager)
	{
		this.config = config;
		this.playerIndicatorsService = playerIndicatorsService;
		this.clanManager = clanManager;
		setPosition(OverlayPosition.DYNAMIC);
		setPriority(OverlayPriority.MED);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		playerIndicatorsService.forEachPlayer((player, color) -> renderPlayerOverlay(graphics, player, color));
		playerIndicatorsService.checkForPlayers();
		return null;
	}

	private void renderPlayerOverlay(Graphics2D graphics, Player actor, Color color)
	{
		if (config.drawTiles())
		{
			Polygon poly = actor.getCanvasTilePoly();
			if (poly != null)
			{
				OverlayUtil.renderPolygon(graphics, poly, color);
			}
		}

		if (!config.drawOverheadPlayerNames())
		{
			return;
		}

		int offset = actor.getLogicalHeight() + 40;
		String name = actor.getName().replace('\u00A0', ' ');
		Point textLocation = actor.getCanvasTextLocation(graphics, name, offset);

		if (textLocation != null)
		{
			if (config.showClanRanks() && actor.isClanMember())
			{
				ClanMemberRank rank = clanManager.getRank(name);

				if (rank != ClanMemberRank.UNRANKED)
				{
					BufferedImage clanchatImage = clanManager.getClanImage(rank);

					if (clanchatImage != null)
					{
						int width = clanchatImage.getWidth();
						int textHeight = graphics.getFontMetrics().getHeight() - graphics.getFontMetrics().getMaxDescent();
						Point imageLocation = new Point(textLocation.getX() - width / 2 - 1, textLocation.getY() - textHeight / 2 - clanchatImage.getHeight() / 2);
						OverlayUtil.renderImageLocation(graphics, imageLocation, clanchatImage);
						textLocation = new Point(textLocation.getX() + width / 2, textLocation.getY());
					}
				}
			}
			if (config.drawCombatLevel()){
				String combatLevel = Integer.toString(actor.getCombatLevel());
				Point newTextLocation = new Point(textLocation.getX() + 15, textLocation.getY());
				OverlayUtil.renderTextLocation(graphics, newTextLocation, combatLevel, color);

			} else {
				OverlayUtil.renderTextLocation(graphics, textLocation, name, color);
			}
		}
	}
}
