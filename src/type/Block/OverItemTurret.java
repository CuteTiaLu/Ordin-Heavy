package type.Block;

import arc.math.Mathf;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.blocks.defense.turrets.ItemTurret;

public class OverItemTurret extends ItemTurret {

    public float Overheating, CoolingRate;

    public OverItemTurret(String name) {
        super(name);
    }

    @Override
    public void setStats() {
        super.setStats();
        stats.add("heat", (OverItemTurretBuild entity) -> new Bar("bar.heat", Pal.lightOrange, entity.heat));
    }

    public class OverItemTurretBuild extends ItemTurretBuild {

        public float heat = 0f;
        public boolean Cooling = false;

        @Override
        public void updateTile() {
            super.updateTile();
            if (heat > 1f) {
                Cooling = true;
            }
            if (Cooling) {
                heat = Mathf.clamp(heat - CoolingRate, 0f, 1.0001f);
            } else {
                heat = Mathf.clamp(heat + Overheating, 0f, 1.0001f);
            }
        }

        @Override
        public boolean canConsume(){
            return super.canConsume() && !Cooling;
        }

    }
}
