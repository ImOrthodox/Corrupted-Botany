package com.eternalive.corruptedbotany;

import com.eternalive.corruptedbotany.entities.client.renders.LesserSkeletonPlantEntityRender;
import com.eternalive.corruptedbotany.entities.client.renders.LesserZombiePlantEntityRender;
import com.eternalive.corruptedbotany.registers.BlockRegistry;
import com.eternalive.corruptedbotany.registers.EntityRegistry;
import com.eternalive.corruptedbotany.registers.ItemRegistry;
import com.eternalive.corruptedbotany.registers.SoundRegistry;
import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.stream.Collectors;

@Mod(CorruptedBotany.MOD_ID)
public class CorruptedBotany
{
    public static final String MOD_ID = "corruptedbotany";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public CorruptedBotany()
    {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ItemRegistry.register(eventBus);
        BlockRegistry.register(eventBus);
        EntityRegistry.register(eventBus);
        SoundRegistry.register(eventBus);








        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register Client items
        eventBus.addListener(this::clientSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void clientSetup(final FMLClientSetupEvent event)
    {
        /*
        EntityRenderers.register(EntityRegistry.LESSERZOMBIE.get(), LesserZombiePlantEntityRender::new);
        EntityRenderers.register(EntityRegistry.LESSERSKELETON.get(), LesserSkeletonPlantEntityRender::new);
         */
    }


    private void setup(final FMLCommonSetupEvent event)
    {

        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.LESSER_ZOMBIE_PLANT.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.LESSER_SKELETON_PLANT.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.LESSER_CORRUPTED_PLANT.get(), RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.CORRUPTED_SKELETON_PLANT.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.CORRUPTED_ZOMBIE_PLANT.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.CORRUPTED_CREEPER_PLANT.get(), RenderType.cutout());

        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
       // LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // Some example code to dispatch IMC to another mod
        InterModComms.sendTo("corruptedbotany", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // Some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.messageSupplier().get()).
                collect(Collectors.toList()));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("Corrupted Botany Has Invaded");
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents
    {
        /*
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent)
        {
            // Register a new block here
            LOGGER.info("Hello from the Earth Inc crew! (we're registering our blocks)");
        }
         */

    }
}
