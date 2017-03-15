import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * Created by kma on 31.01.2017.
 */
public class Main {

    private static final String url = "jdbc:postgresql://localhost:5432/postgres";
    private static final String user = "postgres";
    private static final String password = "root";

    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;


    public static void main(String[] args) {

        TreeMap<Integer,SortedSet<Integer>> birthday = new TreeMap<>();
        TreeMap<Integer,SortedSet<Integer>> gender = new TreeMap<>();
        TreeMap<Integer,SortedSet<Integer>> city = new TreeMap<>();


        String query = "SELECT id, (('2017-02-26'- birthday::date)/365)::int, gender, city_id, EXTRACT(EPOCH FROM time_reg)::INT FROM user1 WHERE ID BETWEEN 1 AND 999999";
        String query1 ="SELECT MIN((('2017-02-26'- birthday::date)/365)::int) FROM user1";
        String query2 ="SELECT MAX((('2017-02-26'- birthday::date)/365)::int) FROM user1";

        System.out.println("Please wait....");
//собираем БД в массивы
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            rs = QueryGet(query);

            while (rs.next()) {
                int c1 = rs.getInt(1);
                int c2 = rs.getInt(2);
                int c3 = rs.getInt(3);
                int c4 = rs.getInt(4);
//                int c5 = rs.getInt(5);

                SortedSet<Integer> birthdaySet = new TreeSet<>();
                SortedSet<Integer> genderSet = new TreeSet<>();
                SortedSet<Integer> citySet = new TreeSet<>();

                AddElement (birthday, birthdaySet, c2, c1);
                AddElement (gender, genderSet, c3, c1);
                AddElement (city, citySet, c4, c1);

//                System.out.print(c1+"\t");
//                System.out.print(c2+"\t");
//                System.out.print(c3+"\t");
//                System.out.print(c4+"\t");
//                System.out.println(c5+"\t");
//            }
            }
//            rs.close();



        }
        catch (SQLException e) {
            e.printStackTrace();
        }

//находим макс и мин возраст в БД
        int minage = 0;
        int maxage = 0;
        try {
            rs = QueryGet(query1);
            rs.next();
            minage = rs.getInt(1);

            rs = QueryGet(query2);
            rs.next();
            maxage = rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        int a1, a2, a3;
//запрашиваем данные для поиска

        SortedSet<Integer> birthdayOut = new TreeSet<>();
        SortedSet<Integer> genderOut = new TreeSet<>();
        SortedSet<Integer> cityOut = new TreeSet<>();

        a1 = ScannerParam("возраст(0-не искать,", minage, maxage);
        birthdayOut = IsElementInMap(a1,birthday,"людей", "возрастом");
        a2 = ScannerParam("пол(0-не искать,1-М,2-Ж,", 0, 2);
        genderOut = IsElementInMap(a2,gender,"людей", "полом");
        a3 = ScannerParam("id города(0-не искать,", 0, 2500000);
        cityOut = IsElementInMap(a3,city,"города", "id");

        long startTime = System.currentTimeMillis();

        if(birthdayOut.isEmpty()){
            if(genderOut.isEmpty()){
                if(cityOut.isEmpty()){
                    System.out.println("совпадений не найдено");
                }else{
                    System.out.println(city.get(a1));
                }
            }else{
                if(cityOut.isEmpty()){
                    System.out.println("2");
                    System.out.println(gender.get(a1));
                }else{
                    System.out.println("2 3");
                    SortedSet<Integer> s = new TreeSet<>();
                    s.addAll(city.get(a3));
                    s.retainAll(gender.get(a2));
                    System.out.println(s);
                }
            }

        }else{
            if(genderOut.isEmpty()){
                if(cityOut.isEmpty()){
                    System.out.println("1");
                    System.out.println(birthday.get(a1));
                }else{
                    System.out.println("1 3");
                    SortedSet<Integer> s = new TreeSet<>();
                    s.addAll(city.get(a3));
                    s.retainAll(birthday.get(a1));
                    System.out.println(s);
                }
            }
            else{
                if(cityOut.isEmpty()){
                    System.out.println("1 2");
                    SortedSet<Integer> s = new TreeSet<>();
                    s.addAll(birthday.get(a1));
                    s.retainAll(gender.get(a2));
                    System.out.println(s);
                }else{
                    System.out.println("1 2 3");
                    SortedSet<Integer> s = new TreeSet<>();
                    s.addAll(city.get(a3));
                    s.retainAll(birthday.get(a1));
                    s.retainAll(gender.get(a2));
                    System.out.println(s);
                }
            }
        }

        long timeSpent = System.currentTimeMillis() - startTime;
        System.out.println("программа выполнялась " + timeSpent + " миллисекунд");

//        for (Map.Entry<Integer, SortedSet<Integer>> t : birthday.entrySet()){
//                System.out.println(t.getKey() + " : " + t.getValue());
//        }
//        for (Map.Entry<Integer, SortedSet<Integer>> t : gender.entrySet()){
//            System.out.println(t.getKey() + " : " + t.getValue());
//        }
//        for (Map.Entry<Integer, SortedSet<Integer>> t : city.entrySet()){
//            System.out.println(t.getKey() + " : " + t.getValue());
//        }

    }

    private static int ScannerParam(String s, int min, int max){
        int i = 0;
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите " + s + " минимальное-" + min + ", максимальное-" + max +") :");
        if(sc.hasNextInt()){
                i = sc.nextInt();
            if((i < min)||(i > max)){
                System.out.println("неверное значение или формат");
                ScannerParam(s,min,max);
            }
        } else {
            System.out.println("неверное значение или формат");
            ScannerParam(s,min,max);
        }
        return i;
    }

    private static ResultSet QueryGet(String q) {
        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery(q);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    private static void AddElement(TreeMap<Integer,SortedSet<Integer>> t, SortedSet<Integer> s, int i, int j) {
        if (t.containsKey(i)){
            s = t.get(i);
            s.add(j);
        }else{
            s.add(j);
            t.put(i,s);
        }

    }

    private static SortedSet<Integer> IsElementInMap(int i, TreeMap<Integer, SortedSet<Integer>> t, String s1, String s2) {
        SortedSet<Integer> sl = new TreeSet<>();
        if(i==0){
            sl.clear();
        }
        else if(t.containsKey(i)){
            sl = t.get(i);
        }
        else{
            System.out.println(s1 + " с таким " + s2 + " еще нет в БД");
            System.out.println("число совпадений 0");
            System.exit(0);
        }
        return sl;
    }


}
