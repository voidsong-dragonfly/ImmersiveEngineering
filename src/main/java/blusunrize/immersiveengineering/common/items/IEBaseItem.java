/*
 * BluSunrize
 * Copyright (c) 2017
 *
 * This code is licensed under "Blu's License of Common Sense"
 * Details can be found in the license file in the root folder of this project
 */

package blusunrize.immersiveengineering.common.items;

import blusunrize.immersiveengineering.ImmersiveEngineering;
import blusunrize.immersiveengineering.common.IEContent;
import blusunrize.immersiveengineering.common.gui.GuiHandler;
import blusunrize.immersiveengineering.common.items.IEItemInterfaces.IColouredItem;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class IEBaseItem extends Item implements IColouredItem
{
	public String itemName;
	private int burnTime = -1;
	private boolean isHidden = false;

	public IEBaseItem(String name)
	{
		this(name, new Properties());
	}

	public IEBaseItem(String name, Properties props)
	{
		this(name, props, ImmersiveEngineering.ITEM_GROUP);
	}

	public IEBaseItem(String name, Properties props, CreativeModeTab group)
	{
		super(props.tab(group));
		this.itemName = name;
		setRegistryName(ImmersiveEngineering.MODID, name);
		IEContent.registeredIEItems.add(this);
	}

	public IEBaseItem setBurnTime(int burnTime)
	{
		this.burnTime = burnTime;
		return this;
	}

	@Override
	public int getBurnTime(ItemStack itemStack)
	{
		return burnTime;
	}

	public boolean isHidden()
	{
		return isHidden;
	}

	public void hide()
	{
		isHidden = true;
	}

	public void unhide()
	{
		isHidden = false;
	}

	protected void openGui(Player player, EquipmentSlot slot)
	{
		ItemStack stack = player.getItemBySlot(slot);
		NetworkHooks.openGui((ServerPlayer)player, new MenuProvider()
		{
			@Nonnull
			@Override
			public Component getDisplayName()
			{
				return new TextComponent("");
			}

			@Nullable
			@Override
			public AbstractContainerMenu createMenu(int i, Inventory playerInventory, Player playerEntity)
			{
				return GuiHandler.createContainer(playerInventory, playerEntity.level, slot, stack, i);
			}
		}, buffer -> buffer.writeInt(slot.ordinal()));
	}

	public static Item.Properties withIEOBJRender()
	{
		return ImmersiveEngineering.proxy.useIEOBJRenderer(new Properties());
	}

	@Override
	public boolean isRepairable(@Nonnull ItemStack stack)
	{
		return false;
	}

	public boolean isIERepairable(@Nonnull ItemStack stack)
	{
		return super.isRepairable(stack);
	}

	@Override
	public boolean isBookEnchantable(ItemStack stack, ItemStack book)
	{
		return false;
	}
}
