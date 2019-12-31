import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MapTest {


    public static void main(String[] args) {
        Map<String,Object> map=new ConcurrentHashMap<String,Object>();
        map.put("str0",0);
        map.put("str1",1);
        map.put("str2",2);
        map.put("str3",3);

        Set<String> keys=map.keySet();

        for (String key: keys){
            System.out.println("健："+key+",值："+map.get(key));

        }


    }
}
