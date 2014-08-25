package dmillerw.camera;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.relauncher.Side;
import dmillerw.camera.command.CommandCamera;
import dmillerw.camera.core.EntityHandler;
import dmillerw.camera.core.KeyHandler;
import dmillerw.camera.core.RenderHandler;
import dmillerw.camera.entity.EntityCamera;
import dmillerw.camera.helper.EventBusHelper;
import net.minecraftforge.client.ClientCommandHandler;

/**
 * @author dmillerw
 */
@Mod(modid = "MineCamera", name = "MineCamera", version = "%MOD_VERSION%", dependencies = "required-after:Forge@[%FORGE_VERSION%,)", guiFactory = "dmillerw.menu.gui.config.MineMenuGuiFactory")
public class MineCamera {

    @Mod.Instance("MineCamera")
    public static MineCamera instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        if (event.getSide() == Side.SERVER) {
            FMLLog.bigWarning("[MineCamera] You're trying to run MineCamera on a server, but it's a client only mod!");
            return;
        }

        ClientRegistry.registerKeyBinding(KeyHandler.KEY_EXIT);

        EventBusHelper.register(new RenderHandler(), EventBusHelper.Type.BOTH);
        EventBusHelper.register(new KeyHandler(), EventBusHelper.Type.FML);
        EventBusHelper.register(new EntityHandler(), EventBusHelper.Type.FORGE);

        ClientCommandHandler.instance.registerCommand(new CommandCamera());

        EntityRegistry.registerModEntity(EntityCamera.class, "EntityCamera", 0, MineCamera.instance, 30, 8, false);
    }
}
