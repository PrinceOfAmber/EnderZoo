package crazypants.enderzoo;

import crazypants.enderzoo.config.Config;
import crazypants.enderzoo.entity.EntityConcussionCreeper;
import crazypants.enderzoo.entity.EntityDireSlime;
import crazypants.enderzoo.entity.EntityDireWolf;
import crazypants.enderzoo.entity.EntityEnderminy;
import crazypants.enderzoo.entity.EntityFallenKnight;
import crazypants.enderzoo.entity.EntityFallenMount;
import crazypants.enderzoo.entity.EntityOwl; 
import crazypants.enderzoo.entity.EntityWitherCat;
import crazypants.enderzoo.entity.EntityWitherWitch;
import crazypants.enderzoo.entity.MobInfo;
import crazypants.enderzoo.entity.render.RenderConcussionCreeper;
import crazypants.enderzoo.entity.render.RenderDireSlime;
import crazypants.enderzoo.entity.render.RenderDirewolf;
import crazypants.enderzoo.entity.render.RenderEnderminy; 
import crazypants.enderzoo.entity.render.RenderFallenKnight;
import crazypants.enderzoo.entity.render.RenderFallenMount;
import crazypants.enderzoo.entity.render.RenderOwl;
import crazypants.enderzoo.entity.render.RenderWitherCat;
import crazypants.enderzoo.entity.render.RenderWitherWitch;  
import crazypants.enderzoo.item.ItemSpawnEgg; 
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {

  @Override
  public World getClientWorld() {
    return FMLClientHandler.instance().getClient().world;
  }

  @Override
  public EntityPlayer getClientPlayer() {
    return Minecraft.getMinecraft().player;
  }

  @Override
  public void preInit() {
    super.preInit();
    if (Config.enderminyEnabled) {
      RenderingRegistry.registerEntityRenderingHandler(EntityEnderminy.class, RenderEnderminy.FACTORY);
    }
    if (Config.concussionCreeperEnabled) {
      RenderingRegistry.registerEntityRenderingHandler(EntityConcussionCreeper.class, RenderConcussionCreeper.FACTORY);
    }
    if (Config.fallenKnightEnabled) {
      RenderingRegistry.registerEntityRenderingHandler(EntityFallenKnight.class, RenderFallenKnight.FACTORY);
    }
    if (Config.fallenMountEnabled) {
      RenderingRegistry.registerEntityRenderingHandler(EntityFallenMount.class, RenderFallenMount.FACTORY);
    }
    if (Config.witherWitchEnabled) {
      RenderingRegistry.registerEntityRenderingHandler(EntityWitherWitch.class, RenderWitherWitch.FACTORY);
    }
    if (Config.witherCatEnabled) {
      RenderingRegistry.registerEntityRenderingHandler(EntityWitherCat.class, RenderWitherCat.FACTORY);
    }
    if (Config.direWolfEnabled) {
      RenderingRegistry.registerEntityRenderingHandler(EntityDireWolf.class, RenderDirewolf.FACTORY);
    }
    if (Config.direSlimeEnabled) {
      RenderingRegistry.registerEntityRenderingHandler(EntityDireSlime.class, RenderDireSlime.FACTORY);
    }
    if (Config.owlEnabled) {      
      RenderingRegistry.registerEntityRenderingHandler(EntityOwl.class, RenderOwl.FACTORY);
    }
   // RenderingRegistry.registerEntityRenderingHandler(EntityPrimedCharge.class, RenderPrimedCharge.FACTORY);    
   

  }

  @Override
  public void init() {
    super.init();
  
   
    //Color the spawn eggs
    IItemColor handler = new IItemColor() {
      @Override
      public int getColorFromItemstack(ItemStack stack, int tintIndex) {
        int damage = MathHelper.clamp(stack.getItemDamage(), 0, MobInfo.values().length - 1);
        MobInfo mob = MobInfo.values()[damage];
        return tintIndex == 0 ? mob.getEggBackgroundColor() : mob.getEggForegroundColor();

      }
    };
    Minecraft.getMinecraft().getItemColors().registerItemColorHandler(handler, EnderZoo.itemSpawnEgg);

    for (MobInfo inf : MobInfo.values()) {
      if (inf.isEnabled()) {
        regRenderer(EnderZoo.itemSpawnEgg, inf.ordinal(), ItemSpawnEgg.NAME);
      }
    }
 
  }

  private void regRenderer(Item item, int meta, String name) {
    regRenderer(item, meta, EnderZoo.MODID, name);
  }

  private void regRenderer(Item item, int meta, String modId, String name) {
    RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
    // ItemMeshDefinition d;
    String resourceName;
    if (modId != null) {
      resourceName = modId + ":" + name;
    } else {
      resourceName = name;
    }
    renderItem.getItemModelMesher().register(item, meta, new ModelResourceLocation(resourceName, "inventory"));
  }

  private void regRenderer(Item item, String name) {
    regRenderer(item, 0, name);
  }

  @Override
  public void setInstantConfusionOnPlayer(EntityPlayer ent, int duration) {
    ent.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, duration, 1, false, true));
    Minecraft.getMinecraft().player.timeInPortal = 1;
  }

}
