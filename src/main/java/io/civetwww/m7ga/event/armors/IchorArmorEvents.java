package io.civetwww.m7ga.event.armors;

import io.civetwww.m7ga.M7GAConfig;
import io.civetwww.m7ga.common.items.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.Objects;
import java.util.Random;

import static io.civetwww.m7ga.M7GA.MODID;

@EventBusSubscriber
public class IchorArmorEvents {

    private static final ResourceLocation ICHOR_ARMOR_SPEED = ResourceLocation.fromNamespaceAndPath(MODID, "ichor_armor_speed");
    private static final ResourceLocation ICHOR_ARMOR_HEALTH = ResourceLocation.fromNamespaceAndPath(MODID, "ichor_armor_health");
    private static final ResourceLocation ICHOR_ARMOR_ATTACK_DAMAGE = ResourceLocation.fromNamespaceAndPath(MODID, "ichor_armor_attack_damage");
    private static final ResourceLocation ICHOR_ARMOR_KNOCKBACK = ResourceLocation.fromNamespaceAndPath(MODID, "ichor_armor_knockback_resistance");

    @SubscribeEvent
    public static void onPlayerTick(EntityTickEvent.Post event) {
        // 只处理玩家实体
        if (!(event.getEntity() instanceof Player player)) return;
        
        // 检查是否启用了护甲效果
        if (!M7GAConfig.getInstance().isArmorEffectsEnabled()) {
            removeAllArmorEffects(player);
            return;
        }
        
        // 检查是否穿着全套Ichor护甲
        if (hasFullIchorArmor(player)) {
            applyFullArmorEffects(player);
        } else {
            removeAllArmorEffects(player);
        }
    }
    
    /**
     * 应用全套Ichor护甲的所有效果
     */
    private static void applyFullArmorEffects(Player player) {
        applyAttributeModifiers(player);
        applyPotionEffects(player);
        applyHungerRegeneration(player);
    }
    
    /**
     * 移除所有Ichor护甲效果
     */
    private static void removeAllArmorEffects(Player player) {
        removeFullSetAttributes(player);
    }
    
    /**
     * 应用属性修饰符
     */
    private static void applyAttributeModifiers(Player player) {
        addFullSetAttributes(player);
    }
    
    /**
     * 应用药水效果
     */
    private static void applyPotionEffects(Player player) {
        applyPotionEffect(player, MobEffects.NIGHT_VISION, 400, 0, 200);
        applyPotionEffect(player, MobEffects.FIRE_RESISTANCE, 280, 0, 140);
        applyPotionEffect(player, MobEffects.DIG_SPEED, 140, 1, 140);
    }
    
    /**
     * 应用单个药水效果
     */
    private static void applyPotionEffect(Player player, Holder<MobEffect> effect, int duration, int amplifier, int minDuration) {
        if (!player.hasEffect(effect) || Objects.requireNonNull(player.getEffect(effect)).getDuration() <= minDuration) {
            player.addEffect(new MobEffectInstance(effect, duration, amplifier, false, false));
        }
    }
    
    /**
     * 应用饱食度回复效果
     */
    private static void applyHungerRegeneration(Player player) {
        if (player.tickCount % 100 == 0 && player.getFoodData().getFoodLevel() < 20) {
            player.getFoodData().eat(1, 0.1F);
        }
    }



    // 添加全套护甲的属性修饰符
    private static void addFullSetAttributes(Player player) {
        // 移动速度
        if (!Objects.requireNonNull(player.getAttribute(Attributes.MOVEMENT_SPEED)).hasModifier(ICHOR_ARMOR_SPEED)) {
            Objects.requireNonNull(player.getAttribute(Attributes.MOVEMENT_SPEED)).addPermanentModifier(
                    new AttributeModifier(ICHOR_ARMOR_SPEED,
                            0.03, AttributeModifier.Operation.ADD_VALUE)
            );
        }

        // 最大生命值
        if (!Objects.requireNonNull(player.getAttribute(Attributes.MAX_HEALTH)).hasModifier(ICHOR_ARMOR_HEALTH)) {
            Objects.requireNonNull(player.getAttribute(Attributes.MAX_HEALTH)).addPermanentModifier(
                    new AttributeModifier(ICHOR_ARMOR_HEALTH, 30.0, AttributeModifier.Operation.ADD_VALUE)
            );
            // 立即恢复满生命值
            player.setHealth(player.getMaxHealth());
        }

        // 攻击力
        if (!Objects.requireNonNull(player.getAttribute(Attributes.ATTACK_DAMAGE)).hasModifier(ICHOR_ARMOR_ATTACK_DAMAGE)) {
            Objects.requireNonNull(player.getAttribute(Attributes.ATTACK_DAMAGE)).addPermanentModifier(
                    new AttributeModifier(ICHOR_ARMOR_ATTACK_DAMAGE, 7.0, AttributeModifier.Operation.ADD_VALUE)
            );
        }

        // 击退抗性
        if (!Objects.requireNonNull(player.getAttribute(Attributes.KNOCKBACK_RESISTANCE)).hasModifier(ICHOR_ARMOR_KNOCKBACK)) {
            Objects.requireNonNull(player.getAttribute(Attributes.KNOCKBACK_RESISTANCE)).addPermanentModifier(
                    new AttributeModifier(ICHOR_ARMOR_KNOCKBACK, 0.5, AttributeModifier.Operation.ADD_VALUE)
            );
        }

    }

    // 移除全套护甲的属性修饰符
    private static void removeFullSetAttributes(Player player) {
        if (Objects.requireNonNull(player.getAttribute(Attributes.MOVEMENT_SPEED)).hasModifier(ICHOR_ARMOR_SPEED)) {
            Objects.requireNonNull(player.getAttribute(Attributes.MOVEMENT_SPEED)).removeModifier(ICHOR_ARMOR_SPEED);
        }

        if (Objects.requireNonNull(player.getAttribute(Attributes.MAX_HEALTH)).hasModifier(ICHOR_ARMOR_HEALTH)) {
            Objects.requireNonNull(player.getAttribute(Attributes.MAX_HEALTH)).removeModifier(ICHOR_ARMOR_HEALTH);
        }

        if (Objects.requireNonNull(player.getAttribute(Attributes.ATTACK_DAMAGE)).hasModifier(ICHOR_ARMOR_ATTACK_DAMAGE)) {
            Objects.requireNonNull(player.getAttribute(Attributes.ATTACK_DAMAGE)).removeModifier(ICHOR_ARMOR_ATTACK_DAMAGE);
        }

        if (Objects.requireNonNull(player.getAttribute(Attributes.KNOCKBACK_RESISTANCE)).hasModifier(ICHOR_ARMOR_KNOCKBACK)) {
            Objects.requireNonNull(player.getAttribute(Attributes.KNOCKBACK_RESISTANCE)).removeModifier(ICHOR_ARMOR_KNOCKBACK);
        }

    }

    // 检查玩家是否穿着全套Ichor护甲
    private static boolean hasFullIchorArmor(LivingEntity entity) {
        if (entity == null) return false;

        ItemStack head = entity.getItemBySlot(EquipmentSlot.HEAD);
        ItemStack chest = entity.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack legs = entity.getItemBySlot(EquipmentSlot.LEGS);
        ItemStack feet = entity.getItemBySlot(EquipmentSlot.FEET);

        return head.getItem() == ModItems.ICHOR_HELMET.get() &&
                chest.getItem() == ModItems.ICHOR_CHESTPLATE.get() &&
                legs.getItem() == ModItems.ICHOR_LEGGINGS.get() &&
                feet.getItem() == ModItems.ICHOR_BOOTS.get();
    }
}