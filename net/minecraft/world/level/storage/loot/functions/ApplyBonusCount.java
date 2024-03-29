package net.minecraft.world.level.storage.loot.functions;

import com.google.gson.JsonParseException;
import com.google.gson.JsonElement;
import net.minecraft.core.Registry;
import net.minecraft.util.GsonHelper;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonObject;
import java.util.Random;
import com.google.common.collect.Maps;
import java.util.function.Function;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.item.ItemStack;
import com.google.common.collect.ImmutableSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import java.util.Set;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.resources.ResourceLocation;
import java.util.Map;

public class ApplyBonusCount extends LootItemConditionalFunction {
    private static final Map<ResourceLocation, FormulaDeserializer> FORMULAS;
    private final Enchantment enchantment;
    private final Formula formula;
    
    private ApplyBonusCount(final LootItemCondition[] arr, final Enchantment bfs, final Formula b) {
        super(arr);
        this.enchantment = bfs;
        this.formula = b;
    }
    
    public Set<LootContextParam<?>> getReferencedContextParams() {
        return (Set<LootContextParam<?>>)ImmutableSet.of(LootContextParams.TOOL);
    }
    
    public ItemStack run(final ItemStack bcj, final LootContext coy) {
        final ItemStack bcj2 = coy.<ItemStack>getParamOrNull(LootContextParams.TOOL);
        if (bcj2 != null) {
            final int integer5 = EnchantmentHelper.getItemEnchantmentLevel(this.enchantment, bcj2);
            final int integer6 = this.formula.calculateNewCount(coy.getRandom(), bcj.getCount(), integer5);
            bcj.setCount(integer6);
        }
        return bcj;
    }
    
    public static Builder<?> addBonusBinomialDistributionCount(final Enchantment bfs, final float float2, final int integer) {
        return LootItemConditionalFunction.simpleBuilder((Function<LootItemCondition[], LootItemFunction>)(arr -> new ApplyBonusCount(arr, bfs, new BinomialWithBonusCount(integer, float2))));
    }
    
    public static Builder<?> addOreBonusCount(final Enchantment bfs) {
        return LootItemConditionalFunction.simpleBuilder((Function<LootItemCondition[], LootItemFunction>)(arr -> new ApplyBonusCount(arr, bfs, new OreDrops())));
    }
    
    public static Builder<?> addUniformBonusCount(final Enchantment bfs) {
        return LootItemConditionalFunction.simpleBuilder((Function<LootItemCondition[], LootItemFunction>)(arr -> new ApplyBonusCount(arr, bfs, new UniformBonusCount(1))));
    }
    
    public static Builder<?> addUniformBonusCount(final Enchantment bfs, final int integer) {
        return LootItemConditionalFunction.simpleBuilder((Function<LootItemCondition[], LootItemFunction>)(arr -> new ApplyBonusCount(arr, bfs, new UniformBonusCount(integer))));
    }
    
    static {
        (FORMULAS = (Map)Maps.newHashMap()).put(BinomialWithBonusCount.TYPE, BinomialWithBonusCount::deserialize);
        ApplyBonusCount.FORMULAS.put(OreDrops.TYPE, OreDrops::deserialize);
        ApplyBonusCount.FORMULAS.put(UniformBonusCount.TYPE, UniformBonusCount::deserialize);
    }
    
    static final class BinomialWithBonusCount implements Formula {
        public static final ResourceLocation TYPE;
        private final int extraRounds;
        private final float probability;
        
        public BinomialWithBonusCount(final int integer, final float float2) {
            this.extraRounds = integer;
            this.probability = float2;
        }
        
        public int calculateNewCount(final Random random, int integer2, final int integer3) {
            for (int integer4 = 0; integer4 < integer3 + this.extraRounds; ++integer4) {
                if (random.nextFloat() < this.probability) {
                    ++integer2;
                }
            }
            return integer2;
        }
        
        public void serializeParams(final JsonObject jsonObject, final JsonSerializationContext jsonSerializationContext) {
            jsonObject.addProperty("extra", (Number)this.extraRounds);
            jsonObject.addProperty("probability", (Number)this.probability);
        }
        
        public static Formula deserialize(final JsonObject jsonObject, final JsonDeserializationContext jsonDeserializationContext) {
            final int integer3 = GsonHelper.getAsInt(jsonObject, "extra");
            final float float4 = GsonHelper.getAsFloat(jsonObject, "probability");
            return new BinomialWithBonusCount(integer3, float4);
        }
        
        public ResourceLocation getType() {
            return BinomialWithBonusCount.TYPE;
        }
        
        static {
            TYPE = new ResourceLocation("binomial_with_bonus_count");
        }
    }
    
    static final class UniformBonusCount implements Formula {
        public static final ResourceLocation TYPE;
        private final int bonusMultiplier;
        
        public UniformBonusCount(final int integer) {
            this.bonusMultiplier = integer;
        }
        
        public int calculateNewCount(final Random random, final int integer2, final int integer3) {
            return integer2 + random.nextInt(this.bonusMultiplier * integer3 + 1);
        }
        
        public void serializeParams(final JsonObject jsonObject, final JsonSerializationContext jsonSerializationContext) {
            jsonObject.addProperty("bonusMultiplier", (Number)this.bonusMultiplier);
        }
        
        public static Formula deserialize(final JsonObject jsonObject, final JsonDeserializationContext jsonDeserializationContext) {
            final int integer3 = GsonHelper.getAsInt(jsonObject, "bonusMultiplier");
            return new UniformBonusCount(integer3);
        }
        
        public ResourceLocation getType() {
            return UniformBonusCount.TYPE;
        }
        
        static {
            TYPE = new ResourceLocation("uniform_bonus_count");
        }
    }
    
    static final class OreDrops implements Formula {
        public static final ResourceLocation TYPE;
        
        private OreDrops() {
        }
        
        public int calculateNewCount(final Random random, final int integer2, final int integer3) {
            if (integer3 > 0) {
                int integer4 = random.nextInt(integer3 + 2) - 1;
                if (integer4 < 0) {
                    integer4 = 0;
                }
                return integer2 * (integer4 + 1);
            }
            return integer2;
        }
        
        public void serializeParams(final JsonObject jsonObject, final JsonSerializationContext jsonSerializationContext) {
        }
        
        public static Formula deserialize(final JsonObject jsonObject, final JsonDeserializationContext jsonDeserializationContext) {
            return new OreDrops();
        }
        
        public ResourceLocation getType() {
            return OreDrops.TYPE;
        }
        
        static {
            TYPE = new ResourceLocation("ore_drops");
        }
    }
    
    public static class Serializer extends LootItemConditionalFunction.Serializer<ApplyBonusCount> {
        public Serializer() {
            super(new ResourceLocation("apply_bonus"), ApplyBonusCount.class);
        }
        
        @Override
        public void serialize(final JsonObject jsonObject, final ApplyBonusCount cpw, final JsonSerializationContext jsonSerializationContext) {
            super.serialize(jsonObject, cpw, jsonSerializationContext);
            jsonObject.addProperty("enchantment", Registry.ENCHANTMENT.getKey(cpw.enchantment).toString());
            jsonObject.addProperty("formula", cpw.formula.getType().toString());
            final JsonObject jsonObject2 = new JsonObject();
            cpw.formula.serializeParams(jsonObject2, jsonSerializationContext);
            if (jsonObject2.size() > 0) {
                jsonObject.add("parameters", (JsonElement)jsonObject2);
            }
        }
        
        @Override
        public ApplyBonusCount deserialize(final JsonObject jsonObject, final JsonDeserializationContext jsonDeserializationContext, final LootItemCondition[] arr) {
            final ResourceLocation qv5 = new ResourceLocation(GsonHelper.getAsString(jsonObject, "enchantment"));
            final Enchantment bfs6 = (Enchantment)Registry.ENCHANTMENT.getOptional(qv5).orElseThrow(() -> new JsonParseException(new StringBuilder().append("Invalid enchantment id: ").append(qv5).toString()));
            final ResourceLocation qv6 = new ResourceLocation(GsonHelper.getAsString(jsonObject, "formula"));
            final FormulaDeserializer c8 = (FormulaDeserializer)ApplyBonusCount.FORMULAS.get(qv6);
            if (c8 == null) {
                throw new JsonParseException(new StringBuilder().append("Invalid formula id: ").append(qv6).toString());
            }
            Formula b9;
            if (jsonObject.has("parameters")) {
                b9 = c8.deserialize(GsonHelper.getAsJsonObject(jsonObject, "parameters"), jsonDeserializationContext);
            }
            else {
                b9 = c8.deserialize(new JsonObject(), jsonDeserializationContext);
            }
            return new ApplyBonusCount(arr, bfs6, b9, null);
        }
    }
    
    interface Formula {
        int calculateNewCount(final Random random, final int integer2, final int integer3);
        
        void serializeParams(final JsonObject jsonObject, final JsonSerializationContext jsonSerializationContext);
        
        ResourceLocation getType();
    }
    
    interface FormulaDeserializer {
        Formula deserialize(final JsonObject jsonObject, final JsonDeserializationContext jsonDeserializationContext);
    }
}
