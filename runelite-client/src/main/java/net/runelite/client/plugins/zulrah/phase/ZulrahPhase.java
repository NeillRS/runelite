package net.runelite.client.plugins.raidsZ.phase;

import net.runelite.api.NPC;
import net.runelite.api.Prayer;
import net.runelite.api.coords.WorldPoint;

import java.awt.Color;

public class ZulrahPhase {
    private static final Color RANGE_COLOR = new Color(150, 255, 0, 100);
    private static final Color MAGIC_COLOR = new Color(20, 170, 200, 100);
    private static final Color MELEE_COLOR = new Color(180, 50, 20, 100);
    private static final Color JAD_COLOR = new Color(71, 22, 59, 227);

    private final ZulrahLocation zulrahLocation;
    private final ZulrahType type;
    private final boolean jad;
    private final StandLocation standLocation;
    private final Prayer prayer;

    public ZulrahPhase(ZulrahLocation zulrahLocation, ZulrahType type, boolean jad, StandLocation standLocation, Prayer prayer) {
        this.zulrahLocation = zulrahLocation;
        this.type = type;
        this.jad = jad;
        this.standLocation = standLocation;
        this.prayer = prayer;
    }

    public static ZulrahPhase valueOf(NPC zulrah, WorldPoint start) {
        ZulrahLocation zulrahLocation = ZulrahLocation.calculateZulrahLocation(start, zulrah.getWorldLocation());
        ZulrahType zulrahType = ZulrahType.getZulrahTypeByID(zulrah.getId());
        if (zulrahLocation == null || zulrahType == null) {
            return null;
        }
        StandLocation standLocation = zulrahType == ZulrahType.MAGIC ? StandLocation.PILLAR_WEST_OUTSIDE : StandLocation.TOP_EAST;
        Prayer prayer = zulrahType == ZulrahType.MAGIC ? Prayer.PROTECT_FROM_MAGIC : null;
        return new ZulrahPhase(zulrahLocation, zulrahType, false, standLocation, prayer);
    }


    public WorldPoint getStandWorlPoint(WorldPoint zulrahStartWorldPoint) {
        switch (standLocation) {
            case WEST:
                return zulrahStartWorldPoint.dx(-3);
            case EAST:
                return zulrahStartWorldPoint.dx(7).dy(0);
            case SOUTH:
                return zulrahStartWorldPoint.dy(-4);
            case SOUTH_WEST:
                return zulrahStartWorldPoint.dx(-2).dy(-2);
            case SOUTH_EAST:
                return zulrahStartWorldPoint.dx(4).dy(-4);
            case TOP_EAST:
                return zulrahStartWorldPoint.dx(8).dy(4);
            case TOP_WEST:
                return zulrahStartWorldPoint.dx(-2).dy(5);
            case PILLAR_WEST_INSIDE:
                return zulrahStartWorldPoint.dx(-2).dy(-1);
            case PILLAR_WEST_OUTSIDE:
                return zulrahStartWorldPoint.dx(-3).dy(-1);
            case PILLAR_EAST_INSIDE:
                return zulrahStartWorldPoint.dx(6).dy(-1);
            case PILLAR_EAST_OUTSIDE:
                return zulrahStartWorldPoint.dx(6).dy(-2);
        }
        return zulrahStartWorldPoint;
    }

    public ZulrahLocation getZulrahLocation() {
        return zulrahLocation;
    }

    public ZulrahType getType() {
        return type;
    }

    public boolean isJad() {
        return jad;
    }

    public StandLocation getStandLocation() {
        return standLocation;
    }

    public Prayer getPrayer() {
        return prayer;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ZulrahPhase other = (ZulrahPhase) obj;
        return this.jad == other.jad && this.zulrahLocation == other.zulrahLocation && this.type == other.type;
    }


    public Color getRelevantColor() {
        switch (type) {
            case RANGE:
                return RANGE_COLOR;
            case MAGIC:
                return MAGIC_COLOR;
            case MELEE:
                return MELEE_COLOR;
            case JADMR:
            case JADRM:
                return JAD_COLOR;
        }
        return RANGE_COLOR;
    }
}
