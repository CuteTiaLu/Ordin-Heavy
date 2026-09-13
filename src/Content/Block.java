package Content;

import mindustry.world.blocks.power.ConsumeGenerator;

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
        Reaction_Power_Plant = new ConsumeGenerator("反应发电厂") {{

        }};
    }
}
