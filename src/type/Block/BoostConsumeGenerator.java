package type.Block;

import arc.Events;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.game.EventType;
import mindustry.graphics.Pal;
import mindustry.type.Liquid;
import mindustry.ui.Bar;
import mindustry.world.blocks.heat.HeatBlock;
import mindustry.world.blocks.power.ConsumeGenerator;

public class BoostConsumeGenerator extends ConsumeGenerator {
    public float BoostIntensity;
    public Liquid liqu;
    public Liquid boostliqu;
    public float liquCons;

    public BoostConsumeGenerator(String name) {
        super(name);
        BoostIntensity = 1;
        liquCons = 0;
        addBar("heat", (BoostConsumeGeneratorBuild entity) -> new Bar("bar.heat", Pal.lightOrange, entity::heatFrac));
    }

    @Override
    public void setStats(){
        super.setStats();
    }

    public class BoostConsumeGeneratorBuild extends ConsumeGeneratorBuild implements HeatBlock {

        public float heat = 0f;

        @Override
        public void updateTile() {
            boolean valid = efficiency > 0;

            warmup = Mathf.lerpDelta(warmup, valid ? 1f : 0f, warmupSpeed);

            productionEfficiency = efficiency * efficiencyMultiplier;
            totalTime += warmup * Time.delta;

            //randomly produce the effect
            if(valid && Mathf.chanceDelta(effectChance)){
                generateEffect.at(x + Mathf.range(generateEffectRange), y + Mathf.range(generateEffectRange));
            }

            //make sure the multiplier doesn't change when there is nothing to consume while it's still running
            if(filterItem != null && valid && itemDurationMultipliers.size > 0 && filterItem.getConsumed(this) != null){
                itemDurationMultiplier = itemDurationMultipliers.get(filterItem.getConsumed(this), 1);
            }

            //take in items periodically
            if(hasItems && valid && generateTime <= 0f){
                consume();
                consumeEffect.at(x + Mathf.range(generateEffectRange), y + Mathf.range(generateEffectRange));
                generateTime = 1f;
            }

            //generation time always goes down, but only at the end so consumeTriggerValid doesn't assume fake items
            generateTime -= delta() / (itemDuration * itemDurationMultiplier);

            if (liquids.get(boostliqu) > 0f && warmup() > 0f) {
                if (liquids.get(liqu) > 0f) {
                    heat = Math.max(heat - delta() * 1.8f, 0f);
                    liquids.remove(liqu, liquCons);
                } else {
                    heat = Math.max(heat + ((delta() / 60f) * 0.1f), 0f);
                    if (heat >= 1f) kill();
                }
            } else if ( warmup() > 0f) {
                heat = Math.max(heat - delta() * 1.8f, 0f);
                float added = Math.min(productionEfficiency * delta() * outputLiquid.amount, liquidCapacity - liquids.get(outputLiquid.liquid));
                liquids.add(outputLiquid.liquid, added);
                dumpLiquid(outputLiquid.liquid);

                if(explodeOnFull && liquids.get(outputLiquid.liquid) >= liquidCapacity - 0.01f){
                    kill();
                    Events.fire(new EventType.GeneratorPressureExplodeEvent(this));
                }
            } else heat = Math.max(heat - delta() * 1.8f, 0f);
        }

        @Override
        public float heat(){
            return heat;
        }

        @Override
        public float heatFrac() {
            return heat;
        }

        @Override
        public float getPowerProduction(){
            return super.getPowerProduction() + ((liquids.get(boostliqu) > 0f) ? (BoostIntensity - 1f) * super.getPowerProduction() : 0);
        }
    }
}
