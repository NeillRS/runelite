package net.runelite.client.plugins.zulrah.overlays;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Prayer;
import net.runelite.client.plugins.zulrah.ZulrahInstance;
import net.runelite.client.plugins.zulrah.ZulrahPlugin;
import net.runelite.client.plugins.zulrah.phase.ZulrahPhase;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.components.ComponentConstants;
import net.runelite.client.ui.overlay.components.ImageComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;

import javax.inject.Inject;
import java.awt.*;
import java.awt.image.BufferedImage;

@Slf4j
public class ZulrahPhaseInfoOverlay extends Overlay {

    private final Client client;
    private final ZulrahPlugin plugin;
    private final PanelComponent mainPanelComponent = new PanelComponent();
    private final PanelComponent currentPhaseOverlayComponent = new PanelComponent();
    private final PanelComponent nextPhaseOverlayComponent = new PanelComponent();
    private final PanelComponent prayerOverlayComponent = new PanelComponent();

    @Inject
    public ZulrahPhaseInfoOverlay(Client client, ZulrahPlugin plugin){

        this.client = client;
        this.plugin = plugin;

        setPosition(OverlayPosition.BOTTOM_LEFT);
        setPriority(OverlayPriority.HIGH);
        mainPanelComponent.setOrientation(PanelComponent.Orientation.HORIZONTAL);
        currentPhaseOverlayComponent.setOrientation(PanelComponent.Orientation.VERTICAL);
        currentPhaseOverlayComponent.setPreferredSize(new Dimension(70,70));
        nextPhaseOverlayComponent.setOrientation(PanelComponent.Orientation.VERTICAL);
        nextPhaseOverlayComponent.setPreferredSize(new Dimension(70,70));
    }

    @Override
    public Dimension render(Graphics2D graphics){

        clearComponents();
        ZulrahInstance instance = plugin.getInstance();
        if (instance == null){
            return null;
        }
        ZulrahPhase currentPhase = instance.getPhase();
        if (currentPhase == null)
        {
            return null;
        } else {
            renderCurrentPhaseOverlay(instance);
        }
        ZulrahPhase nextPhase = instance.getNextPhase();
        if (nextPhase != null){
            renderNextPhaseOverlay(instance);
        }
        Prayer prayer = currentPhase.isJad() ? null : currentPhase.getPrayer();
        if (prayer != null){
            renderPrayerOverlay(instance);
        }
        return mainPanelComponent.render(graphics);
    }

    private void renderCurrentPhaseOverlay(ZulrahInstance instance){

        ZulrahPhase currentPhase = instance.getPhase();
        BufferedImage zulrahImage = ZulrahImageManager.getZulrahBufferedImage(currentPhase.getType());
        mainPanelComponent.getChildren().add(new ImageComponent(zulrahImage));
    }

    private void renderNextPhaseOverlay(ZulrahInstance instance){

        ZulrahPhase nextPhase = instance.getNextPhase();
        BufferedImage zulrahImage = ZulrahImageManager.getSmallZulrahBufferedImage(nextPhase.getType());
        mainPanelComponent.getChildren().add(new ImageComponent(zulrahImage));
    }

    private void renderPrayerOverlay( ZulrahInstance instance){
        ZulrahPhase currentPhase = instance.getPhase();
        Prayer prayer = currentPhase.isJad() ? null : currentPhase.getPrayer();
        if (prayer != null){
            BufferedImage prayerImage = ZulrahImageManager.getProtectionPrayerBufferedImage(prayer);
            prayerOverlayComponent.getChildren().add(new ImageComponent(prayerImage));
            if (!client.isPrayerActive(prayer)){
                prayerOverlayComponent.setBackgroundColor(new Color(150, 20, 20));
            } else {
                prayerOverlayComponent.setBackgroundColor(ComponentConstants.STANDARD_BACKGROUND_COLOR);
            }
        } else {
            BufferedImage prayerImage = ZulrahImageManager.getProtectionPrayerBufferedImage(Prayer.THICK_SKIN); //placeholder to trigger no prayer default.
            prayerOverlayComponent.getChildren().add(new ImageComponent(prayerImage));
        }
        mainPanelComponent.getChildren().add(prayerOverlayComponent);
    }

    private void clearComponents(){
        mainPanelComponent.getChildren().clear();
        currentPhaseOverlayComponent.getChildren().clear();
        nextPhaseOverlayComponent.getChildren().clear();
        prayerOverlayComponent.getChildren().clear();
    }
}
