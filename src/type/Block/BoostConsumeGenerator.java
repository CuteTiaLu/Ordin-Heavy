package type.Block;

import arc.Events;
import arc.math.Mathf;
import arc.util.Interval;
import arc.util.Nullable;
import arc.util.Time;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.graphics.Pal;
import mindustry.type.Liquid;
import mindustry.ui.Bar;
import mindustry.world.Block;
import mindustry.world.blocks.heat.HeatBlock;
import mindustry.world.blocks.power.ConsumeGenerator;
import mindustry.world.modules.ItemModule;
import mindustry.world.modules.LiquidModule;
import mindustry.world.modules.PowerModule;

public class BoostConsumeGenerator extends ConsumeGenerator {
    public float BoostIntensity;
    public Liquid Coolingliquid;
    public Liquid boostliquid;
    public float
    CoolingCons,
    coolingRate,
    Overheating;

    boolean boost, Cool, CoolisOut;

    public BoostConsumeGenerator(String name) {
        super(name);
        BoostIntensity = 1;
        CoolingCons = 0;
        coolingRate = 0;
        Overheating = 0;
        addBar("heat", (BoostConsumeGeneratorBuild entity) -> new Bar("bar.heat", Pal.lightOrange, entity::heatFrac));
    }

    @Override
    public void setStats(){
        super.setStats();
    }

    @Override
    public void init() {
        super.init();
        boost = boostliquid != null;
        Cool = Coolingliquid != null;
        CoolisOut = outputLiquid != null && outputLiquid.liquid != null && outputLiquid.liquid == Coolingliquid;
    }

    public class BoostConsumeGeneratorBuild extends ConsumeGeneratorBuild implements HeatBlock {

        @Override
        public void placed() {
            super.placed();
            liquids = new LiquidModule() {
                @Override
                public void add(Liquid liquid, float amount) {
                    if (boost && liquids.get(boostliquid) > 0 && warmup() > 0f && CoolisOut) {
                        if (Coolingliquid != liquid) {
                            super.add(liquid, amount);
                        }
                    } else {
                        super.add(liquid, amount);
                    }
                }
            };
        }

        public float heat = 0f;

        @Override
        public void updateTile() {
            super.updateTile();
            if (boost && liquids.get(boostliquid) > 0 && warmup() > 0f) {
                if (Cool) {
                    if (liquids.get(Coolingliquid) > CoolingCons) {
                        heat = Math.max(heat - (coolingRate * Time.delta), 0);
                        liquids.remove(Coolingliquid, CoolingCons);
                    } else {
                        heat = Math.min(heat + (Overheating * Time.delta), 1.001f);
                    }
                } else heat = Math.min(heat + (Overheating * Time.delta), 1.001f);
            }
            if (heat > 1f) {
                Events.fire(EventType.Trigger.thoriumReactorOverheat);
                kill();
            }
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
            return super.getPowerProduction() + ((liquids.get(boostliquid) > 0f) ? (BoostIntensity - 1f) * super.getPowerProduction() : 0);
        }
    }
}
