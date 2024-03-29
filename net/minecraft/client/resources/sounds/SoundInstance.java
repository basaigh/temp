package net.minecraft.client.resources.sounds;

import net.minecraft.sounds.SoundSource;
import javax.annotation.Nullable;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.resources.ResourceLocation;

public interface SoundInstance {
    ResourceLocation getLocation();
    
    @Nullable
    WeighedSoundEvents resolve(final SoundManager eap);
    
    Sound getSound();
    
    SoundSource getSource();
    
    boolean isLooping();
    
    boolean isRelative();
    
    int getDelay();
    
    float getVolume();
    
    float getPitch();
    
    float getX();
    
    float getY();
    
    float getZ();
    
    Attenuation getAttenuation();
    
    default boolean canStartSilent() {
        return false;
    }
    
    public enum Attenuation {
        NONE, 
        LINEAR;
    }
}
