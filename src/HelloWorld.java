import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/HelloWorld")//注解方式部署Servlet
public class HelloWorld extends HttpServlet {
    private String message;
    String html = null;

    @Override
    public void init() throws ServletException {
        System.out.println("init...");
        html =  "<script>location.href='login.html'</script>";

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置响应内容类型
        resp.setContentType("text/html");

        //设置逻辑实现
        PrintWriter out = resp.getWriter();
        out.println(html);
    }

    @Override
    public void destroy() {
        super.destroy();
        System.out.println("destroy...");
    }
}
