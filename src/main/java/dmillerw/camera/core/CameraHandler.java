package dmillerw.camera.core;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;

/**
 * @author dmillerw
 */
public class CameraHandler {

    public static final class CameraData {
        public final Vec3 position;
        public final float pitch;
        public final float yaw;
        public CameraData(Vec3 position, float pitch, float yaw) {
            this.position = position;
            this.pitch = pitch;
            this.yaw = yaw;
        }
    }

    public static CameraData[] cameras = new CameraData[256];

    public static int createCamera(double x, double y, double z, float pitch, float yaw) {
        CameraData cameraData = new CameraData(Vec3.createVectorHelper(x, y, z), pitch, yaw);
        for (int i=0; i<cameras.length; i++) {
            if (cameras[i] == null) {
                cameras[i] = cameraData;
                return i;
            }
        }
        return -1;
    }

    public static int createCamera(EntityPlayer player) {
        CameraData cameraData = new CameraData(Vec3.createVectorHelper(player.posX, player.posY + player.getEyeHeight(), player.posZ), player.rotationPitch, player.rotationYawHead);
        for (int i=0; i<cameras.length; i++) {
            if (cameras[i] == null) {
                cameras[i] = cameraData;
                return i;
            }
        }
        return -1;
    }

    public static CameraData getCamera(int id) {
        if (id < 0 || id >= cameras.length) {
            return null;
        }
        return cameras[id];
    }

    public static void removeCamera(int id) {
        if (id < 0 || id >= cameras.length) {
            return;
        }
        cameras[id] = null;
    }
}
