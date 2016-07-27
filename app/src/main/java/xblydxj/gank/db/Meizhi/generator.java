package xblydxj.gank.db.Meizhi;


import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * Created by 46321 on 2016/7/23/023.
 *
 */
public class generator {
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1,"xblydxj.gank.db.Meizhi");
        Entity entity = schema.addEntity("Meizhi");
        entity.addIdProperty();
        entity.addStringProperty("time");
        entity.addStringProperty("url").unique();
        new DaoGenerator().generateAll(schema,"F:\\gank\\Gank\\app\\src\\main\\java");
    }
}
