import java.util.*;
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
            System.out.println("key健："+key+",value值："+map.get(key));
        }
        List<String> list=new ArrayList<String>();
        list.add("123");
        list.add("345");
        for (String str:list){
            System.out.println("list遍历："+str);
        }

    }
}
