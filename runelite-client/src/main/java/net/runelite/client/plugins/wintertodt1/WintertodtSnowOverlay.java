package net.runelite.client.plugins.wintertodt1;

import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.GraphicID;
import net.runelite.api.GraphicsObject;
import net.runelite.api.Perspective;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.queries.GameObjectQuery;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;
import javax.inject.Inject;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.List;

public class WintertodtSnowOverlay extends Overlay
{
    private final Client client;
    private final Wintertodt1Plugin plugin;
    private final Wintertodt1Config config;

    private static final int MAX_DISTANCE = 2350; //Grabbed from the cannon plugin, magic number to keep overlay from rendering in unloaded tiles
    private static final int SNOW_ATTACK_CENTER_ID = 502; //Id of the graphics object at center of snow attack
    private static final int SNOW_PARTICLES_ID = GraphicID.SNOW_PARTICLES_ID; //Id of the particles that spawn around an attack that was spawned on a player


    Brazier[] Braziers = {
            //south east brazier
            new Brazier(
                    //The location the snow attack graphics object will spawn at for a south east brazier attack
                    new WorldPoint(1638, 3997, 0),
                    //The center of the snow attacks AOE damage
                    new WorldPoint(1640, 3998, 0)
            ),
            //south west brazier
            new Brazier(
                    new WorldPoint(1620, 3997, 0),
                    new WorldPoint(1621, 3998, 0)
            ),
            //north west brazier
            new Brazier(
                    new WorldPoint(1620, 4015, 0),
                    new WorldPoint(1621, 4017, 0)
            ),
            //north east brazier
            new Brazier(
                    new WorldPoint(1638, 4015, 0),
                    new WorldPoint(1640, 4017, 0)
            )
    };

    private boolean isAttackingPyromancer(int x, int y, List<GraphicsObject> graphicsObjects)
    {
        for (int i = 0; i < graphicsObjects.size(); i++)
        {
            GraphicsObject object = graphicsObjects.get(i);
            if (object.getId() == SNOW_PARTICLES_ID)
            {
                WorldPoint particleLocation = WorldPoint.fromLocal(client, object.getLocation());
                int particleX = particleLocation.getX();
                int particleY = particleLocation.getY();

                //particles spawn on the tiles diagonal to the main attack object
                if (particleX == x - 1 && particleY == y - 1 ||
                        particleX == x - 1 && particleY == y + 1 ||
                        particleX == x + 1 && particleY == y - 1 ||
                        particleX == x + 1 && particleY == y + 1)
                {
                    return false;
                }
            }
        }
        //if there are no particles around an attack object, and it's not attacking a brazier, it is a non-damaging pyro hit
        return true;
    }

    @Inject
    WintertodtSnowOverlay(Client client, Wintertodt1Plugin plugin, Wintertodt1Config config)
    {
        setPosition(OverlayPosition.DYNAMIC);
        this.client = client;
        this.plugin = plugin;
        this.config = config;
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        if (config.showDangerTiles() && plugin.isInWintertodt())
        {
            List<GraphicsObject> graphicsObjects = client.getGraphicsObjects();
            LocalPoint localLocation = client.getLocalPlayer().getLocalLocation();

            GameObjectQuery query = new GameObjectQuery();
            GameObject[] gameObjects = query.result(client);

            for (int i = 0; i < graphicsObjects.size(); i++)
            {
                GraphicsObject object = graphicsObjects.get(i);
                if (object.getId() == SNOW_ATTACK_CENTER_ID)
                {
                    LocalPoint localCenter = object.getLocation();
                    WorldPoint worldCenter = WorldPoint.fromLocal(client, localCenter);
                    Polygon dangerZone = null;

                    int worldX = worldCenter.getX();
                    int worldY = worldCenter.getY();

                    boolean brazierHit = false;

                    for (int j = 0; i < Braziers.length; i++)
                    {
                        if (Braziers[i].isAttackingBrazier(worldX, worldY))
                        {
                            brazierHit = true;
                            if (Braziers[i].isBrazierAttackDangerous(gameObjects))
                            {
                                dangerZone = Braziers[i].dangerZone(client);
                                break;
                            }
                            else
                            {
                                break;
                            }
                        }
                    }

                    if (dangerZone == null && brazierHit == false && !isAttackingPyromancer(worldX, worldY, graphicsObjects))
                    {
                        dangerZone = Perspective.getCanvasTileAreaPoly(client, localCenter, 3);
                    }

                    if (dangerZone != null && localLocation.distanceTo(localCenter) <= MAX_DISTANCE)
                    {
                        OverlayUtil.renderPolygon(graphics, dangerZone, Color.RED);
                    }
                }
            }

        }


        return null;
    }
}