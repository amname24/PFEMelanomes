package mongo.model;

import java.util.List;

public class PhotoData {
	
	private String Photoid;
	private int Asymmetry;
	private int White;
	private int Red;
	private int LightBrown;
	private int DarkBrown;
	private int BlueGray;
	private int Black;
	private String PigmentNetwork;
	private String DotsGlobules;
	private String Streaks;
	private String RegressionAreas;
	private String BlueWhitishVeil;
	
	@Override
	public String toString() {
		return "PhotoData [Photoname=" + Photoid + ", Asymmetry=" + Asymmetry + ", White=" + White + ", Red=" + Red
				+ ", LightBrown=" + LightBrown + ", DarkBrown=" + DarkBrown + ", BlueGray=" + BlueGray + ", Black="
				+ Black + ", PigmentNetwork=" + PigmentNetwork + ", DotsGlobules=" + DotsGlobules + ", Streaks="
				+ Streaks + ", RegressionAreas=" + RegressionAreas + ", BlueWhitishVeil=" + BlueWhitishVeil + "]";
	}
	public int getAsymmetry() {
		return Asymmetry;
	}
	public void setAsymmetry(int asymmetry) {
		Asymmetry = asymmetry;
	}
	public int getWhite() {
		return White;
	}
	public void setWhite(int white) {
		White = white;
	}
	public int getRed() {
		return Red;
	}
	public void setRed(int red) {
		Red = red;
	}
	public int getLightBrown() {
		return LightBrown;
	}
	public void setLightBrown(int lightBrown) {
		LightBrown = lightBrown;
	}
	public int getDarkBrown() {
		return DarkBrown;
	}
	public void setDarkBrown(int darkBrown) {
		DarkBrown = darkBrown;
	}
	public int getBlueGray() {
		return BlueGray;
	}
	public void setBlueGray(int blueGray) {
		BlueGray = blueGray;
	}
	public int getBlack() {
		return Black;
	}
	public void setBlack(int black) {
		Black = black;
	}
	public String getPigmentNetwork() {
		return PigmentNetwork;
	}
	public void setPigmentNetwork(String pigmentNetwork) {
		PigmentNetwork = pigmentNetwork;
	}
	public String getDotsGlobules() {
		return DotsGlobules;
	}
	public void setDotsGlobules(String dotsGlobules) {
		DotsGlobules = dotsGlobules;
	}
	public String getStreaks() {
		return Streaks;
	}
	public void setStreaks(String streaks) {
		Streaks = streaks;
	}
	public String getRegressionAreas() {
		return RegressionAreas;
	}
	public void setRegressionAreas(String regressionAreas) {
		RegressionAreas = regressionAreas;
	}
	public String getBlueWhitishVeil() {
		return BlueWhitishVeil;
	}
	public void setBlueWhitishVeil(String blueWhitishVeil) {
		BlueWhitishVeil = blueWhitishVeil;
	}
	public String getPhotoid() {
		return Photoid;
	}
	public void setPhotoid(String photoid) {
		Photoid = photoid;
	}
	
	
	
}
