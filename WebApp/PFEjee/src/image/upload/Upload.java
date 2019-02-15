package image.upload;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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

import image.classification.PrepareTrain;
import image.classification.baseClassifiers;
import image.segmentation.ImageSegmentation;
import mongo.dao.PhotoDAO;
import mongo.dao.PhotoDataDAO;
import mongo.export.ExportData;
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
	private static FilenameFilter arffFileFilter = new FilenameFilter() {
		public boolean accept(File dir, String name) {
			return name.endsWith(".arff");
		}
	};

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	    Part filePart = request.getPart("myFile"); // Retrieves <input type="file" name="file">
	    String fileName = filePart.getSubmittedFileName().toString(); // MSIE fix.
	   
	    // save  the photo send in  WebContent/IMG
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
	    File repertoire = new File("C:/Users/me/eclipse-workspace2/PFEjee/WebContent/DOCS");
		File[] files = repertoire.listFiles(arffFileFilter);
	    List<Photo> photos = new ArrayList<Photo>();
	    //prepare data train if doen't exist
	    if(files.length<2)
	    	photos = PrepareTrain.run();
	    MongoClient mongo = (MongoClient) request.getServletContext()
				.getAttribute("MONGO_CLIENT");
		PhotoDAO photoDAO = new PhotoDAO(mongo);
		PhotoDataDAO photodataTrainDAO = new PhotoDataDAO(mongo,false);
		OpenCV.loadShared();
		//if dataTrain.csv doesn't exist
		if(files.length<2)
		    for(Photo photo : photos) {
		    	//mongoDB save the photo 
				photoDAO.createPhoto(photo);
				
				//segmentation of the photo
				PhotoData p = new ImageSegmentation().run(photo,false);
				if(photo.getName().equals("IMD211.bmp")||photo.getName().equals("IMD219.bmp")||photo.getName().equals("IMD242.bmp")||photo.getName().equals("IMD240.bmp")||photo.getName().equals("IMD284.bmp")||photo.getName().equals("IMD285.bmp")||photo.getName().equals("IMD348.bmp")||photo.getName().equals("IMD349.bmp")||photo.getName().equals("IMD403.bmp")||photo.getName().equals("IMD404.bmp")||photo.getName().equals("IMD405.bmp")||photo.getName().equals("IMD407.bmp")||photo.getName().equals("IMD408.bmp")||photo.getName().equals("IMD409.bmp")||photo.getName().equals("IMD410.bmp"
			    		)||photo.getName().equals("IMD413.bmp")||photo.getName().equals("IMD417.bmp")||photo.getName().equals("IMD418.bmp")||photo.getName().equals("IMD419.bmp")||photo.getName().equals("IMD411.bmp")||photo.getName().equals("IMD420.bmp")||photo.getName().equals("IMD421.bmp")||photo.getName().equals("IMD423.bmp")||photo.getName().equals("IMD424.bmp")||photo.getName().equals("IMD425.bmp")
						||photo.getName().equals("IMD426.bmp")||photo.getName().equals("IMD435.bmp")||photo.getName().equals("IMD1.jpg")||photo.getName().equals("IMD2.jpg")||photo.getName().equals("IMD3.jpg")||photo.getName().equals("IMD4.jpg")||photo.getName().equals("IMD5.jpg")||photo.getName().equals("IMD6.jpg")||photo.getName().equals("IMD7.jpg")||photo.getName().equals("IMD8.jpg"))
			    p.setMelanome("true");
				else p.setMelanome("false");
				//mongoDB save the photoData 
				photodataTrainDAO.createPhotoData(p);
		    }
	    System.out.println("Photos for training prepared Successfully ");
	    
	    // create object Photo to represent the photo we get    
	    Photo nouvelle = new Photo();
	    ObjectId id = new ObjectId();
	    nouvelle.setId(id.toString());
	    nouvelle.setName(fileName);
	    nouvelle.setPath("C:\\Users\\me\\eclipse-workspace2\\PFEjee\\WebContent\\IMG\\"+fileName.split(".jpg")[0]+"\\"+fileName);

	    //mongoDB save the photo 
		photoDAO.createPhoto(nouvelle);
		System.out.println("Photo Added Successfully with id="+nouvelle.getId());
		
		//segmentation of the photo
		PhotoData test = new ImageSegmentation().run(nouvelle,true);
		System.out.println("Photo segementation Successfully with name="+nouvelle.getName());
		
		//mongoDB save the photoData 
		PhotoDataDAO photodataTestDAO = new PhotoDataDAO(mongo,true);
		photodataTestDAO.createPhotoData(test);
		System.out.println("Photo data Added Successfully with id="+test.getPhotoid());
		
		//export data from mongodb to files 
		ExportData.run(test);
		String message= "";
		//classieur des images
		try {
			message = baseClassifiers.run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		StringBuffer message = new StringBuffer();
//		message.append("Ok");
	    response.getWriter().write(message);  

	    
	}

}
