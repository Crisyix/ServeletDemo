package CRUD;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
@WebServlet("/AddHero")
public class AddHero extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        float hp = Float.parseFloat(req.getParameter("hp"));
        int damage = Integer.parseInt(req.getParameter("damage"));

        Hero hero = new Hero();
        hero.name = name;
        hero.hp = hp;
        hero.damage = damage;
        try
        {
            new HeroDAO().add(hero);
            resp.sendRedirect("/listHero");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }
}
