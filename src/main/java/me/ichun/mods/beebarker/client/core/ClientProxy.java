package me.ichun.mods.beebarker.client.core;

import me.ichun.mods.beebarker.client.render.RenderBee;
import me.ichun.mods.beebarker.common.BeeBarker;
import me.ichun.mods.beebarker.common.core.CommonProxy;
import me.ichun.mods.beebarker.common.entity.EntityBee;
import me.ichun.mods.beebarker.common.item.ItemBeeBarker;
import me.ichun.mods.ichunutil.client.render.item.ItemRenderingHelper;
import me.ichun.mods.ichunutil.common.iChunUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class ClientProxy extends CommonProxy
{
    @Override
    public void preInit()
    {
        super.preInit();

        iChunUtil.proxy.registerMinecraftKeyBind(Minecraft.getMinecraft().gameSettings.keyBindUseItem);

        tickHandlerClient = new TickHandlerClient();
        FMLCommonHandler.instance().bus().register(tickHandlerClient);
    }

    @Override
    public void init()
    {
        super.init();

        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(BeeBarker.itemBeeBarker, 0, new ModelResourceLocation("beebarker:BeeBarker", "inventory"));

        ItemRenderingHelper.registerBowAnimationLockedItem(ItemBeeBarker.class);
        ItemRenderingHelper.registerSwingProofItem(new ItemRenderingHelper.SwingProofHandler(ItemBeeBarker.class, new EquipBeeBarkerHandler()));

        RenderingRegistry.registerEntityRenderingHandler(EntityBee.class, new RenderBee());
    }
}
