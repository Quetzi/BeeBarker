package me.ichun.mods.beebarker.common.packet;

import io.netty.buffer.ByteBuf;
import me.ichun.mods.beebarker.common.BeeBarker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;
import me.ichun.mods.ichunutil.common.core.network.AbstractPacket;

public class PacketKeyState extends AbstractPacket
{
    public String name;
    public boolean add;

    public PacketKeyState(){}

    public PacketKeyState(String name, boolean add)
    {
        this.name = name;
        this.add = add;
    }

    @Override
    public void writeTo(ByteBuf buffer)
    {
        ByteBufUtils.writeUTF8String(buffer, name);
        buffer.writeBoolean(add);
    }

    @Override
    public void readFrom(ByteBuf buffer)
    {
        name = ByteBufUtils.readUTF8String(buffer);
        add = buffer.readBoolean();
    }

    @Override
    public AbstractPacket execute(Side side, EntityPlayer player)
    {
        if(add)
        {
            if(!BeeBarker.proxy.tickHandlerClient.pressState.contains(name))
            {
                BeeBarker.proxy.tickHandlerClient.pressState.add(name);
            }
        }
        else
        {
            BeeBarker.proxy.tickHandlerClient.pressState.remove(name);
        }
        return this;
    }

    @Override
    public Side receivingSide()
    {
        return Side.SERVER;
    }
}
