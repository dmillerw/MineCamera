package dmillerw.camera.core;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import dmillerw.camera.entity.EntityCamera;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

/**
 * @author dmillerw
 */
public class EntityHandler {

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (EntityCamera.isActive()) {
            event.setCanceled(true);
        }
    }
}
