package artillects.core.surveyor;

import org.lwjgl.opengl.GL11;

import resonant.lib.render.RenderTaggedTile;
import resonant.lib.render.RenderUtility;
import artillects.core.Reference;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class RenderSurveyor extends RenderTaggedTile
{
    public final static IModelCustom MODEL = AdvancedModelLoader.loadModel(Reference.MODEL_DIRECTORY + "SurveyorCam.tcn");
    public final static ResourceLocation TEXTURE = new ResourceLocation(Reference.DOMAIN, Reference.MODEL_TEXTURE_DIRECTORY + "SurveyorCam.png");
    public final static String[] YAW_ONLY = {"base", "left", "right"};
    
    @Override
    public void renderTileEntityAt(TileEntity t, double x, double y, double z, float f)
    {
        super.renderTileEntityAt(t, x, y, z, f);
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
        RenderUtility.bind(RenderSurveyor.TEXTURE);
        
        //YAW
        if(t instanceof TileSurveyor)
        {
            GL11.glRotated(((TileSurveyor)t).angle.yaw(), 0, 1, 0);
        }     
        MODEL.renderOnly(YAW_ONLY);
        
        //PITCH
        if(t instanceof TileSurveyor)
        {
            GL11.glRotated(((TileSurveyor)t).angle.pitch(), 1, 0, 0);
        }
        MODEL.renderAllExcept(YAW_ONLY);
        
        GL11.glPopMatrix();
        
    }

}
