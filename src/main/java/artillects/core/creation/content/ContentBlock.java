package artillects.core.creation.content;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import resonant.lib.content.ContentRegistry;
import artillects.core.creation.ContentLoader;
import artillects.core.creation.MaterialData;
import artillects.core.creation.Subblock;
import artillects.core.creation.templates.BlockTemplate;
import artillects.core.creation.templates.ItemBlockTemplate;

/** Creation system and data storage for creating new basic blocks from xml data
 * 
 * @author Darkguardsman */
public class ContentBlock extends Content
{
    public float hardness = 1;
    public float resistance = 1;
    public String unlocalizedName;
    public Material material = Material.circuits;
    public Subblock[] subBlocks;
    public String iconName;

    public Block block;

    public ContentBlock(ContentLoader loader)
    {
        super(loader);
    }

    /** Called to load the content's data from an xml document */
    public void loadData(Document doc)
    {
        subBlocks = new Subblock[16];
        NodeList blockList = doc.getElementsByTagName("block");
        for (int b = 0; b < blockList.getLength(); b++)
        {
            if (blockList.item(b).getNodeType() == Node.ELEMENT_NODE)
            {
                Element block = (Element) blockList.item(b);
                if (block.hasAttribute("name"))
                {
                    unlocalizedName = block.getAttribute("name");
                    if (block.hasAttribute("icon"))
                    {
                        iconName = block.getAttribute("icon");
                    }
                    if (block.hasAttribute("hardness"))
                    {
                        this.hardness = Float.parseFloat(block.getAttribute("hardness"));
                    }
                    if (block.hasAttribute("resistance"))
                    {
                        this.resistance = Float.parseFloat(block.getAttribute("resistance"));
                    }
                    if (block.hasAttribute("material"))
                    {
                        String matName = block.getAttribute("material");
                        for (MaterialData data : MaterialData.values())
                        {
                            if (data.name().equalsIgnoreCase(matName))
                            {
                                this.material = data.material;
                                break;
                            }
                        }
                    }
                    NodeList metaList = block.getElementsByTagName("meta");
                    int aMeta = 0;
                    for (int m = 0; m < metaList.getLength(); m++)
                    {
                        if (metaList.item(m).getNodeType() == Node.ELEMENT_NODE)
                        {
                            Element meta = (Element) metaList.item(m);
                            if (meta.hasAttribute("id"))
                            {
                                Subblock subblock = new Subblock();
                                subblock.unlocalizedName = meta.hasAttribute("name") ? meta.getAttribute("name") : unlocalizedName;                            
                                if (meta.hasAttribute("hardness"))
                                {
                                    subblock.hardness = Float.parseFloat(meta.getAttribute("hardness"));
                                }
                                if (meta.hasAttribute("resistance"))
                                {
                                    subblock.resistance = Float.parseFloat(meta.getAttribute("resistance"));
                                }
                                //Icon per side
                                NodeList iconList = meta.getElementsByTagName("icon");
                                for (int i = 0; i < iconList.getLength(); i++)
                                {
                                    Element icon = (Element) iconList.item(i);
                                    int side = icon.hasAttribute("side") ? Integer.parseInt(icon.getAttribute("side")) : -1;
                                    String name = icon.hasAttribute("name") ? icon.getAttribute("name") : null;
                                    if (name != null)
                                    {
                                        subblock.addSideIcon(side, name);
                                    }
                                }
                                //Meta value
                                if (meta.hasAttribute("id"))
                                {
                                    subBlocks[Integer.parseInt(meta.getAttribute("id"))] = subblock;
                                }
                                else
                                {
                                    while (true)
                                    {
                                        if (aMeta >= 15)
                                        {
                                            break;
                                        }
                                        if (subBlocks[aMeta] == null)
                                        {
                                            subBlocks[aMeta] = subblock;
                                            break;
                                        }
                                        aMeta++;
                                    }
                                }
                            }
                            else
                            {
                                throw new IllegalArgumentException("Block " + unlocalizedName + "'s subblock meta must have a meta id");
                            }
                        }
                    }
                }
                else
                {
                    throw new IllegalArgumentException("BlockFile " + doc.getDocumentURI() + " doesn't contain a name attribute");
                }
            }
        }
    }

    @Override
    public void create(ContentRegistry creator)
    {
        int assignedID = creator.idManager.getNextBlockID();
        int actualID = creator.config.getBlock(unlocalizedName, assignedID).getInt(assignedID);
        block = new BlockTemplate(actualID, material);
        block.setUnlocalizedName(unlocalizedName);
        block.setHardness(this.hardness);
        block.setResistance(this.resistance);
        block.setCreativeTab(creator.defaultTab);
        ContentRegistry.proxy.registerBlock(creator, block, ItemBlockTemplate.class, unlocalizedName, creator.modID);
        ((BlockTemplate) block).content = this;
    }
}
