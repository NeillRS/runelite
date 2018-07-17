package net.runelite.client.plugins.zulrah.overlays;

import java.awt.*;
import javax.annotation.Nullable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Perspective;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.plugins.zulrah.ZulrahInstance;
import net.runelite.client.plugins.zulrah.ZulrahPlugin;
import net.runelite.client.plugins.zulrah.phase.ZulrahPhase;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

@Slf4j
public class ZulrahOverlay extends Overlay
{
	private static final Color TILE_BORDER_COLOR = new Color(0, 0, 0, 255);
	private static final Color NEXT_TEXT_COLOR = new Color(255, 255, 255, 50);

	private final Client client;
	private final ZulrahPlugin plugin;
	private WorldPoint zulrahNorthWorldPoint;
	private boolean flag = false;


	@Inject
	ZulrahOverlay(@Nullable Client client, ZulrahPlugin plugin)
	{
		setLayer(OverlayLayer.ABOVE_SCENE);
		setPosition(OverlayPosition.DYNAMIC);
		this.client = client;
		this.plugin = plugin;
	}

	@Override
	public Dimension render(Graphics2D graphics) {
		ZulrahInstance instance = plugin.getInstance();

		if (instance == null) {
			return null;
		}

		ZulrahPhase currentPhase = instance.getPhase();
		ZulrahPhase nextPhase = instance.getNextPhase();
		if (currentPhase == null) {
			return null;
		}

		zulrahNorthWorldPoint = instance.getStartLocation();

		drawStandLocation(graphics, zulrahNorthWorldPoint, currentPhase, false);
		if (nextPhase != null) {
			drawStandLocation(graphics, zulrahNorthWorldPoint, nextPhase, true);
		}
		return null;
	}


	private void drawStandLocation(Graphics2D graphics, WorldPoint zulrahNorthWorldPoint, ZulrahPhase phase, boolean isNextPhase){

		WorldPoint standTileWorldPoint = phase.getStandWorlPoint(zulrahNorthWorldPoint);
		LocalPoint standTileLocalPoint = LocalPoint.fromWorld(client, standTileWorldPoint);

		Color color = phase.getRelevantColor();
		renderPolygon(graphics, standTileLocalPoint, color);
		if (isNextPhase){
			Point textLocation = Perspective.getCanvasTextLocation(client, graphics, standTileLocalPoint, "Next", 0);
			renderText(graphics, textLocation, "Next");
		}

	}

	private void renderPolygon(Graphics2D graphics, LocalPoint lp, Color color){
		Polygon poly = Perspective.getCanvasTilePoly(client, lp);
		if (poly != null)
		{
			graphics.setColor(color);
			final Stroke originalStroke = graphics.getStroke();
			graphics.setStroke(new BasicStroke(1));
			graphics.drawPolygon(poly);
			graphics.fillPolygon(poly);
			graphics.setStroke(originalStroke);
		}
	}

	private void renderText(Graphics2D graphics, Point textLocation, String text){
		graphics.setColor(NEXT_TEXT_COLOR);
		graphics.drawString(text, textLocation.getX(), textLocation.getY());
	}
}
