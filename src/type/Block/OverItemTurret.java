package type.Block;

import arc.math.Mathf;
import arc.util.Time;
import mindustry.entities.Mover;
import mindustry.entities.bullet.BulletType;
import mindustry.graphics.Pal;
import mindustry.type.Liquid;
import mindustry.ui.Bar;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.heat.HeatBlock;

public class OverItemTurret extends ItemTurret {

    public float Overheating, coolingRate, heatInaMax;
    public Liquid RapidCooling;

    public OverItemTurret(String name) {
        super(name);
        Overheating = 0.2f;
        coolingRate = 0.6f;
        heatInaMax = 0;
        addBar("heat", (OverItemTurretBuild entity) -> new Bar("bar.heat", Pal.lightOrange, entity::heat));
    }

    public class OverItemTurretBuild extends ItemTurretBuild implements HeatBlock {

        public float heat = 0f;
        public boolean cooling = false;
        public float lastShootTime = 0f;

        @Override
        public void updateTile() {
            lastShootTime += Time.delta;
            if (heat >= 1f) {
                cooling = true;
            } else if (heat < 0.001f) {
                cooling = false;
            }
            if (RapidCooling != null && liquids.get(RapidCooling) > liquidCapacity * 0.4f) {
                cooling = false;
                heat = 0;
            }
            if (!cooling) {
                super.updateTile();
            }
            if (lastShootTime > reload + Time.delta) heat = Math.max(heat - (coolingRate * (Time.delta * (reload / 60)) * ((cooling) ? 0.8f : 1f)), 0f);
        }

        @Override
        public boolean canConsume(){
            return super.canConsume() && !cooling;
        }

        @Override
        protected void shoot(BulletType type) {
            super.shoot(type);
            heat = Math.min(heat + (Overheating * (reload / 60)), 1.0001f);
            lastShootTime = -Time.delta;
        }

        @Override
        protected void bullet(BulletType type, float xOffset, float yOffset, float angleOffset, Mover mover) {
            super.bullet(type, xOffset, yOffset, angleOffset + Mathf.range(heatInaMax * heat), mover);
        }

        @Override
        public float heat() {
            return heat;
        }

        @Override
        public float heatFrac() {
            return heat;
        }
    }
}