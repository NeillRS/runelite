package net.runelite.client.plugins.wintertodt1;

import net.runelite.api.Client;
import net.runelite.api.Perspective;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;
import javax.inject.Inject;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class WintertodtSafespotOverlay extends Overlay
{
    private final Client client;
    private final Wintertodt1Plugin plugin;
    private final Wintertodt1Config config;

    private static final int MAX_DISTANCE = 2350; //Grabbed from the cannon plugin, magic number to keep overlay from rendering in unloaded tiles

    @Inject
    WintertodtSafespotOverlay(Client client, Wintertodt1Plugin plugin, Wintertodt1Config config)
    {
        setPosition(OverlayPosition.DYNAMIC);
        this.client = client;
        this.plugin = plugin;
        this.config = config;
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        if (config.showSafespot() && plugin.isInWintertodt())
        {
            WorldPoint[] safeSpots = plugin.getSafespots();
            LocalPoint localLocation = client.getLocalPlayer().getLocalLocation();

            for (int i = 0; i < safeSpots.length; i++)
            {
                LocalPoint safeSpotPoint = LocalPoint.fromWorld(client, safeSpots[i]);
                Polygon poly = Perspective.getCanvasTilePoly(client, safeSpotPoint);

                if (poly != null  && localLocation.distanceTo(safeSpotPoint) <= MAX_DISTANCE)
                {
                    OverlayUtil.renderPolygon(graphics, poly, Color.GREEN);
                }
            }
        }

        return null;
    }
}