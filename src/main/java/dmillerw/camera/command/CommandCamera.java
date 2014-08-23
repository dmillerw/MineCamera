package dmillerw.camera.command;

import dmillerw.camera.core.CameraHandler;
import dmillerw.camera.entity.EntityCamera;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

/**
 * @author dmillerw
 */
public class CommandCamera extends CommandBase {

    @Override
    public String getCommandName() {
        return "camera";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/camera [create/list/switch/remove/back]";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length > 0) {
            String arg = args[0];

            if (arg.equalsIgnoreCase("create")) {
                if (args.length == 1) {
                    int camera = CameraHandler.createCamera((EntityPlayer) sender);
                    if (camera >= 0) {
                        sender.addChatMessage(new ChatComponentText("Created camera #" + camera));
                        return;
                    }
                } else {
                    if (args.length == 6) {
                        try {
                            double x = Double.parseDouble(args[1]);
                            double y = Double.parseDouble(args[2]);
                            double z = Double.parseDouble(args[3]);
                            float pitch = Float.parseFloat(args[4]);
                            float yaw = Float.parseFloat(args[5]);

                            int camera = CameraHandler.createCamera(x, y, z, pitch, yaw);
                            if (camera >= 0) {
                                sender.addChatMessage(new ChatComponentText("Created camera #" + camera));
                                return;
                            }
                        } catch (NumberFormatException ex) {
                            throw new WrongUsageException("/camera create <x, y, z, pitch, yaw>");
                        }
                    }
                }
            } else if (arg.equalsIgnoreCase("list")) {
                for (int i=0; i<256; i++) {
                    CameraHandler.CameraData cameraData = CameraHandler.getCamera(i);
                    if (cameraData != null) {
                        sender.addChatMessage(new ChatComponentText(String.format("Camera #%s [%s, %s, %s] <%s, %s>", i, cameraData.position.xCoord, cameraData.position.yCoord, cameraData.position.zCoord, cameraData.pitch, cameraData.yaw)));
                        return;
                    }
                }
            } else if (arg.equalsIgnoreCase("remove")) {
                if (args.length == 2) {
                    if (args[1].equalsIgnoreCase("all")) {
                        CameraHandler.cameras = new CameraHandler.CameraData[256];
                        EntityCamera.destroyCamera();
                        return;
                    } else {
                        try {
                            CameraHandler.removeCamera(Integer.parseInt(args[1]));
                            return;
                        } catch (NumberFormatException ex) {
                            throw new WrongUsageException("/camera remove <id>");
                        }
                    }
                }
            } else if (arg.equalsIgnoreCase("switch")) {
                if (args.length == 2) {
                    try {
                        CameraHandler.CameraData cameraData = CameraHandler.getCamera(Integer.parseInt(args[1]));
                        if (cameraData != null) {
                            EntityCamera.createCamera();
                            EntityCamera.activeCameraId = Integer.parseInt(args[1]);
                            EntityCamera.moveCamera(cameraData.position.xCoord, cameraData.position.yCoord, cameraData.position.zCoord, cameraData.pitch, cameraData.yaw);
                            return;
                        }
                    } catch (NumberFormatException ex) {
                        throw new WrongUsageException("/camera switch <id>");
                    }
                }
            } else if (arg.equals("back")) {
                EntityCamera.destroyCamera();
                return;
            }
        }

        throw new WrongUsageException("/camera [create/list/switch/remove/back]");
    }
}
