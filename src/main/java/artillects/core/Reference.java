package artillects.core;

import com.builtbroken.lib.mod.ModCreativeTab;

/** A class for static references.
 * 
 * @author DarkGuardsman */
public class Reference
{
    /** The official name of the mod */
    public static final String NAME = "Artillects";

    public static final String MAJOR_VERSION = "@MAJOR@";
    public static final String MINOR_VERSION = "@MINOR@";
    public static final String REVISION_VERSION = "@REVIS@";
    public static final String BUILD_VERSION = "@BUILD@";
    public static final String VERSION = MAJOR_VERSION + "." + MINOR_VERSION + "." + REVISION_VERSION;
    public static final String CHANNEL = "artillects";
    /** Directory Information */
    public static final String DOMAIN = "artillects";
    public static final String PREFIX = DOMAIN + ":";
    public static final String DIRECTORY = "/assets/" + DOMAIN + "/";
    public static final String TEXTURE_DIRECTORY = "textures/";
    public static final String GUI_DIRECTORY = TEXTURE_DIRECTORY + "gui/";
    public static final String BLOCK_TEXTURE_DIRECTORY = TEXTURE_DIRECTORY + "blocks/";
    public static final String ITEM_TEXTURE_DIRECTORY = TEXTURE_DIRECTORY + "items/";
    public static final String MODEL_TEXTURE_DIRECTORY = TEXTURE_DIRECTORY + "models/";
    public static final String MODEL_PATH = "models/";
    public static final String MODEL_DIRECTORY = DIRECTORY + MODEL_PATH;

    public static final ModCreativeTab CREATIVE_TAB = new ModCreativeTab("artillects");
}
