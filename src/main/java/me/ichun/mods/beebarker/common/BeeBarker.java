package me.ichun.mods.beebarker.common;

import me.ichun.mods.beebarker.common.core.BarkHelper;
import me.ichun.mods.beebarker.common.core.CommonProxy;
import me.ichun.mods.beebarker.common.core.Config;
import me.ichun.mods.beebarker.common.packet.PacketBark;
import me.ichun.mods.beebarker.common.packet.PacketKeyState;
import me.ichun.mods.beebarker.common.packet.PacketSpawnParticles;
import me.ichun.mods.ichunutil.common.core.config.ConfigHandler;
import me.ichun.mods.ichunutil.common.core.network.PacketChannel;
import me.ichun.mods.ichunutil.common.iChunUtil;
import me.ichun.mods.ichunutil.common.module.update.UpdateChecker;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;

@Mod(modid = BeeBarker.MOD_NAME,
        name = BeeBarker.MOD_NAME,
        version = BeeBarker.VERSION,
        guiFactory = "me.ichun.mods.ichunutil.common.core.config.GenericModGuiFactory",
        dependencies = "required-after:iChunUtil@[" + iChunUtil.VERSION_OF_MC +".4.0," + (iChunUtil.VERSION_OF_MC + 1) + ".0.0)",
        acceptableRemoteVersions = "[" + iChunUtil.VERSION_OF_MC +".0.0," + iChunUtil.VERSION_OF_MC + ".1.0)"
)
public class BeeBarker
{
    public static final String MOD_NAME = "BeeBarker";
    public static final String VERSION = iChunUtil.VERSION_OF_MC + ".0.1";

    @Mod.Instance(MOD_NAME)
    public static BeeBarker instance;

    @SidedProxy(clientSide = "me.ichun.mods.beebarker.client.core.ClientProxy", serverSide = "me.ichun.mods.beebarker.common.core.CommonProxy")
    public static CommonProxy proxy;

    public static PacketChannel channel;

    public static Config config;

    public static Item itemBeeBarker;

    public static boolean isForestryInstalled;

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event)
    {
        config = (Config)ConfigHandler.registerConfig(new Config(event.getSuggestedConfigurationFile()));

        proxy.preInit();

        channel = new PacketChannel(MOD_NAME, PacketBark.class, PacketSpawnParticles.class, PacketKeyState.class);

        UpdateChecker.registerMod(new UpdateChecker.ModVersionInfo(MOD_NAME, iChunUtil.VERSION_OF_MC, VERSION, false));
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event)
    {
        proxy.init();

        isForestryInstalled = Loader.isModLoaded("Forestry");
    }

    @Mod.EventHandler
    public void onServerStopping(FMLServerStoppingEvent event)
    {
        BarkHelper.cooldown.clear();
        BarkHelper.pressState.clear();
    }
}
