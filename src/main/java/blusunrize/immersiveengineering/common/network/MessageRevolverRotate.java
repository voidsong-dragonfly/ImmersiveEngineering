/*
 * BluSunrize
 * Copyright (c) 2017
 *
 * This code is licensed under "Blu's License of Common Sense"
 * Details can be found in the license file in the root folder of this project
 */

package blusunrize.immersiveengineering.common.network;

import blusunrize.immersiveengineering.common.items.RevolverItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class MessageRevolverRotate implements IMessage
{
	private boolean forward;

	public MessageRevolverRotate(boolean forward)
	{
		this.forward = forward;
	}

	public MessageRevolverRotate(FriendlyByteBuf buf)
	{
		this.forward = buf.readBoolean();
	}

	@Override
	public void toBytes(FriendlyByteBuf buf)
	{
		buf.writeBoolean(this.forward);
	}

	@Override
	public void process(Supplier<Context> context)
	{
		Context ctx = context.get();
		ServerPlayer player = ctx.getSender();
		assert player!=null;
		ctx.enqueueWork(() -> {
			ItemStack equipped = player.getItemInHand(InteractionHand.MAIN_HAND);
			if(equipped.getItem() instanceof RevolverItem)
				((RevolverItem)equipped.getItem()).rotateCylinder(equipped, player, forward);
		});
	}
}