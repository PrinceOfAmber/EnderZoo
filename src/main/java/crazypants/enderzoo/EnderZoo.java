package crazypants.enderzoo;

import static crazypants.enderzoo.EnderZoo.MODID;
import static crazypants.enderzoo.EnderZoo.MOD_NAME;
import static crazypants.enderzoo.EnderZoo.VERSION;
 
import crazypants.enderzoo.config.Config; 
import crazypants.enderzoo.entity.MobInfo; 
import crazypants.enderzoo.item.ItemForCreativeMenuIcon;  
import crazypants.enderzoo.item.ItemSpawnEgg;  
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

@Mod(modid = MODID, name = MOD_NAME, version = VERSION, guiFactory = "crazypants.enderzoo.config.ConfigFactoryEnderZoo")
public class EnderZoo {

  public static final String MODID = "enderzoo";
  public static final String MOD_NAME = "Ender Zoo";
  public static final String VERSION = "@VERSION@";

  @Instance(MODID)
  public static EnderZoo instance;

  @SidedProxy(clientSide = "crazypants.enderzoo.ClientProxy", serverSide = "crazypants.enderzoo.CommonProxy")
  public static CommonProxy proxy;

  public static ItemSpawnEgg itemSpawnEgg;
 
  public static ItemForCreativeMenuIcon itemForCreativeMenuIcon;
 
 
 
   

  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {

    itemForCreativeMenuIcon = ItemForCreativeMenuIcon.create();

    Config.load(event);
    for (MobInfo mob : MobInfo.values()) {
      registerEntity(mob);
    }
    
    
    itemSpawnEgg = ItemSpawnEgg.create();
 
    FMLInterModComms.sendMessage("Waila", "register", "crazypants.enderzoo.waila.WailaCompat.load");
    proxy.preInit();
  }

  private void registerEntity(MobInfo mob) {
    EntityRegistry.registerModEntity(new ResourceLocation(MODID,mob.getName()),
        mob.getClz(), mob.getName(), mob.getEntityId(), this, 64, 3, true);
  }

  @EventHandler
  public void load(FMLInitializationEvent event) {
    instance = this;
    proxy.init();
  }

  @EventHandler
  public void postInit(FMLPostInitializationEvent event) {
 
 
  }

 

}
