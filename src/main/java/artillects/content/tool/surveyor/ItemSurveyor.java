package artillects.content.tool.surveyor;

import artillects.content.tool.ItemPlaceableTool;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/** Multi-purpose tool for measuring distances, setting up areas, claiming land, or declairing tasks
 * for areas.
 * 
 * @author Darkguardsman */
public class ItemSurveyor extends ItemPlaceableTool
{
    public ItemSurveyor(Block block) {
        super(block);
    }

    @Override
    public boolean used(EntityPlayer player, World world, int x, int y, int z, int side)
    {
        return false;
    }
}
