package uploadImage;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.tomcat.jni.File;

/**
 * Servlet implementation class Upload
 */
@MultipartConfig(fileSizeThreshold = 6291456, // 6 MB
maxFileSize = 10485760L, // 10 MB
maxRequestSize = 20971520L // 20 MB
)
@WebServlet("/Upload")
public class Upload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Upload() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		RequestDispatcher view = request.getRequestDispatcher("WEB-INF/test.html");
//		view.forward(request, response);
		response.getWriter().append("Hello this is a get service ");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	    Part filePart = request.getPart("myFile"); // Retrieves <input type="file" name="file">
	    String fileName = filePart.getSubmittedFileName().toString(); // MSIE fix.
	    InputStream in = filePart.getInputStream();
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    byte[] buf = new byte[1024];
	    int n = 0;
	    while (-1!=(n=in.read(buf)))
	    {
	       out.write(buf, 0, n);
	    }
	    out.close();
	    in.close();
	    byte[] res = out.toByteArray();
	    FileOutputStream fos = new FileOutputStream("C:/Users/me/eclipse-workspace2/PFEjee/WebContent/IMG/old/"+fileName);
	    fos.write(res);
	    fos.close();
	    response.getWriter().append("OK");
	}

}
