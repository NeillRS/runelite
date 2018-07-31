package net.runelite.client.plugins.wintertodt1;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("wintertodt1")

public interface Wintertodt1Config extends Config
{
    @ConfigItem(
            position = 0,
            keyName = "showSafespot",
            name = "Safespot Marker",
            description = "Whether or not snow attack safespots are shown"
    )
    default boolean showSafespot()
    {
        return true;
    }

    @ConfigItem(
            position = 1,
            keyName = "showDangerTiles",
            name = "Highlight Dangerous Tiles",
            description = "Highlight tiles that a snow attack is about to hit"
    )
    default boolean showDangerTiles()
    {
        return true;
    }
}