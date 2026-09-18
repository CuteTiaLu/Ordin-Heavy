package type.Block;

import arc.math.Mathf;
import mindustry.entities.Mover;
import mindustry.entities.bullet.BulletType;
import mindustry.graphics.Pal;
import mindustry.type.Liquid;
import mindustry.ui.Bar;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.heat.HeatBlock;

public class OverItemTurret extends ItemTurret {

    public float Overheating, coolingRate, overheatInaccuracy;
    public Liquid RapidCooling;

    public OverItemTurret(String name) {
        super(name);
        Overheating = 0.2f;
        coolingRate = 0.6f;
        overheatInaccuracy = 0;
        addBar("heat", (OverItemTurretBuild entity) -> new Bar("bar.heat", Pal.lightOrange, entity::heat));
    }

    public class OverItemTurretBuild extends ItemTurretBuild implements HeatBlock {

        public float overheat = 0f;
        public boolean cooling = false;
        public float lastShootTime = 0f;

        @Override
        public void updateTile() {
            lastShootTime += delta();
            if (overheat >= 1f) {
                cooling = true;
            } else if (overheat < 0.001f) {
                cooling = false;
            }
            if (RapidCooling != null && liquids.get(RapidCooling) > liquidCapacity * 0.4f) { cooling = false; overheat = 0;}
            if (!cooling) {
                super.updateTile();
            }
            if ((lastShootTime > (reload / 60f) + 1.5f)) overheat = Math.max(overheat - (coolingRate * delta() * ((cooling) ? 0.8f : 1f)), 0f);
        }

        @Override
        protected void shoot(BulletType type) {
            super.shoot(type);
            overheat = Math.min(overheat + (Overheating * delta()), 1.0001f);
            lastShootTime = 0f;
        }

        @Override
        protected void bullet(BulletType type, float xOffset, float yOffset, float angleOffset, Mover mover) {
            super.bullet(type, xOffset, yOffset, angleOffset + Mathf.range(overheatInaccuracy * overheat), mover);
        }

        @Override
        public boolean canConsume() {
            return super.canConsume() && !cooling;
        }

        @Override
        public float heat() {
            return overheat;
        }

        @Override
        public float heatFrac() {
            return overheat;
        }
    }
}