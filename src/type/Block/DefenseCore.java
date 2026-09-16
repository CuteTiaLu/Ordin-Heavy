package type.Block;

import arc.util.io.Reads;
import mindustry.type.Item;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.payloads.BuildPayload;
import mindustry.world.blocks.storage.CoreBlock;

import java.util.Arrays;

public class DefenseCore extends CoreBlock {

    protected Block[] turret = new Block[0];

    public DefenseCore(String name) {
        super(name);
    }

    public void addTurret(Block tur) {
        turret = Arrays.copyOf(turret, turret.length + 1);
        turret[turret.length - 1] = tur;
    }

    public class DefenseCoreBuild extends CoreBuild {

        protected Build[] turretBuild = new Build[0];

        class Build {

            Build(BuildPayload turret, Item ammo) {
                this.turret = turret;
                this.ammo = ammo;
            }

            BuildPayload turret;
            Item ammo;
        }

        @Override
        public void placed() {
            super.placed();
            NEWBuild();
        }

        @Override
        public void updateTile() {
            super.updateTile();
            for (DefenseCoreBuild.Build building : turretBuild) {
                if (building != null) {
                    building.turret.build.handleItem(building.turret.build, building.ammo);
                    building.turret.update(null, this);
                    building.turret.build.team = team;
                }
            }
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            NEWBuild();
        }

        void NEWBuild() {
            turretBuild = new Build[turret.length];
            for (int i = 0; i < turretBuild.length; i++) {
                BuildPayload b = new BuildPayload(turret[i].newBuilding());
                b.set(x, y, rotation);
                b.build.team = team;
                if (b.block() instanceof ItemTurret itemTurret) for (Item item : itemTurret.ammoTypes.keys()) {
                    turretBuild[i] = new Build(b, item);
                    break;
                }
            }
        }

        @Override
        public void draw() {
            super.draw();
            for (Build build : turretBuild) {
                if (build != null) build.turret.build.draw();
            }
        }
    }
}
