package dmillerw.camera.core;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import dmillerw.camera.entity.EntityCamera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelSkeletonHead;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

/**
 * @author dmillerw
 */
public class RenderHandler {

    public static void translateToWorldCoords(Entity entity, float frame) {
        double interpPosX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * frame;
        double interpPosY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * frame;
        double interpPosZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * frame;
        GL11.glTranslated(-interpPosX, -interpPosY, -interpPosZ);
    }

    private static Minecraft mc = Minecraft.getMinecraft();

    private static ModelSkeletonHead head = new ModelSkeletonHead(0, 0, 64, 32);

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        GL11.glPushMatrix();

        EntityLivingBase renderViewEntity = Minecraft.getMinecraft().renderViewEntity;

        RenderHandler.translateToWorldCoords(renderViewEntity, event.partialTicks);

        mc.getTextureManager().bindTexture(AbstractClientPlayer.locationStevePng);

        for (CameraHandler.CameraData cameraData : CameraHandler.cameras) {
            if (cameraData != null) {
                GL11.glPushMatrix();

                GL11.glTranslated(cameraData.position.xCoord, cameraData.position.yCoord, cameraData.position.zCoord);

                GL11.glTranslated(0, -0.25, 0);
                GL11.glRotated(-cameraData.yaw, 0, 1, 0);
                GL11.glRotated(cameraData.pitch + 180, 1, 0, 0);
                GL11.glTranslated(0, 0.25, 0);

                head.render(null, 0, 0, 0, 0, 0, 0.0625F);

                GL11.glPopMatrix();
            }
        }

        GL11.glPopMatrix();
    }

    @SubscribeEvent
    public void renderHandEvent(RenderHandEvent event) {
        if (EntityCamera.isActive()) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void renderHudEvent(RenderGameOverlayEvent event) {
        if (EntityCamera.isActive() &&
            event.type == RenderGameOverlayEvent.ElementType.HOTBAR ||
            event.type == RenderGameOverlayEvent.ElementType.AIR ||
            event.type == RenderGameOverlayEvent.ElementType.ARMOR ||
            event.type == RenderGameOverlayEvent.ElementType.CROSSHAIRS) {

            event.setCanceled(true);
        }
    }
}
