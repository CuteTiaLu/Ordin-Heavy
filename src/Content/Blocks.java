package Content;

import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.pattern.ShootAlternate;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.type.LiquidStack;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.draw.*;
import type.Block.BoostConsumeGenerator;
import type.Block.DefenseCore;
import type.Block.OverItemTurret;

import static Content.Items.*;

public class Blocks {
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

            Coolingliquid = Liquids.water;
            boostliquid = Liquids.cyanogen;
            CoolingCons = 80f / 60f;
            Overheating = 0.03f;

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

            addTurret(mindustry.content.Blocks.salvo);
        }};

        Suppress = new OverItemTurret("压制") {{

            requirements(Category.turret, ItemStack.with(Iron, 80, Steel, 60, Items.silicon, 40));

            Overheating = 0.08f;
            coolingRate = 0.016f;

            heatInaMax = 2.5f;

            recoil = 0.3f;
            rotateSpeed = 7f;
            inaccuracy = 1.25f;

            range = 254;

            size = 2;
            liquidCapacity = 40f;

            reload = 6.5f;

            shoot = new ShootAlternate(6f);
            shoot.shots = 1;
            shootY = 8;

            RapidCooling = Liquids.cryofluid;

            coolant = consumeCoolant(0.1f);
            coolantMultiplier = 10f;

            ammo(
            Iron, new BasicBulletType(24f, 23) {{
                width = 2.3f;
                height = 15.2f;
                trailLength = 17;
                trailWidth = 1.2f;
                lifetime = 12f;
                ammoMultiplier = 5;
                armorMultiplier = 0.8f;

                hitEffect = despawnEffect = Fx.hitBulletColor;
                hitColor = backColor = trailColor = Pal.copperAmmoBack;
                frontColor = Pal.copperAmmoFront;
            }},
            Items.silicon, new BasicBulletType(24f, 24, "bullet") {{
                width = 2.3f;
                height = 15.2f;
                trailLength = 17;
                trailWidth = 1.2f;
                lifetime = 60f;
                ammoMultiplier = 4;
                armorMultiplier = 0.78f;
                homingPower = 0.2f;
                reloadMultiplier = 1.1f;

                trailLength = 5;
                trailWidth = 1.5f;
                hitEffect = despawnEffect = Fx.hitBulletColor;
                hitColor = backColor = trailColor = Pal.siliconAmmoBack;
                frontColor = Pal.siliconAmmoFront;
            }},
            Steel, new BasicBulletType(24f, 33) {{
                width = 2.3f;
                height = 15.2f;
                trailLength = 17;
                trailWidth = 1.2f;
                lifetime = 60f;
                ammoMultiplier = 5;
                armorMultiplier = 0.8f;

                hitEffect = despawnEffect = Fx.hitBulletColor;
                hitColor = backColor = trailColor = Pal.copperAmmoBack;
                frontColor = Pal.copperAmmoFront;
            }});
        }};
    }
}
