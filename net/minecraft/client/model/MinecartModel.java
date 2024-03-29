package net.minecraft.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;

public class MinecartModel<T extends Entity> extends EntityModel<T> {
    private final ModelPart[] cubes;
    
    public MinecartModel() {
        (this.cubes = new ModelPart[7])[0] = new ModelPart(this, 0, 10);
        this.cubes[1] = new ModelPart(this, 0, 0);
        this.cubes[2] = new ModelPart(this, 0, 0);
        this.cubes[3] = new ModelPart(this, 0, 0);
        this.cubes[4] = new ModelPart(this, 0, 0);
        this.cubes[5] = new ModelPart(this, 44, 10);
        final int integer2 = 20;
        final int integer3 = 8;
        final int integer4 = 16;
        final int integer5 = 4;
        this.cubes[0].addBox(-10.0f, -8.0f, -1.0f, 20, 16, 2, 0.0f);
        this.cubes[0].setPos(0.0f, 4.0f, 0.0f);
        this.cubes[5].addBox(-9.0f, -7.0f, -1.0f, 18, 14, 1, 0.0f);
        this.cubes[5].setPos(0.0f, 4.0f, 0.0f);
        this.cubes[1].addBox(-8.0f, -9.0f, -1.0f, 16, 8, 2, 0.0f);
        this.cubes[1].setPos(-9.0f, 4.0f, 0.0f);
        this.cubes[2].addBox(-8.0f, -9.0f, -1.0f, 16, 8, 2, 0.0f);
        this.cubes[2].setPos(9.0f, 4.0f, 0.0f);
        this.cubes[3].addBox(-8.0f, -9.0f, -1.0f, 16, 8, 2, 0.0f);
        this.cubes[3].setPos(0.0f, 4.0f, -7.0f);
        this.cubes[4].addBox(-8.0f, -9.0f, -1.0f, 16, 8, 2, 0.0f);
        this.cubes[4].setPos(0.0f, 4.0f, 7.0f);
        this.cubes[0].xRot = 1.5707964f;
        this.cubes[1].yRot = 4.712389f;
        this.cubes[2].yRot = 1.5707964f;
        this.cubes[3].yRot = 3.1415927f;
        this.cubes[5].xRot = -1.5707964f;
    }
    
    @Override
    public void render(final T aio, final float float2, final float float3, final float float4, final float float5, final float float6, final float float7) {
        this.cubes[5].y = 4.0f - float4;
        for (int integer9 = 0; integer9 < 6; ++integer9) {
            this.cubes[integer9].render(float7);
        }
    }
}
