package Content;

import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.type.LiquidStack;
import mindustry.content.Blocks;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.draw.*;
import type.Block.BoostConsumeGenerator;
import type.Block.DefenseCore;
import type.Block.OverItemTurret;

public class Block {
    public static mindustry.world.Block
            Electric_Silicon_Furnace,//电硅炉
            Copper_Lead_Alloy_Mixer,//铜铅合金混合机
            Tungsten_Steel_Refining_Furnace,//钨钢精炼炉
            High_Temperature_Press_Machine, //高温压片机
            Combustion_Heat_Generator,//燃烧发热机
            Heat_energy_storage,//热能电池
            Heat_Extractor,//热力提取机
            Electric_Heating_Machine,//电制热机
            Reaction_Power_Plant,//反应发电厂
            Copper_lead_alloy_Drill_bit,//铜铅合金钻头
            iron_Drill_bit,//铁钻头
            Steel_drilling_rig,//钢钻机
            Beginner_Core,//初级核心
            Hold_Core,//坚守核心
            Suppress,//压制
            Copper_Lead_Alloy_Conveyor,//铜铅合金传送带
            Thermal_Transmission_Line,//热力线
            IronBlock;//铁

    public static void Create() {
        Electric_Silicon_Furnace = new GenericCrafter("电硅炉") {{

        }};
        Reaction_Power_Plant = new BoostConsumeGenerator("反应发电厂") {{
            powerProduction = 12800f / 60f;
            size = 5;
            requirements(Category.power, ItemStack.with(Items.thorium, 320, Items.silicon, 30, Items.tungsten, 450, Items.carbide, 160, Items.surgeAlloy, 120, Items.oxide, 80));
            health = 8920;

            liquidCapacity = 640f;

            ambientSound = Sounds.explosionTitan;
            ambientSoundVolume = 0.35f;

            explodeEffect = Fx.impactReactorExplosion;
            explosionDamage = 5740;
            explodeSound = Sounds.explosionReactor2;
            explosionMinWarmup = 0.8f;

            BoostIntensity = 2.2f;

            liqu = Liquids.water;
            boostliqu = Liquids.cyanogen;
            liquCons = 80f / 60f;

            consumeLiquids(LiquidStack.with(Liquids.cyanogen, 12f / 60f)).boost();
            consumeLiquids(LiquidStack.with(Liquids.water, 80f / 60f)).boost().update(false);

            hasLiquids = true;

            consumeLiquids(LiquidStack.with(Liquids.ozone, 16f / 60f, Liquids.arkycite, 280f / 60f, Liquids.hydrogen, 38f / 60f));

            outputLiquid = new LiquidStack(Liquids.water, 30f / 60f);
            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawLiquidRegion(Liquids.water), new DrawDefault());
        }};

        Hold_Core = new DefenseCore("坚守核心") {{
            requirements(Category.effect, ItemStack.with(Items.thorium, 320));
            size = 5;

            addTurret(Blocks.salvo);
        }};

        Suppress = new OverItemTurret("压制") {{

            requirements(Category.turret, ItemStack.with(Items.thorium, 0));

            Overheating = 0.08f;//1秒可以过热的程度
            coolingRate = 0.16f;//1秒可以冷却的程度

            size = 2;
            liquidCapacity = 40f;

            reload = 2.5f;

            RapidCooling = Liquids.cryofluid;

            consumeLiquids(LiquidStack.with(Liquids.cryofluid, 20f / 60f)).boost().update(false);

            ammo(Items.copper, new BasicBulletType(24f, 45) {{
                width = 1.27f;
                height = 10.8f;
                lifetime = 60f;
                ammoMultiplier = 5;
                armorMultiplier = 0.8f;

                hitEffect = despawnEffect = Fx.hitBulletColor;
                hitColor = backColor = trailColor = Pal.copperAmmoBack;
                frontColor = Pal.copperAmmoFront;
            }},
            Items.silicon, new BasicBulletType(24f, 40, "bullet") {{
                width = 1.27f;
                height = 10.8f;
                lifetime = 60f;
                ammoMultiplier = 4;
                armorMultiplier = 0.78f;
                homingPower = 0.2f;
                reloadMultiplier = 1.5f;

                trailLength = 5;
                trailWidth = 1.5f;
                hitEffect = despawnEffect = Fx.hitBulletColor;
                hitColor = backColor = trailColor = Pal.siliconAmmoBack;
                frontColor = Pal.siliconAmmoFront;
            }});
        }};
    }
}
