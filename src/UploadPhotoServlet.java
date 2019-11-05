import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;

public class UploadPhotoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String filename;
        try {
            DiskFileItemFactory factory = new DiskFileItemFactory(); //封装上传项目，构造文件工厂对象
            ServletFileUpload upload = new ServletFileUpload(factory);  //创建上传工具,负责上传文件
            factory.setSizeThreshold(1024 * 1024);//上传文件大小限制为1M
            List items = null;
            try {
                items = upload.parseRequest(req);//解析每一个表单项，封装成List。
            } catch (FileUploadException e) {
                e.printStackTrace();
            }
            assert items != null;

            for (Object item : items) {
                FileItem fileItem = (FileItem) item;
                if (!fileItem.isFormField()) {
                    String userPhone = fileItem.getName();//原始文件名
                    filename = System.currentTimeMillis() + ".txt";//以时间戳命名文件
                    String photoFolder = req.getServletContext().getRealPath(userPhone.substring(0, 11));//获取上传文件夹的绝对路径
                    File f = new File(photoFolder, filename);
                    f.getParentFile().mkdirs();
                    InputStream inputStream = fileItem.getInputStream();//获取文件上传的输入流

                    //复制文件
                    FileOutputStream fos = new FileOutputStream(f);
                    byte[] b = new byte[1024 * 1024];
                    int length;
                    while (-1 != (length = inputStream.read(b))) {
                        fos.write(b, 0, length);
                    }
                    fos.close();
                } else {
                    System.out.println("getFieldName: " + fileItem.getFieldName());
                    String value = fileItem.getString();
                    value = new String(value.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                    System.out.println("value: " + value);
                }

            }

           // String html = "<img width='500 height='400' src='photos/%s' />";
           // resp.setContentType("text/html");
            //PrintWriter pw = resp.getWriter();
            //pw.printf(html, filename);//显示图片

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
