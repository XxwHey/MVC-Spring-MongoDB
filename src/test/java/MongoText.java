import MongoDB.model.Music;
import MongoDB.service.IMusicService;
import MongoDB.service.impl.MusicServiceImpl;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import org.bson.Document;
import org.json.JSONObject;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;

/**
 * Created by xiexw on 2017/8/8.
 */
public class MongoText {

    public static void main(String[] args) throws Exception {

//        long sum = 0L;
//        long now = System.currentTimeMillis();
//        for (long i = 0; i < Integer.MAX_VALUE; i++) {
//            sum += i;
//        }
//        long end = System.currentTimeMillis();
//        System.out.println("耗时：" + (end - now) + "ms");

        WeakHashMap map = new WeakHashMap();
        map.put(1, "hello");
        map.put(2, "world");
        System.out.println(map.get(1));
        System.out.println("=========================");
        Thread.sleep(20000);
        System.out.println(map.get(1));
        System.out.println(map.get(2));

//        JaxWsProxyFactoryBean svr = new JaxWsProxyFactoryBean();
//        svr.setServiceClass(ICXFService.class);
//        svr.setAddress("http://localhost:8099/cxf");
//        ICXFService cxf = (ICXFService) svr.create();
//        System.out.println(cxf.sayHi("hello"));

        //lambda
//        List list = Arrays.asList(1, 2, 3);
//        list.forEach(System.out::println);
    }

    public static boolean valueOf(boolean value) {
        return value ? Boolean.TRUE : Boolean.FALSE;
    }

    public static void mongoDB() {
        //连接
        MongoClient client = new MongoClient("localhost", 27017);

        //数据库
        MongoDatabase database = client.getDatabase("HEYBook");

        //获取集合
        MongoCollection<Document> doc = database.getCollection("music");

        //获取文档
        FindIterable<Document> iter = doc.find();
        MongoCursor<Document> cursor = iter.iterator();
        if (cursor.hasNext()) {
            Document object = cursor.next();
            String jsonStr = JSON.serialize(object);
            JSONObject json = new JSONObject(jsonStr);
            System.out.println(json.get("name"));
        }

        //插入文档
        Document data = new Document();
        data.put("id", 1);
        data.put("name", "刚刚好");
        data.put("artist", "薛之谦");
        data.put("album", "初学者");
        data.put("description", "初学者");
        data.put("album_id", 1);
        doc.insertOne(data);

        //获取配置文件
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        IMusicService musicService = context.getBean(MusicServiceImpl.class);
        Music music = new Music(3, "你还要我怎样", "薛之谦", "意外", "《妈妈像花儿一样》", 3);
        musicService.save(music);

//        删除文档
        doc.deleteOne(new Document("id", 1));

//        条件查询
//        Music result = musicService.getById(3);
//        System.out.println(new JSONObject(result));

        FindIterable<Document> result = doc.find(new Document("album_id", 3));
        System.out.println(result.iterator().next());
    }

}
