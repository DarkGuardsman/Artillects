package artillects.block.teleporter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import net.minecraft.world.World;
import artillects.Vector3;
import artillects.VectorWorld;

public class TeleportManager
{
    private static HashMap<Integer, TeleportManager> managerList = new HashMap<Integer, TeleportManager>();
    private HashSet<TileEntityTeleporterAnchor> teleporters = new HashSet<TileEntityTeleporterAnchor>();

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
            TeleportManager manager = getManagerForDim(anch.worldObj.provider.dimensionId);
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
            TeleportManager manager = getManagerForDim(anch.worldObj.provider.dimensionId);
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

    public static TileEntityTeleporterAnchor getClosestWithFrequency(VectorWorld vec, int frequency, TileEntityTeleporterAnchor... anchors)
    {
        TileEntityTeleporterAnchor tele = null;
        List<TileEntityTeleporterAnchor> ignore = new ArrayList<TileEntityTeleporterAnchor>();
        if (anchors != null)
        {
            ignore.addAll(Arrays.asList(anchors));
        }
        Iterator<TileEntityTeleporterAnchor> it = new ArrayList(TeleportManager.getConnectedAnchors(vec.world)).iterator();
        while (it.hasNext())
        {
            TileEntityTeleporterAnchor teleporter = it.next();
            if (!ignore.contains(teleporter) && teleporter.getFrequency() == frequency)
            {
                if (tele == null || new Vector3(tele).distance(vec) < new Vector3(teleporter).distance(vec))
                {
                    tele = teleporter;
                }
            }
        }
        return tele;
    }
}
