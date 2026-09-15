package type.Block;

import arc.util.io.Reads;
import mindustry.gen.Building;
import mindustry.type.Item;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.ItemTurret;
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

            Build(Building turret, Item ammo) {
                this.turret = turret;
                this.ammo = ammo;
            }

            Building turret;
            Item ammo;
        }

        @Override
        public void placed() {
            super.placed();
            turretBuild = new Build[turret.length];
            for (int i = 0; i < turretBuild.length; i++) {
                Building b = turret[i].newBuilding();
                if (b.block instanceof ItemTurret it) {
                    b.team = team;
                    b.tile = tile;
                    b.x = x;
                    b.y = y;
                    for (Item item : it.ammoTypes.keys()) {
                        turretBuild[i] = new Build(b, item);
                        break;
                    }
                }
            }
        }

        @Override
        public void updateTile() {
            super.updateTile();
            for (Build building : turretBuild) {
                if (building != null) {
                    building.turret.handleItem(building.turret, building.ammo);
                    building.turret.updateTile();
                }
            }
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            turretBuild = new Build[turret.length];
            for (int i = 0; i < turretBuild.length; i++) {
                Building b = turret[i].newBuilding();
                if (b.block instanceof ItemTurret it) {
                    b.team = team;
                    b.tile = tile;
                    b.x = x;
                    b.y = y;
                    for (Item item : it.ammoTypes.keys()) {
                        turretBuild[i] = new Build(b, item);
                        break;
                    }
                }
            }
        }

    }
}
