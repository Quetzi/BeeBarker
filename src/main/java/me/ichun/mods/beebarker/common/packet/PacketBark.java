package me.ichun.mods.beebarker.common.packet;

import io.netty.buffer.ByteBuf;
import me.ichun.mods.beebarker.common.BeeBarker;
import me.ichun.mods.beebarker.common.core.BarkHelper;
import me.ichun.mods.beebarker.common.core.EventHandler;
import me.ichun.mods.beebarker.common.item.ItemBeeBarker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.relauncher.Side;
import me.ichun.mods.ichunutil.common.core.network.AbstractPacket;

public class PacketBark extends AbstractPacket
{
    public boolean pressed;

    public PacketBark(){}

    public PacketBark(boolean press)
    {
        pressed = press;
    }

    @Override
    public void writeTo(ByteBuf buffer)
    {
        buffer.writeBoolean(pressed);
    }

    @Override
    public void readFrom(ByteBuf buffer)
    {
        pressed = buffer.readBoolean();
    }

    @Override
    public AbstractPacket execute(Side side, EntityPlayer player)
    {
        if(pressed)
        {
            ItemStack is = player.getHeldItem(EnumHand.MAIN_HAND);
            if(is != null && is.getItem() == BeeBarker.itemBeeBarker && is.getTagCompound() != null && is.getTagCompound().hasKey(ItemBeeBarker.WOLF_DATA_STRING))
            {
                if(BeeBarker.config.easterEgg == 1 && ((NBTTagCompound)is.getTagCompound().getTag(ItemBeeBarker.WOLF_DATA_STRING)).hasKey("CustomName") && ((NBTTagCompound)is.getTagCompound().getTag(ItemBeeBarker.WOLF_DATA_STRING)).getString("CustomName").equals("iChun"))
                {
                    if(!BarkHelper.pressState.contains(player.getName()))
                    {
                        BarkHelper.pressState.add(player.getName());
                        player.worldObj.playSoundAtEntity(player, "mob.wolf.growl", 0.4F, 1.0F);
                        BeeBarker.channel.sendToAll(new PacketKeyState(player.getName(), true));
                    }
                }
                else
                {
                    BarkHelper.bark(player);
                }
            }
        }
        else
        {
            BarkHelper.removePressState(player.getName());
        }
        return this;
    }

    @Override
    public Side receivingSide()
    {
        return Side.CLIENT;
    }
}
