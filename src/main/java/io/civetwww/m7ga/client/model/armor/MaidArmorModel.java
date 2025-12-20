package io.civetwww.m7ga.client.model.armor;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class MaidArmorModel {
    // 创建正常护甲网格定义方法
    public static MeshDefinition createNormalMesh() {
        // 创建新的网格定义对象
        MeshDefinition meshdefinition = new MeshDefinition();
        // 获取根部件定义
        PartDefinition partdefinition = meshdefinition.getRoot();
        // 添加帽子部件（空部件，位置为原点）
        partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
        // 添加头部部件（空部件，位置为原点）
        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        // 添加头饰部件：纹理偏移(1,61)，立方体(-5,-9,-1,10,4,1)，变形0.0F，位置原点
        PartDefinition headgear = head.addOrReplaceChild("headgear", CubeListBuilder.create().texOffs(1, 61).addBox(-5.0F, -9.0F, -1.0F, 10.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        // 添加右耳部件：纹理偏移(12,55)，立方体(-2,-2,-1,3,3,2)，变形0.0F，位置(-2,-9,-2)，旋转(0,0,-0.8727)
        PartDefinition right_ear_r1 = headgear.addOrReplaceChild("right_ear_r1", CubeListBuilder.create().texOffs(12, 55).addBox(-2.0F, -2.0F, -1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -9.0F, -2.0F, 0.0F, 0.0F, -0.8727F));
        // 添加左耳部件：纹理偏移(1,55)，立方体(-1,-2,-1,3,3,2)，变形0.0F，位置(2,-9,-2)，旋转(0,0,0.8727)
        PartDefinition left_ear_r1 = headgear.addOrReplaceChild("left_ear_r1", CubeListBuilder.create().texOffs(1, 55).addBox(-1.0F, -2.0F, -1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -9.0F, -2.0F, 0.0F, 0.0F, 0.8727F));
        // 添加身体部件（空部件，位置为原点）
        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        // 添加上衣部件：纹理偏移(16,16)，立方体(-4,0,-2,8,12,4)，变形0.5F，位置原点
        PartDefinition suit = body.addOrReplaceChild("suit", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        // 添加胸部装饰部件：纹理偏移(1,67)，立方体(-1,-3.5,-2,6,3,3)，变形0.5F，位置(-2,5,-1)，旋转(0.7592,0,0)
        PartDefinition chest_r1 = suit.addOrReplaceChild("chest_r1", CubeListBuilder.create().texOffs(1, 67).addBox(-1.0F, -3.5F, -2.0F, 6.0F, 3.0F, 3.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(-2.0F, 5.0F, -1.0F, 0.7592F, 0.0F, 0.0F));
        // 添加右臂部件（空部件，位置(-5,2,0)）
        PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(-5.0F, 2.0F, 0.0F));
        // 添加右袖部件：纹理偏移(27,77)，立方体(-3,-2,-2,4,4,4)，变形0.5F，位置原点
        PartDefinition right_sleeve = right_arm.addOrReplaceChild("right_sleeve", CubeListBuilder.create().texOffs(27, 77).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.35F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        // 添加左臂部件（空部件，位置(5,2,0)）
        PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(5.0F, 2.0F, 0.0F));
        // 添加左袖部件：纹理偏移(4,77)，立方体(-1,-2,-2,4,4,4)，变形0.5F，位置原点
        PartDefinition left_sleeve = left_arm.addOrReplaceChild("left_sleeve", CubeListBuilder.create().texOffs(4, 77).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.35F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        // 添加右腿部件（空部件，位置(-1.9,12,0)）
        PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-1.9F, 12.0F, 0.0F));
        // 添加右袜部件：纹理偏移(0,14)，立方体(-2,-2,-2,4,14,4)，变形0.5F，位置原点
        PartDefinition right_sock = right_leg.addOrReplaceChild("right_sock", CubeListBuilder.create().texOffs(0, 14).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 14.0F, 4.0F, new CubeDeformation(0.3F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        // 添加左腿部件（空部件，位置(1.9,12,0)）
        PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(1.9F, 12.0F, 0.0F));
        // 添加左袜部件：纹理偏移(0,14)，立方体(-2,-2,-2,4,14,4)，变形0.5F，位置原点
        PartDefinition left_sock = left_leg.addOrReplaceChild("left_sock", CubeListBuilder.create().texOffs(0, 14).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 14.0F, 4.0F, new CubeDeformation(0.3F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        // 返回完整的网格定义
        return meshdefinition;
    }

    // 创建裙子网格定义方法
    public static MeshDefinition createDressMesh() {
        // 创建新的网格定义对象
        MeshDefinition meshdefinition = new MeshDefinition();
        // 获取根部件定义
        PartDefinition partdefinition = meshdefinition.getRoot();
        // 添加帽子部件（空部件，位置为原点）
        partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
        // 添加头部部件（空部件，位置为原点）
        partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.ZERO);
        // 添加左臂部件（空部件，位置为原点）
        partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.ZERO);
        // 添加右臂部件（空部件，位置为原点）
        partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.ZERO);
        // 添加左腿部件（空部件，位置为原点）
        partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.ZERO);
        // 添加右腿部件（空部件，位置为原点）
        partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.ZERO);
        // 添加身体部件（空部件，位置为原点）
        var body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.ZERO);
        // 添加裙子部件（空部件，位置(0,24,0)）
        PartDefinition dress = body.addOrReplaceChild("dress", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        // 添加前裙部件（空部件，位置(0,-12,-5)，旋转(-0.3054,0,0)）
        PartDefinition front = dress.addOrReplaceChild("front", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -12.0F, -5.0F, -0.3054F, 0.0F, 0.0F));
        // 添加前裙主要部分：纹理偏移(20,48)，立方体(-4,-5,-0.5,8,5,1)，变形0.35F，位置原点，旋转(-0.4363,0,0)
        PartDefinition DressFront_r1 = front.addOrReplaceChild("DressFront_r1", CubeListBuilder.create().texOffs(20, 48).addBox(-4.0F, -5.0F, -0.5F, 8.0F, 5.0F, 1.0F, new CubeDeformation(0.35F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.4363F, 0.0F, 0.0F));
        // 添加前裙附加部分：纹理偏移(1,32)，立方体(-2,-5,-0.7,6,4,1)，变形0.35F，位置(-1,0,0)，旋转(-0.48,0,0)
        PartDefinition DressFrontPlus_r1 = front.addOrReplaceChild("DressFrontPlus_r1", CubeListBuilder.create().texOffs(1, 32).addBox(-2.0F, -5.0F, -0.7F, 6.0F, 4.0F, 1.0F, new CubeDeformation(0.35F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, -0.48F, 0.0F, 0.0F));
        // 添加后裙部件（空部件，位置(0,-12.0298,5.0203)，旋转(0.2618,0,0)）
        PartDefinition backend = dress.addOrReplaceChild("backend", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -12.0298F, 5.0203F, 0.2618F, 0.0F, 0.0F));
        // 添加后裙主要部分：纹理偏移(1,48)，立方体(-4,-3.038,-0.5216,8,5,1)，变形0.35F，位置(0,-1.9702,-1.0203)，旋转(0.5236,0,0)
        PartDefinition DressBack_r1 = backend.addOrReplaceChild("DressBack_r1", CubeListBuilder.create().texOffs(1, 48).addBox(-4.0F, -3.038F, -0.5216F, 8.0F, 5.0F, 1.0F, new CubeDeformation(0.35F)), PartPose.offsetAndRotation(0.0F, -1.9702F, -1.0203F, 0.5236F, 0.0F, 0.0F));
        // 添加后裙附加部分：纹理偏移(16,32)，立方体(-3,-5,0.1,6,4,1)，变形0.35F，位置(0,0.0298,-0.5203)，旋转(0.5236,0,0)
        PartDefinition DressBackPlus_r1 = backend.addOrReplaceChild("DressBackPlus_r1", CubeListBuilder.create().texOffs(16, 32).addBox(-3.0F, -5.0F, 0.1F, 6.0F, 4.0F, 1.0F, new CubeDeformation(0.35F)), PartPose.offsetAndRotation(0.0F, 0.0298F, -0.5203F, 0.5236F, 0.0F, 0.0F));
        // 添加左侧裙摆部件（空部件，位置(2,-1,0)）
        PartDefinition left_cloth = dress.addOrReplaceChild("left_cloth", CubeListBuilder.create(), PartPose.offset(2.0F, -1.0F, 0.0F));
        // 添加左侧裙摆布料：纹理偏移(1,38)，立方体(-0.5088,-4.0341,-2,1,5,4)，变形0.35F，位置(3,-11,0)，旋转(0,0,-0.3491)
        PartDefinition cloth_r1 = left_cloth.addOrReplaceChild("cloth_r1", CubeListBuilder.create().texOffs(1, 38).addBox(-0.5088F, -4.0341F, -2.0F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.35F)), PartPose.offsetAndRotation(3.0F, -11.0F, 0.0F, 0.0F, 0.0F, -0.3491F));
        // 添加右侧裙摆部件（空部件，位置(-2,-1,0)）
        PartDefinition right_cloth = dress.addOrReplaceChild("right_cloth", CubeListBuilder.create(), PartPose.offset(-2.0F, -1.0F, 0.0F));
        // 添加右侧裙摆布料：纹理偏移(1,38)，立方体(-0.4912,-4.0341,-2,1,5,4)，变形0.35F，位置(-3,-11,0)，旋转(0,0,0.3491)
        PartDefinition cloth_r2 = right_cloth.addOrReplaceChild("cloth_r2", CubeListBuilder.create().texOffs(1, 38).addBox(-0.4912F, -4.0341F, -2.0F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.35F)), PartPose.offsetAndRotation(-3.0F, -11.0F, 0.0F, 0.0F, 0.0F, 0.3491F));
        // 返回完整的网格定义
        return meshdefinition;
    }


}