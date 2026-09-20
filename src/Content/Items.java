package Content;

import arc.graphics.Color;
import mindustry.type.Item;


public class Items {
    public static Item
        Copper_Lead_alloy,//铜铅合金
        Iron,//铁
        Steel,//钢
        Tungsten_Steel,//钨钢
        Tungsten_Steel_Plate;//钨钢板

    public static void Create() {
        Copper_Lead_alloy = new Item("铜铅合金", Color.valueOf("#6c8587")) {{//铜铅_合金
            cost = 1f;//一般不用管
        }};
        Iron = new Item("铁", Color.valueOf("#808080")) {{
            hardness = 3;
            cost = 1f;
            description = "一种普通的金属";
            details = "可以练钢";
            alwaysUnlocked = false;
        }};
        Steel = new Item("钢", Color.valueOf("#e0e0e0")) {{
            hardness = 3;
            cost = 1f;
            description = "加工过的铁";
            details = "能崩掉你的牙";
            alwaysUnlocked = false;
        }};
        Tungsten_Steel = new Item("钨钢", Color.valueOf("#6c8587")) {{//钨钢
            cost = 1f;
        }};
        Tungsten_Steel_Plate = new Item("钨钢板", Color.valueOf("#6c8587")) {{//钨钢_板
            cost = 1f;
        }};
    }
}
