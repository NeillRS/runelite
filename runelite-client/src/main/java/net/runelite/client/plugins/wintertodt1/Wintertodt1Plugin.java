package net.runelite.client.plugins.wintertodt1;

import javax.inject.Inject;
import com.google.inject.Provides;
import lombok.Getter;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@PluginDescriptor(
        name = "Wintertodt"
)
public class Wintertodt1Plugin extends Plugin
{
    @Inject
    private Client client;

    @Inject
    private Wintertodt1Config config;

    private static final int WINTERTODT_REGION = 6462;

    @Getter
    private static final WorldPoint[] safespots = {
            new WorldPoint(1622, 3988, 0),
            new WorldPoint(1622, 3996, 0),
            new WorldPoint(1638, 3996, 0),
            new WorldPoint(1638, 3988, 0)
    };

    @Inject
    private OverlayManager overlayManager;

    @Inject
    private WintertodtSafespotOverlay safespotOverlay;

    @Inject
    private WintertodtSnowOverlay snowOverlay;

    @Override
    protected  void startUp() throws Exception{
        overlayManager.add(snowOverlay);
        overlayManager.add(safespotOverlay);
    }

    @Override
    protected  void shutDown() throws Exception{
        overlayManager.remove(snowOverlay);
        overlayManager.remove(safespotOverlay);
    }

    @Provides
    Wintertodt1Config getConfig(ConfigManager configManager)
    {
        return configManager.getConfig(Wintertodt1Config.class);
    }

    public boolean isInWintertodt()
    {
        if (client.getGameState() != GameState.LOGGED_IN)
        {
            return false;
        }
        return client.getLocalPlayer().getWorldLocation().getRegionID() == WINTERTODT_REGION;
    }
}