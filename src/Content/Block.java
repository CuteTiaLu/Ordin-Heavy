package Content;

import arc.graphics.Color;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.type.LiquidStack;
import mindustry.world.blocks.power.ConsumeGenerator;
import mindustry.content.Blocks;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.draw.*;

import static mindustry.type.ItemStack.with;

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
        Suppress,//压制
        Copper_Lead_Alloy_Conveyor,//铜铅合金传送带
        Thermal_Transmission_Line,//热力线
        IronBlock;//铁

    public static void Create() {
        Electric_Silicon_Furnace = new GenericCrafter("电硅炉") {{

        }};
        Reaction_Power_Plant = new ConsumeGenerator("反应发电厂") {{
            powerProduction = 15780f / 60f;
            size = 5;
            requirements(Category.power, ItemStack.with(Items.thorium, 320, Items.silicon, 30, Items.tungsten, 450, Items.carbide, 160, Items.oxide, 80));
            health = 18500;
            description = "";
            liquidCapacity = 640f;

            ambientSound = Sounds.explosionTitan;
            ambientSoundVolume = 0.35f;

            explodeEffect = Fx.impactReactorExplosion;
            explosionDamage = 6500;
            explodeSound = Sounds.explosionReactor2;
            explosionMinWarmup = 0.8f;

            consumeLiquids(LiquidStack.with(Liquids.ozone, 16f / 60f, Liquids.arkycite, 280f / 60f, Liquids.hydrogen, 38f / 60f));
            outputLiquid = new LiquidStack(Liquids.water, 40f / 60f);
            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawDefault());
        }};
    }
}
