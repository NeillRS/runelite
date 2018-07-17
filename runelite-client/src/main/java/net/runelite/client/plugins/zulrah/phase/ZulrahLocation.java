package net.runelite.client.plugins.raidsZ.phase;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.coords.WorldPoint;


@Slf4j
public enum ZulrahLocation
{
	NORTH, SOUTH, EAST, WEST;

	public static ZulrahLocation calculateZulrahLocation(WorldPoint zulrahNorthLocation, WorldPoint current)
	{
		int dx = zulrahNorthLocation.getX() - current.getX();
		int dy = zulrahNorthLocation.getY() - current.getY();
		if (dx == -10 && dy == 2)
		{
			return ZulrahLocation.EAST;
		}
		else if (dx == 10 && dy == 2)
		{
			return ZulrahLocation.WEST;
		}
		else if (dx == 0 && dy == 11)
		{
			return ZulrahLocation.SOUTH;
		}
		else if (dx == 0 && dy == 0)
		{
			return ZulrahLocation.NORTH;
		}
		else
		{
			log.debug("Unknown Zulrah location dx: {}, dy: {}", dx, dy);
			return null;
		}
	}
}
