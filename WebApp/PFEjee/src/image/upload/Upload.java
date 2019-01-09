package image.upload;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.bson.types.ObjectId;
import org.opencv.core.Core;

import com.mongodb.MongoClient;

import image.segmentation.ImageSegmentation;
import mongo.dao.PhotoDAO;
import mongo.dao.PhotoDataDAO;
import mongo.model.Photo;
import mongo.model.PhotoData;
import nu.pattern.OpenCV;

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
	   
	    // save  the photo send in  WebContent/IMG/old
	    
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
	    new File("C:/Users/me/eclipse-workspace2/PFEjee/WebContent/IMG/"+fileName.split(".jpg")[0]).mkdir();
	    FileOutputStream fos = new FileOutputStream("C:/Users/me/eclipse-workspace2/PFEjee/WebContent/IMG/"+fileName.split(".jpg")[0]+"/"+fileName);
	    fos.write(res);
	    fos.close();
	    
	    // creat object Photo to represent the photo we get
	    
	    Photo nouvelle = new Photo();
	    ObjectId id = new ObjectId();
	    nouvelle.setId(id.toString());
	    nouvelle.setName(fileName);
	    nouvelle.setPath("C:/Users/me/eclipse-workspace2/PFEjee/WebContent/IMG/"+fileName.split(".jpg")[0]);

	    //mongoDB save the photo 
	    MongoClient mongo = (MongoClient) request.getServletContext()
				.getAttribute("MONGO_CLIENT");
		PhotoDAO photoDAO = new PhotoDAO(mongo);
		photoDAO.createPhoto(nouvelle);
		System.out.println("Photo Added Successfully with id="+nouvelle.getId());
		
		//segmentation of the photo
		// Load the native OpenCV library
    	//System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		OpenCV.loadShared();
		PhotoData p = new ImageSegmentation().run(nouvelle);
		System.out.println("Photo segementation Successfully with name="+nouvelle.getName());
		
		//mongoDB save the photoData 
		PhotoDataDAO photodataDAO = new PhotoDataDAO(mongo);
		photodataDAO.createPhotoData(p);
		System.out.println("Photo data Added Successfully with id="+p.getPhotoid());
		
	    response.getWriter().append("OK");
	}

}
