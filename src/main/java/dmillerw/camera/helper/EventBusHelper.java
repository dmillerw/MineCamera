package dmillerw.camera.helper;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraftforge.common.MinecraftForge;

/**
 * @author dmillerw
 */
public class EventBusHelper {

    public static enum Type {
        FORGE,
        FML,
        BOTH
    }

    public static void register(Object handler, Type type) {
        if (type == Type.FORGE || type == Type.BOTH) {
            MinecraftForge.EVENT_BUS.register(handler);
        }

        if (type == Type.FML || type == Type.BOTH) {
            FMLCommonHandler.instance().bus().register(handler);
        }
    }
}
