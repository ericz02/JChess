package chess;
/**
 * Class to display GUI
 * @author Kevin Reid, Jerry Aviles, & Eric Zheng.
 * @date April 8 - May 3, 2022
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class GUI {
	
	/** This method converts the Y coordinates and reserves it because JavaSwing 0,0 starts 
	 * at the top left instead of the bottom right causing problems in the way I choose to draw the pieces 
	 * @param yconvert The row to be switched
	 * @return The Y coordinate that is switched to be suited to match the console
	 */
	private static int switcheroo(int yconvert) {
		int yp = 0;
		switch(yconvert) {
		case 0:
			yp = 7;
			break;
		case 1:
			yp = 6;
			break;
		case 2:
			yp = 5;
			break;
		case 3:
			yp = 4;
			break;
		case 4:
			yp = 3;
			break;
		case 5:
			yp = 2;
			break;
		case 6:
			yp = 1;
			break;
		case 7:
			yp = 0;
			break;
		default:
			break;
		}
		 
		return yp;
	}
	
	/**
	 * This is a method to display the GUI  
	 * @Lines 62-72 This is used to get the image of the chess pieces I create subimages for all pieces
	 * @Line  74 Used to get Data from the console 
	 * @Lines 75-79 Startup code for GUI
	 * @Lines 80-94 This draws the checkerboard for the Chess game
	 * @Lines 96-127 This draws the image on the board, it is inside a nested loop to get data for every piece
	 * is not null then assigns the piece a subimage depending on what piece it is, in like 122 I draw that piece in
	 * its position by mutipling by 64 let the computer know where to draw, I have to use the switcheroo method in line 122
	 * to counteract 0,0 being in the top left instead of the bottom right.
	 * @Lines 128-131 Makes the GUI visible
	 */
	public static void display()  {
		BufferedImage all= null;
		try{
			all = ImageIO.read(new File("C:\\Users\\Kevin\\Desktop\\MyWorkspace\\CSC 330 N\\src\\chess\\chess.png"));
		} catch(IOException e) {}
        Image imgs[]=new Image[12];
        int ind=0;
        for(int y=0;y<400;y+=200){
        for(int x=0;x<1200;x+=200){
            imgs[ind]=all.getSubimage(x, y, 200, 200).getScaledInstance(64, 64, BufferedImage.SCALE_SMOOTH);
        ind++;
        }}    
        
        MoveableGamePiece[][] array = PlayableChessBoard.getDeepCopyOfPieces();
  //     System.out.println(array[0][0]);
	JFrame frame = new JFrame(); // Just startup code
    frame.setBounds(10, 10, 512, 512);  // sets the size of the board.
    frame.setUndecorated(true);
    JPanel pn=new JPanel(){
        @Override
        public void paint(Graphics g) {  // Using a graphics class to draw the board
        boolean white=true;
        for(int y= 0;y<8;y++){// Double nested loop that places white in a column.
        	for(int x= 0;x<8;x++){
        		if(white){
        			g.setColor(new Color(235,235, 208));  // This sets the color of the column to white
        		}else{
        			g.setColor(new Color(119, 148, 85));  // this sets the color of the column to green                 
        		}
            g.fillRect(x*64, y*64, 64, 64);  // This executes the color that is being set @ lines 25 and 27
            white=!white; // boolean that changes every time the loop ends.
        	}
        	white=!white; // same as above.
        	}           

        for(int row = 0; row < 8; row++){
        	for (int col = 0; col < 8; col++) {
        		int ind = 0;
        		//System.out.println(array[row][col].getClass().getSimpleName().equals("Pawn"));
        		if (array[row][col] != null) {
        			if(array[row][col].getClass().getSimpleName().equals("King")){
            			ind = 0;
            		}
        			if(array[row][col].getClass().getSimpleName().equals("Queen")){
            			ind = 1;
            		}
        			if(array[row][col].getClass().getSimpleName().equals("Bishop")){
            			ind = 2;
            		}
        			if(array[row][col].getClass().getSimpleName().equals("Knight")){
            			ind = 3;
            		}
        			if(array[row][col].getClass().getSimpleName().equals("Rook")){
            			ind = 4;
            		}
        			if(array[row][col].getClass().getSimpleName().equals("Pawn")){
        			ind = 5;
        			}
        			if(!(array[row][col].getColor() == chess.Color.WHITE)) {
        				ind+=6;
        			}
        		g.drawImage(imgs[ind],col*64,switcheroo(row)*64,this);
        		}
        		

}};
        }};
frame.add(pn);
frame.setDefaultCloseOperation(3);
frame.setVisible(true);
}}
