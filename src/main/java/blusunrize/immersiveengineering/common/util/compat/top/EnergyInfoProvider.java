/*
 * BluSunrize
 * Copyright (c) 2020
 *
 * This code is licensed under "Blu's License of Common Sense"
 * Details can be found in the license file in the root folder of this project
 */
package blusunrize.immersiveengineering.common.util.compat.top;

import blusunrize.immersiveengineering.ImmersiveEngineering;
import blusunrize.immersiveengineering.api.Lib;
import blusunrize.immersiveengineering.api.energy.immersiveflux.IFluxProvider;
import blusunrize.immersiveengineering.api.energy.immersiveflux.IFluxReceiver;
import mcjty.theoneprobe.api.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * @author Robustprogram - 26.1.2020
 */
public class EnergyInfoProvider implements IProbeInfoProvider, IProbeConfigProvider
{

	@Override
	public String getID()
	{
		return ImmersiveEngineering.MODID+":"+"EnergyInfo";
	}

	@Override
	public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, Player player, Level world,
		BlockState blockState, IProbeHitData data)
	{
		BlockEntity tileEntity = world.getBlockEntity(data.getPos());
		int cur = 0;
		int max = 0;
		if (tileEntity instanceof IFluxReceiver)
		{
			cur = ((IFluxReceiver) tileEntity).getEnergyStored(null);
			max = ((IFluxReceiver) tileEntity).getMaxEnergyStored(null);
		}
		else if (tileEntity instanceof IFluxProvider)
		{
			cur = ((IFluxProvider) tileEntity).getEnergyStored(null);
			max = ((IFluxProvider) tileEntity).getMaxEnergyStored(null);
		}
		if (max > 0)
		{
			probeInfo.progress(cur, max,
					probeInfo.defaultProgressStyle()
							.suffix("IF")
							.filledColor(Lib.COLOUR_I_ImmersiveOrange)
							.alternateFilledColor(0xff994f20)
							.borderColor(Lib.COLOUR_I_ImmersiveOrangeShadow)
							.numberFormat(NumberFormat.COMPACT));
		}
	}

	@Override
	public void getProbeConfig(IProbeConfig config, Player player, Level world, Entity entity,
		IProbeHitEntityData data)
	{
	}

	@Override
	public void getProbeConfig(IProbeConfig config, Player player, Level world,
		BlockState blockState, IProbeHitData data)
	{
		BlockEntity tileEntity = world.getBlockEntity(data.getPos());
		if(tileEntity instanceof IFluxReceiver||tileEntity instanceof IFluxProvider)
		{
			config.setRFMode(0);				
		}
	}
}
