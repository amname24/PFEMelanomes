package mongo.model;

public class Photo {

	private String path;
	private String name;
	private String id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Photo(String name) {
		this.name = name;
	}
	public Photo() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "photo [name=" + name + "]";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
}
