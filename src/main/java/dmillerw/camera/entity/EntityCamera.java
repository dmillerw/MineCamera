package dmillerw.camera.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * @author dmillerw
 */
@SideOnly(Side.CLIENT)
public class EntityCamera extends EntityLivingBase {

	private static EntityCamera activeCamera;

    private static EntityLivingBase activePlayer;

    public static int activeCameraId = -1;

	public static boolean isActive() {
		return activeCamera != null;
	}

	public static void createCamera() {
		if (activeCamera == null) {
			activeCamera = new EntityCamera(Minecraft.getMinecraft().renderViewEntity.worldObj);
			activePlayer = Minecraft.getMinecraft().renderViewEntity;

			activeCamera.worldObj.spawnEntityInWorld(activeCamera);

			Minecraft.getMinecraft().renderViewEntity = activeCamera;
		}
	}

    public static void moveCamera(double x, double y, double z, float pitch, float yaw) {
        if (activeCamera != null) {
            activeCamera.setPositionAndRotation(x, y, z, yaw, pitch);
        }
    }

	public static void destroyCamera() {
		if (activeCamera != null) {
			Minecraft.getMinecraft().renderViewEntity = activePlayer;
			activeCamera.worldObj.removeEntity(activeCamera);
			activeCamera.setDead();
			activeCamera = null;
		}
	}

	public EntityCamera(World world) {
		super(world);

		width = 0;
		height = 0;
	}

    @Override
    public void onEntityUpdate() {
        this.motionX = this.motionY = this.motionZ = 0;
        super.onEntityUpdate();
    }

    /* IGNORE */
	@Override
	public ItemStack getHeldItem() {
		return null;
	}

	@Override
	public ItemStack getEquipmentInSlot(int var1) {
		return null;
	}

	@Override
	public void setCurrentItemOrArmor(int var1, ItemStack var2) {

	}

	@Override
	public ItemStack[] getLastActiveItems() {
		return new ItemStack[0];
	}

}