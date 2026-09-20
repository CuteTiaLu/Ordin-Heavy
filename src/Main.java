import Content.Blocks;
import Content.Items;
import mindustry.mod.*;

public class Main extends Mod {
    @Override
    public void loadContent(){
        Items.Create();
        Blocks.Create();
    }
}
