package collection;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiConsumer;

public class CollectionDemo {
    public static void main(String[] args) {
        HashMap<Integer,Integer> map = new HashMap<>();
//        map.containsKey()
        PriorityQueue<Integer> queue = new PriorityQueue<>(
                new Comparator<Integer>() {
                    @Override
                    public int compare(Integer o1, Integer o2) {
                        return o2-o1;
                    }
        });
        queue.add(5);
        queue.add(2);
        queue.add(3);
        while(queue.size()!=0){
            System.out.println(queue.poll());
        }
    }
    private static String  maxSameChild(String a,String b){
        int x,y;
        if(a==null||b==null||(x = a.length())==0||(y=b.length())==0)return "";
        int max=0,end=0;
        int [] arr = new int[x];
        for(int i=0;i<y;i++){
            for(int j=x-1;j>=0;j--){
                if(a.charAt(j)==b.charAt(i)){
                    arr[j]=1+((i==0||j==0)?0:arr[j-1]);
                    if(arr[j]>max) {
                        max = arr[j];
                        end = j;
                    }
                }else{
                    arr[j]=0;
                }
            }
        }
        return a.substring(end-max+1,end+1);
    }
    public static String validIPAddress(String IP) {
        String neither = "Neither";
        if(IP==null||IP.length()<7||IP.length()>39) return neither;
        if(IP.contains(".")){
            //字符必须是'.'、'0'-'9'、split后的个数必须为4、长度小于3
            //数字大小[0，255]、不为0的不能以0开头、为0的不能有多个0
            for(char c : IP.toCharArray()){
                if(c=='.')continue;
                if(c<'0'||c>'9')return neither;
            }
            String[] arr = IP.split("\\.");
            if(arr.length!=4)return neither;
            for(String s : arr){
                if(s.length()>3||s.length()==0)return neither;
                int v = Integer.parseInt(s);
                if((v<0||v>255)
                        ||(v!=0&&s.startsWith("0"))
                        ||(v==0&&s.startsWith("00")))
                    return neither;
            }
            return "IPv4";
        }else if(IP.contains(":")){
            //字符必须是':'、'0'-'9'、'a'-'z'、'A'-"Z"
            //split后的个数必须为8、长度不超过4、
            //数字大小[0，255*255]
            for(char c : IP.toCharArray()){
                if(c==':'||(c>='0'&&c<='9')||(c>='a'&&c<='z')||(c>='A'&&c<='Z'))continue;
                return neither;
            }
            String[] arr = IP.split(":");
            if(arr.length!=8)return neither;
            int max  = Integer.parseInt("FFFF",16);
            for(String s : arr){
                if(s.length()>4||s.length()==0)return neither;
                int v = Integer.parseInt(s,16);
                if(v<0||v>max)return neither;
            }
            return "IPv6";
        }
        return neither;
    }
    private void test4(){
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("a");
        arrayList.add("b");
        arrayList.add("c");

        Iterator<String>  iterable = arrayList.iterator();
        while(iterable.hasNext()){
            if(iterable.next().equals("b")){
                iterable.remove();
            }
        }
        System.out.println("arr:::"+arrayList.size());

        List<String> arrayList2 = new ArrayList<>();
    }
    private void test3(){
            LinkedHashMap<String,String> linkedHashMap = new LinkedHashMap<>(
                    16,0.75f,true);
            linkedHashMap.put("b","2");
            linkedHashMap.put("a","1");
            linkedHashMap.put("c","3");

            String result = linkedHashMap.get("b");
            System.out.println(result);
            linkedHashMap.forEach(new BiConsumer(){
                @Override
                public void accept(Object o, Object o2) {
                    System.out.println(o+"  "+o2);
                }
            });
        }
    private void test2(){
        Object object = new Object();
        int hashCode= object.hashCode();
        int len = 512;
        System.out.println("&:::"+(hashCode&(len-1)));
        System.out.println("%:::"+(hashCode%len));
    }

    private void testMap(){
        //如果允许为空
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        //map.put("key",null);
        if(map.get("key")==null){
            if(map.contains("key")){
                //逻辑...
            }else{
                //逻辑...
            }
        }

    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

}
