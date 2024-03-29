package net.minecraft.world.level.pathfinder;

public enum BlockPathTypes {
    BLOCKED(-1.0f), 
    OPEN(0.0f), 
    WALKABLE(0.0f), 
    TRAPDOOR(0.0f), 
    FENCE(-1.0f), 
    LAVA(-1.0f), 
    WATER(8.0f), 
    WATER_BORDER(8.0f), 
    RAIL(0.0f), 
    DANGER_FIRE(8.0f), 
    DAMAGE_FIRE(16.0f), 
    DANGER_CACTUS(8.0f), 
    DAMAGE_CACTUS(-1.0f), 
    DANGER_OTHER(8.0f), 
    DAMAGE_OTHER(-1.0f), 
    DOOR_OPEN(0.0f), 
    DOOR_WOOD_CLOSED(-1.0f), 
    DOOR_IRON_CLOSED(-1.0f), 
    BREACH(4.0f), 
    LEAVES(-1.0f);
    
    private final float malus;
    
    private BlockPathTypes(final float float3) {
        this.malus = float3;
    }
    
    public float getMalus() {
        return this.malus;
    }
}
