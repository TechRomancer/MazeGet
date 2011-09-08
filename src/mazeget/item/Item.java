package mazeget.item;

import org.newdawn.slick.Image;


public class Item {

	// class data
	public String name;
	public int colour;
	public char symbol;
	public int weight;
	public int value;
	public int count;
	public Image img;

	// constructor
	public Item(Image img) {
		this.img = img;
	}	

	public void use() {
	}
}