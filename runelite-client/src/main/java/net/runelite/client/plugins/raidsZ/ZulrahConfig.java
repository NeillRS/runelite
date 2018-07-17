package net.runelite.client.plugins.raidsZ;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("zulrah")
public interface ZulrahConfig extends Config
{
	@ConfigItem(
		keyName = "enabled",
		name = "Enabled",
		description = "Configures whether or not zulrah overlays are displayed"
	)
	default boolean enabled()
	{
		return false;
	}
}
