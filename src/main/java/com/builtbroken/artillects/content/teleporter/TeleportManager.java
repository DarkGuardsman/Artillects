package com.builtbroken.artillects.content.teleporter;

import com.builtbroken.mc.imp.transform.vector.Pos;
import com.builtbroken.mc.imp.transform.vector.Location;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

import java.util.*;

public class TeleportManager
{
	private static HashMap<Integer, TeleportManager> managerList = new HashMap<Integer, TeleportManager>();
	private HashSet<TileEntityTeleporterAnchor> teleporters = new HashSet<TileEntityTeleporterAnchor>();
	private static HashMap<String, Long> coolDown = new HashMap<String, Long>();

	public static TeleportManager getManagerForDim(int dim)
	{
		if (managerList.get(dim) == null)
		{
			managerList.put(dim, new TeleportManager());
		}
		return managerList.get(dim);
	}

	/** Adds a teleport anchor to this list or anchors */
	public static void addAnchor(TileEntityTeleporterAnchor anch)
	{
		if (anch != null)
		{
			TeleportManager manager = getManagerForDim(anch.getWorldObj().provider.dimensionId);
			if (!manager.teleporters.contains(anch))
			{
				manager.teleporters.add(anch);
			}
		}
	}

	/** Removes a teleport anchor to this list or anchors */
	public static void remAnchor(TileEntityTeleporterAnchor anch)
	{
		if (anch != null)
		{
			TeleportManager manager = getManagerForDim(anch.getWorldObj().provider.dimensionId);
			manager.teleporters.remove(anch);
		}
	}

	public static HashSet<TileEntityTeleporterAnchor> getConnectedAnchors(World world)
	{
		return getManagerForDim(world.provider.dimensionId).teleporters;
	}

	public boolean contains(TileEntityTeleporterAnchor anch)
	{
		return teleporters.contains(anch);
	}

	public static TileEntityTeleporterAnchor getClosestWithFrequency(Location vec, int frequency, TileEntityTeleporterAnchor... anchors)
	{
		TileEntityTeleporterAnchor tele = null;
		List<TileEntityTeleporterAnchor> ignore = new ArrayList<TileEntityTeleporterAnchor>();
		if (anchors != null)
		{
			ignore.addAll(Arrays.asList(anchors));
		}
		Iterator<TileEntityTeleporterAnchor> it = new ArrayList(TeleportManager.getConnectedAnchors(vec.world())).iterator();
		while (it.hasNext())
		{
			TileEntityTeleporterAnchor teleporter = it.next();
			if (!ignore.contains(teleporter) && teleporter.getFrequency() == frequency)
			{
				if (tele == null || new Pos(tele).distance(vec) > new Pos(teleporter).distance(vec))
				{
					tele = teleporter;
				}
			}
		}
		return tele;
	}

	protected static void moveEntity(Entity entity, Location location)
	{
		if (entity != null && location != null)
		{
			location.world().markBlockForUpdate((int) location.x(), (int) location.y(), (int) location.z());
			if (entity instanceof EntityPlayerMP)
			{
				if (coolDown.get(entity.getCommandSenderName()) == null || (System.currentTimeMillis() - coolDown.get(entity.getCommandSenderName()) > 30))
				{
					((EntityPlayerMP) entity).playerNetServerHandler.setPlayerLocation(location.x(), location.y(), location.z(), 0, 0);
					coolDown.put(entity.getCommandSenderName(), System.currentTimeMillis());
				}
			}
			else
			{
				entity.setPosition(location.x(), location.y(), location.z());
			}
		}
	}
}
