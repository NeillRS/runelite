package net.runelite.client.plugins.raidsZ.overlays;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Prayer;
import net.runelite.client.plugins.raidsZ.ZulrahPlugin;
import net.runelite.client.plugins.zulrah.phase.ZulrahType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class ZulrahImageManager
{
	private static final BufferedImage[] zulrahBufferedImages = new BufferedImage[5];
	private static final BufferedImage[] smallZulrahBufferedImages = new BufferedImage[5];
	private static final BufferedImage[] prayerBufferedImages = new BufferedImage[3];

	public static BufferedImage getZulrahBufferedImage(ZulrahType type)
	{
		switch (type)
		{
			case RANGE:
				if (zulrahBufferedImages[0] == null)
				{
					zulrahBufferedImages[0] = getBufferedImage("/zulrahImages/zulrah_range.png");
				}
				return zulrahBufferedImages[0];
			case MAGIC:
				if (zulrahBufferedImages[1] == null)
				{
					zulrahBufferedImages[1] = getBufferedImage("/zulrahImages/zulrah_magic.png");
				}
				return zulrahBufferedImages[1];
			case MELEE:
				if (zulrahBufferedImages[2] == null)
				{
					zulrahBufferedImages[2] = getBufferedImage("/zulrahImages/zulrah_melee.png");
				}
				return zulrahBufferedImages[2];
			case JADMR:
				if (zulrahBufferedImages[3] == null)
				{
					zulrahBufferedImages[3] = getBufferedImage("/zulrahImages/zulrah_jad_mr.png");
				}
				return zulrahBufferedImages[3];
			case JADRM:
				if (zulrahBufferedImages[4] == null)
				{
					zulrahBufferedImages[4] = getBufferedImage("/zulrahImages/zulrah_jad_rm.png");
				}
				return zulrahBufferedImages[4];
		}
		return null;
	}

	public static BufferedImage getSmallZulrahBufferedImage(ZulrahType type)
	{
		switch (type)
		{
			case RANGE:
				if (smallZulrahBufferedImages[0] == null)
				{
					smallZulrahBufferedImages[0] = getBufferedImage("/zulrahImages/zulrah_range_sm.png");
				}
				return smallZulrahBufferedImages[0];
			case MAGIC:
				if (smallZulrahBufferedImages[1] == null)
				{
					smallZulrahBufferedImages[1] = getBufferedImage("/zulrahImages/zulrah_magic_sm.png");
				}
				return smallZulrahBufferedImages[1];
			case MELEE:
				if (smallZulrahBufferedImages[2] == null)
				{
					smallZulrahBufferedImages[2] = getBufferedImage("/zulrahImages/zulrah_melee_sm.png");
				}
				return smallZulrahBufferedImages[2];
			case JADMR:
				if (zulrahBufferedImages[3] == null)
				{
					zulrahBufferedImages[3] = getBufferedImage("/zulrahImages/zulrah_jad_mr.png");
				}
				return zulrahBufferedImages[3];
			case JADRM:
				if (zulrahBufferedImages[4] == null)
				{
					zulrahBufferedImages[4] = getBufferedImage("/zulrahImages/zulrah_jad_rm.png");
				}
				return zulrahBufferedImages[4];
		}
		return null;
	}

	public static BufferedImage getProtectionPrayerBufferedImage(Prayer prayer)
	{
		switch (prayer)
		{
			case PROTECT_FROM_MAGIC:
				if (prayerBufferedImages[0] == null)
				{
					prayerBufferedImages[0] = getBufferedImage("/zulrahImages/protect_from_magic.png");
				}
				return prayerBufferedImages[0];
			case PROTECT_FROM_MISSILES:
				if (prayerBufferedImages[1] == null)
				{
					prayerBufferedImages[1] = getBufferedImage("/zulrahImages/protect_from_missiles.png");
				}
				return prayerBufferedImages[1];
			default:
				if (prayerBufferedImages[2] == null)
				{
					prayerBufferedImages[2] = getBufferedImage("/zulrahImages/no_prayer.png");
				}
				return prayerBufferedImages[2];
		}
	}

	private static BufferedImage getBufferedImage(String path)
	{
		BufferedImage image = null;
		try
		{
			InputStream in = ZulrahPlugin.class.getResourceAsStream(path);
			image = ImageIO.read(in);
		}
		catch (IOException e)
		{
			log.debug("Error loading image {}", e);
		}
		return image;
	}
}
