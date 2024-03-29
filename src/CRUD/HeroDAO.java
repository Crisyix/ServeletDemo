package CRUD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HeroDAO {

        public HeroDAO() {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        public Connection getConnection() throws SQLException {
            return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/how2java?serverTimezone=UTC", "root",
                    "wyx123");
        }

        public int getTotal() {
            int total = 0;
            try (Connection c = getConnection(); Statement s = c.createStatement();) {

                String sql = "select count(*) from hero";

                ResultSet rs = s.executeQuery(sql);
                while (rs.next()) {
                    total = rs.getInt(1);
                }

                System.out.println("total:" + total);

            } catch (SQLException e) {

                e.printStackTrace();
            }
            return total;
        }

        public void add(Hero hero) {

            String sql = "insert into hero values(null,?,?,?)";
            try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);) {

                ps.setString(1, hero.name);
                ps.setFloat(2, hero.hp);
                ps.setInt(3, hero.damage);

                ps.execute();
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    hero.id = id;
                }
            } catch (SQLException e) {

                e.printStackTrace();
            }
        }

        public void update(Hero hero) {

            String sql = "update hero set name= ?, hp = ? , damage = ? where id = ?";
            try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {

                ps.setString(1, hero.name);
                ps.setFloat(2, hero.hp);
                ps.setInt(3, hero.damage);
                ps.setInt(4, hero.id);

                ps.execute();

            } catch (SQLException e) {

                e.printStackTrace();
            }

        }

        public void delete(int id) {

            try (Connection c = getConnection(); Statement s = c.createStatement();) {

                String sql = "delete from hero where id = " + id;

                s.execute(sql);

            } catch (SQLException e) {

                e.printStackTrace();
            }
        }

        public Hero get(int id) {
            Hero hero = null;

            try (Connection c = getConnection(); Statement s = c.createStatement();) {

                String sql = "select * from hero where id = " + id;

                ResultSet rs = s.executeQuery(sql);

                if (rs.next()) {
                    hero = new Hero();
                    String name = rs.getString(2);
                    float hp = rs.getFloat("hp");
                    int damage = rs.getInt(4);
                    hero.name = name;
                    hero.hp = hp;
                    hero.damage = damage;
                    hero.id = id;
                }

            } catch (SQLException e) {

                e.printStackTrace();
            }
            return hero;
        }

        public List<Hero> list() {
            return list(0, Short.MAX_VALUE);
        }

        public List<Hero> list(int start, int count) {
            List<Hero> heros = new ArrayList<Hero>();

            String sql = "select * from hero order by id desc limit ?,? "; //limit 显示结果数量，？代表参数

            try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql);) { // try语句带小括号，资源在try结束时会自动释放
                                                                                                    // prepare预编译sql语句获得预编译对象
                ps.setInt(1, start);// 设置参数值
                ps.setInt(2, count);

                ResultSet rs = ps.executeQuery();//执行sql并返回结果

                while (rs.next()) {
                    Hero hero = new Hero();
                    //获取每一列的值
                    int id = rs.getInt(1);
                    String name = rs.getString(2);//按列的索引取值
                    float hp = rs.getFloat("hp");//按列名取值
                    int damage = rs.getInt(4);
                    hero.id = id;
                    hero.name = name;
                    hero.hp = hp;
                    hero.damage = damage;
                    heros.add(hero);
                }
            } catch (SQLException e) {

                e.printStackTrace();
            }
            return heros;
        }

    }

