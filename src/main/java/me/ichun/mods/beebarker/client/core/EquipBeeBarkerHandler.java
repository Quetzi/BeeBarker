package me.ichun.mods.beebarker.client.core;

import me.ichun.mods.beebarker.common.BeeBarker;
import me.ichun.mods.ichunutil.client.render.item.ItemRenderingHelper;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;

public class EquipBeeBarkerHandler implements ItemRenderingHelper.SwingProofHandler.IItemEquippedHandler
{
    @Override
    public void handleEquip(EntityPlayerSP player, ItemStack stack)
    {
        BeeBarker.proxy.tickHandlerClient.prevYaw = BeeBarker.proxy.tickHandlerClient.prevPitch = BeeBarker.proxy.tickHandlerClient.currentYaw = BeeBarker.proxy.tickHandlerClient.currentPitch = BeeBarker.proxy.tickHandlerClient.targetYaw = BeeBarker.proxy.tickHandlerClient.targetPitch = 0F;
    }

    @Override
    public boolean hideName()
    {
        return true;
    }
}
