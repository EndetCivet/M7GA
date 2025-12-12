package io.civetwww.m7ga.init;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.common.Tags;

public class ModToolsTiers {
    //等级标签
    public static final Tier MODTOOLS_TIER = new SimpleTier(
            BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
            77777,
            27f,
            7f,
            27,
            () -> Ingredient.of(Tags.Items.NETHER_STARS)
    );

}
