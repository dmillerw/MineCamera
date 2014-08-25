package dmillerw.camera.core;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import dmillerw.camera.entity.EntityCamera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

/**
 * @author dmillerw
 */
public class KeyHandler {

    public static final KeyBinding KEY_EXIT = new KeyBinding("key.camera.exit", 29 /* LEFT CONTROL */, "key.category.camera");

    @SubscribeEvent
    public void onKeyEvent(InputEvent.KeyInputEvent event) {
        if (EntityCamera.isActive()) {
            Minecraft mc = Minecraft.getMinecraft();
            if (!mc.isGamePaused() && mc.inGameHasFocus && mc.currentScreen == null) {
                if (KEY_EXIT.getIsKeyPressed()) {
                    EntityCamera.destroyCamera();
                }
            }
        }
    }
}
