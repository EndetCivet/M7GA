package io.civetwww.m7ga.event.armors;

import io.civetwww.m7ga.common.items.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static io.civetwww.m7ga.M7GA.MODID;

@EventBusSubscriber
public class MaidArmorEvents {

    private static final ResourceLocation MAID_ARMOR_SPEED = ResourceLocation.fromNamespaceAndPath(MODID, "maid_armor_speed");
    private static final ResourceLocation MAID_ARMOR_HEALTH = ResourceLocation.fromNamespaceAndPath(MODID, "maid_armor_health");
    private static final ResourceLocation MAID_ARMOR_KNOCKBACK = ResourceLocation.fromNamespaceAndPath(MODID, "maid_armor_knockback_resistance");
    private static final ResourceLocation MAID_ARMOR_ATTACK_DAMAGE = ResourceLocation.fromNamespaceAndPath(MODID, "maid_armor_attack_damage");


    @SubscribeEvent
    public static void onPlayerTick(EntityTickEvent.Post event) {
        // 只处理玩家实体
        if (!(event.getEntity() instanceof Player player)) return;

        // 检查是否穿着全套女仆护甲
        if (hasFullMaidArmor(player)) {
            // 循环清除debuff
            player.getActiveEffects().removeIf(effect -> !effect.getEffect().value().isBeneficial());

            // 属性修饰符
            addFullSetAttributes(player);

            // 生命恢复2
            if (!player.hasEffect(MobEffects.REGENERATION) || Objects.requireNonNull(player.getEffect(MobEffects.REGENERATION)).getDuration() <= 70) {
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 140, 1, false, false));
            }

            // 夜视
            if (!player.hasEffect(MobEffects.NIGHT_VISION) || Objects.requireNonNull(player.getEffect(MobEffects.NIGHT_VISION)).getDuration() <= 200) {
                player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 400, 0, false, false));
            }

            //幸运
            if (!player.hasEffect(MobEffects.LUCK) || Objects.requireNonNull(player.getEffect(MobEffects.LUCK)).getDuration() <= 70) {
                player.addEffect(new MobEffectInstance(MobEffects.LUCK, 140, 6, false, false));
            }

            //抗火 II
            if (!player.hasEffect(MobEffects.FIRE_RESISTANCE) || Objects.requireNonNull(player.getEffect(MobEffects.FIRE_RESISTANCE)).getDuration() <= 70) {
                player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 140, 1, false, false));
            }

            // 饱食度回复
            if (player.tickCount % 100 == 0) {
                if (player.getFoodData().getFoodLevel() < 20) {
                    player.getFoodData().eat(1, 0.1F);
                }
            }

            // 检查是否空手
            boolean isEmptyHanded = player.getMainHandItem().isEmpty() && player.getOffhandItem().isEmpty();
            if (isEmptyHanded) {
                // 空手时添加攻击力加成
                if (!Objects.requireNonNull(player.getAttribute(Attributes.ATTACK_DAMAGE)).hasModifier(MAID_ARMOR_ATTACK_DAMAGE)) {
                    Objects.requireNonNull(player.getAttribute(Attributes.ATTACK_DAMAGE)).addPermanentModifier(
                            new AttributeModifier(MAID_ARMOR_ATTACK_DAMAGE, 20.0, AttributeModifier.Operation.ADD_VALUE)
                    );
                }
            } else {
                // 非空手时移除攻击力加成
                if (Objects.requireNonNull(player.getAttribute(Attributes.ATTACK_DAMAGE)).hasModifier(MAID_ARMOR_ATTACK_DAMAGE)) {
                    Objects.requireNonNull(player.getAttribute(Attributes.ATTACK_DAMAGE)).removeModifier(MAID_ARMOR_ATTACK_DAMAGE);
                }
            }


        } else {
            // 移除全套护甲的属性修饰符
            removeFullSetAttributes(player);

            // 移除攻击力加成
            if (Objects.requireNonNull(player.getAttribute(Attributes.ATTACK_DAMAGE)).hasModifier(MAID_ARMOR_ATTACK_DAMAGE)) {
                Objects.requireNonNull(player.getAttribute(Attributes.ATTACK_DAMAGE)).removeModifier(MAID_ARMOR_ATTACK_DAMAGE);
            }

        }
    }


    // 添加全套护甲的属性修饰符
    private static void addFullSetAttributes(Player player) {
        // 移动速度
        if (!Objects.requireNonNull(player.getAttribute(Attributes.MOVEMENT_SPEED)).hasModifier(MAID_ARMOR_SPEED)) {
            Objects.requireNonNull(player.getAttribute(Attributes.MOVEMENT_SPEED)).addPermanentModifier(
                    new AttributeModifier(MAID_ARMOR_SPEED,
                            0.03, AttributeModifier.Operation.ADD_VALUE)
            );
        }

        // 最大生命值
        if (!Objects.requireNonNull(player.getAttribute(Attributes.MAX_HEALTH)).hasModifier(MAID_ARMOR_HEALTH)) {
            Objects.requireNonNull(player.getAttribute(Attributes.MAX_HEALTH)).addPermanentModifier(
                    new AttributeModifier(MAID_ARMOR_HEALTH,
                            20.0, AttributeModifier.Operation.ADD_VALUE)
            );
        }

        // 击退抗性
        if (!Objects.requireNonNull(player.getAttribute(Attributes.KNOCKBACK_RESISTANCE)).hasModifier(MAID_ARMOR_KNOCKBACK)) {
            Objects.requireNonNull(player.getAttribute(Attributes.KNOCKBACK_RESISTANCE)).addPermanentModifier(
                    new AttributeModifier(MAID_ARMOR_KNOCKBACK,
                            0.7, AttributeModifier.Operation.ADD_VALUE)
            );
        }
    }

    // 移除全套护甲的属性修饰符
    private static void removeFullSetAttributes(Player player) {
        if (Objects.requireNonNull(player.getAttribute(Attributes.MOVEMENT_SPEED)).hasModifier(MAID_ARMOR_SPEED)) {
            Objects.requireNonNull(player.getAttribute(Attributes.MOVEMENT_SPEED)).removeModifier(MAID_ARMOR_SPEED);
        }

        if (Objects.requireNonNull(player.getAttribute(Attributes.MAX_HEALTH)).hasModifier(MAID_ARMOR_HEALTH)) {
            Objects.requireNonNull(player.getAttribute(Attributes.MAX_HEALTH)).removeModifier(MAID_ARMOR_HEALTH);
        }

        if (Objects.requireNonNull(player.getAttribute(Attributes.KNOCKBACK_RESISTANCE)).hasModifier(MAID_ARMOR_KNOCKBACK)) {
            Objects.requireNonNull(player.getAttribute(Attributes.KNOCKBACK_RESISTANCE)).removeModifier(MAID_ARMOR_KNOCKBACK);
        }
    }

    // 检查玩家是否穿着全套女仆护甲
    private static boolean hasFullMaidArmor(LivingEntity entity) {
        if (entity == null) return false;

        ItemStack head = entity.getItemBySlot(EquipmentSlot.HEAD);
        ItemStack chest = entity.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack legs = entity.getItemBySlot(EquipmentSlot.LEGS);
        ItemStack feet = entity.getItemBySlot(EquipmentSlot.FEET);

        return head.getItem() == ModItems.MAID_HEADGEAR.get() &&
                chest.getItem() == ModItems.MAID_SUIT.get() &&
                legs.getItem() == ModItems.MAID_SKIRT.get() &&
                feet.getItem() == ModItems.MAID_BOOTS.get();
    }
}