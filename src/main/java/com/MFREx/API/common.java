package com.MFREx.API;

import minefantasy.mfr.api.refine.BigFurnaceRecipes;
import minefantasy.mfr.config.ConfigHardcore;
import minefantasy.mfr.recipe.DummyRecipe;
import minefantasy.mfr.recipe.refine.BloomRecipe;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

//A collection of methods that will probably be rendered useless once the data-driven update occurs
public class common {

    //Removes a crafting table recipe... I think, these two are totally untested :)
    public static void removeRecipe (Block input) {
        ForgeRegistry<IRecipe> recipeRegistry = (ForgeRegistry<IRecipe>) ForgeRegistries.RECIPES;
        recipeRegistry.remove(input.getRegistryName());
    }
    public static void removeRecipe (Item input) {
        ForgeRegistry<IRecipe> recipeRegistry = (ForgeRegistry<IRecipe>) ForgeRegistries.RECIPES;
        recipeRegistry.remove(input.getRegistryName());
    }

    //Removes a furnace recipe
    public static void removeSmeltingRecipe(Block ore) {
        if (ConfigHardcore.HCCreduceIngots) {
            Map<ItemStack, ItemStack> SmeltingRecipes = FurnaceRecipes.instance().getSmeltingList();
            ItemStack oreItem = null;
            for (ItemStack item : SmeltingRecipes.keySet()) {
                if (item.getItem() == Item.getItemFromBlock(ore)) {
                    oreItem = item;
                }
            }
            if (oreItem != null) {
                FurnaceRecipes.instance().getSmeltingList().remove(oreItem);
            }
        }

    }

    //These two remove an MFR recipe
    private static void mfrRemoveRecipes(final Item output) {
        mfrRemoveRecipes(recipe -> {
            final ItemStack recipeOutput = recipe.getRecipeOutput();
            return !recipeOutput.isEmpty() && recipeOutput.getItem() == output;
        });
    }

    private static int mfrRemoveRecipes(final Predicate<IRecipe> predicate) {
        final IForgeRegistry<IRecipe> registry = ForgeRegistries.RECIPES;
        final List<IRecipe> toRemove = new ArrayList<>();

        for (final IRecipe recipe : registry) {
            if (predicate.test(recipe)) {
                toRemove.add(recipe);
            }
        }

        toRemove.forEach(recipe -> {
            final ResourceLocation registryName = Objects.requireNonNull(recipe.getRegistryName());
            final IRecipe replacement = new DummyRecipe().setRegistryName(registryName);
            registry.register(replacement);
        });

        return 0;
    }

    //Add a recipe to both the bloomery and the big furnace
    private static void addsmeltall (ItemStack ore, ItemStack bar) {
        if (ConfigHardcore.HCCreduceIngots) {
            BloomRecipe.addRecipe(ore, bar);
        }
        BigFurnaceRecipes.addRecipe(ore, bar, 0);
    }

    //This turns two strings into a JSON file in Minefantasy's anvil_recipes folder
    private static void anvilRecipeWriter(FMLPreInitializationEvent event, String recipeName, String recipe) {
        try {
            String path = "MineFantasyReforged/custom/recipes/anvil_recipes/";
            File file = new File(event.getModConfigurationDirectory(), path + recipeName + ".json");
            file.deleteOnExit();
            PrintWriter writer = new PrintWriter(file);
            writer.println(recipe);
            writer.flush();

            writer.close();
        }
        catch(IOException e) {
        }
    }

    //This turns two strings into a JSON file in Minefantasy's carpenter_recipes folder
    private static void carpenterRecipeWriter(FMLPreInitializationEvent event, String recipeName, String recipe) {
        try {
            String path = "MineFantasyReforged/custom/recipes/carpenter_recipes/";
            File file = new File(event.getModConfigurationDirectory(), path + recipeName + ".json");
            file.deleteOnExit();
            PrintWriter writer = new PrintWriter(file);
            writer.println(recipe);
            writer.flush();

            writer.close();
        }
        catch(IOException e) {
        }
    }


}
