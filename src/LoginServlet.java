

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.StandardSocketOptions;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;


public class LoginServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8"); //设置编码格式
        resp.setContentType("text/html;charset=UTF-8"); //设置内容格式
        String name  = req.getParameter("name");
        String password = req.getParameter("password");

        if(name.equals("wyx")&&password.equals("123"))
            resp.sendRedirect("upload.html");//客户端跳转，客户端直接跳转到页面
        else
            req.getRequestDispatcher("failed.html").forward(req,resp);//服务器跳转,forward转发数据，resp必须没有提交，客户端没有显示跳转



        System.out.println("getQueryString:"+req.getQueryString());
        System.out.println("remotePort:"+req.getRemotePort());
        System.out.println("method:"+req.getMethod());
        System.out.println("servletContext"+req.getServletContext().getRealPath("photos"));
        //PrintWriter pr = resp.getWriter();
        //pr.println(html);
        Map<String,String[]> parameterMap  = req.getParameterMap();//读取所有参数的Map
        for(String para:parameterMap.keySet())
        {
            String[] values = parameterMap.get(para);
            System.out.println(para+":"+ Arrays.asList(values));
        }

        Enumeration<String> heads =  req.getHeaderNames();//获取浏览器的所有头信息
        while (heads.hasMoreElements())
        {
            String header = heads.nextElement();
            String value = req.getHeader(header);
            System.out.printf("%s\t%s\n",header,value);
        }

    }
}
