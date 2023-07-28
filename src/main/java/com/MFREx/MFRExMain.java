package com.MFREx;

import com.MFREx.proxy.CommonProxy;
import minefantasy.mfr.init.MineFantasyItems;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.logging.Logger;

@Mod(modid = MFRExMain.MODID, name = MFRExMain.NAME, version = MFRExMain.VERSION)
public class MFRExMain {
    public static final String MODID = "mfrex";
    public static final String NAME = "mfrex";
    public static final String VERSION = "0.1";

    @SidedProxy(clientSide = "com.MFREx.proxy.ClientProxy", serverSide = "com.MFREx.proxy.CommonProxy")
    public static CommonProxy PROXY;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        System.out.println("Waka: " + MineFantasyItems.SALT);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        System.out.println();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }
}