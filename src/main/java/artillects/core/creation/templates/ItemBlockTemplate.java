package artillects.core.creation.templates;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import resonant.lib.render.EnumColor;
import resonant.lib.utility.LanguageUtility;
import resonant.lib.utility.TooltipUtility;

public class ItemBlockTemplate extends ItemBlock
{
    public ItemBlockTemplate(int id)
    {
        super(id);
        this.setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int damage)
    {
        return damage;
    }

    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer par2EntityPlayer, List list, boolean par4)
    {
        String tooltip = LanguageUtility.getLocal(getUnlocalizedName(itemStack) + ".tooltip");

        if (tooltip != null && tooltip.length() > 0)
        {
            if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
            {
                list.add(LanguageUtility.getLocal("tooltip.noShift").replace("%0", EnumColor.AQUA.toString()).replace("%1", EnumColor.GREY.toString()));
            }
            else
            {
                list.addAll(LanguageUtility.splitStringPerWord(tooltip, 5));
            }
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_J))
        {
            List<String> recipeList = new ArrayList<String>();
            TooltipUtility.addTooltip(itemStack, recipeList);
            if(recipeList.size() > 1 || !recipeList.get(0).contains(itemStack.getDisplayName()))
            {
                list.addAll(recipeList);
            }
        }
        else
        {
            list.add(LanguageUtility.getLocal("info.recipes.tooltip").replace("%0", EnumColor.AQUA.toString()).replace("%1", EnumColor.GREY.toString()));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack)
    {
        int meta = itemstack.getItemDamage();
        String localized = null;
        Block block = Block.blocksList[getBlockID()];
        if (meta >= 0 && meta < 16 && block instanceof BlockTemplate)
        {
            if (((BlockTemplate) block).content.subBlocks != null && ((BlockTemplate) block).content.subBlocks[meta] != null)
            {
                localized = LanguageUtility.getLocal("tile." + ((BlockTemplate) block).content.subBlocks[meta].unlocalizedName + ".name");
            }
        }
        if (localized == null || localized.isEmpty())
        {
            localized = LanguageUtility.getLocal(getUnlocalizedName() + "." + itemstack.getItemDamage() + ".name");
        }

        if (localized != null && !localized.isEmpty())
        {
            return localized;
        }
        return getUnlocalizedName() + "." + itemstack.getItemDamage();
    }
}