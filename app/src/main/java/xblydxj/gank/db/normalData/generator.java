package xblydxj.gank.db.normalData;


import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * Created by 46321 on 2016/7/23/023.
 *
 */
public class generator {
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1,"xblydxj.gank.db.normalData");
        Entity entity = schema.addEntity("dataCatch");
        entity.addIdProperty();
        entity.addStringProperty("desc");
        entity.addStringProperty("time");
        entity.addStringProperty("author");
        entity.addStringProperty("url");
        entity.addStringProperty("type");
        new DaoGenerator().generateAll(schema,"F:\\gank\\Gank\\app\\src\\main\\java");
    }
}
