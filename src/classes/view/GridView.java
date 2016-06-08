package classes.view;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.ScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GridView extends JPanel{
		
		final protected Dimension dim;
		final protected int cellSize;
		final protected int cellCountX;
		final protected int cellCountY;
		
		final private int hLines[];
		//final private int vLines[];
		
		private boolean cellState[][];
		protected Rectangle view;
		protected Point mousePos;
		protected int coordX, coordY, sectSize, sectX, sectY;
		
	
		public GridView(){
			
			dim = new Dimension(400000, 400000);
			setPreferredSize(dim);
			
			cellSize = 10;
			cellCountX = dim.width/cellSize;
			cellCountY = dim.height/cellSize;
			cellState = new boolean[cellCountX][cellCountY];
			
			hLines = new int[cellCountY];
			for(int i = 0;i<cellCountY;i++)
				hLines[i] = i*cellSize;
			
			/*
			for(boolean row[]: cellState)
			    java.util.Arrays.fill(row, true);
			*/
			addMouseListener(new MouseAdapter() {
                
                @Override
                public void mousePressed(MouseEvent e){
                	mousePos = e.getPoint();
                	int cellX = (int)Math.floor(mousePos.x/cellSize);
                	int cellY = (int)Math.floor(mousePos.y/cellSize);
                	
                	if(cellState[cellX][cellY] == false)
                		cellState[cellX][cellY] = true;
                	else
                		cellState[cellX][cellY] = false;

                	paintComponent(getGraphics());
                	
                }
                
            });
            
            addMouseMotionListener(new MouseAdapter() {
            	
            	@Override
                public void mouseMoved(MouseEvent e){
                	mousePos = e.getPoint();
                	coordX = calculateX(mousePos.x);
                	coordY = calculateY(mousePos.y);
                	sectX = calculateSector(coordX, sectSize);
                	sectY = calculateSector(coordY, sectSize);
                }
                });

		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

		    drawCells(g);
		    //drawLines(g);
		    //drawVertical(g);
		    drawHorizontal(g);
		    //drawSectors(g, sectSize);  
		}

		
	
		
		/**
		 * Draws horizontal lines for the grid
		 * @param g
		 */
		
		private void drawHorizontal(Graphics g) {
			int top = this.view.y/this.cellSize;
			int bottom = (this.view.y+this.view.height)/this.cellSize;
			int leftEdge = this.view.x;
			int rightEdge = this.view.x+this.view.width;
			int mid = (hLines.length/2);
			
			if(bottom < cellCountY)
				bottom +=1;
			
			if(top > 0)
				top -=1;
			
			
			g.setColor(Color.LIGHT_GRAY);
		    for(int i = top; i < bottom;i++){
		    	int posY = hLines[i];
		    	if(i == (hLines.length/2)){
		    		g.setColor(Color.RED);
		    		g.drawLine(leftEdge, posY, rightEdge, posY);
		    		g.setColor(Color.LIGHT_GRAY);
		    	}

		    	else
		    		g.drawLine(leftEdge, posY, rightEdge, posY);
		    }   
		}
		
		
		/**
		 * Draws sector lines to the grid
		 * @param g
		 * @param size Integer amount of cells in a sector
		 */
		
		private void drawSectors(Graphics g, int size) {
			g.setColor(Color.GRAY);
			
			int midY = (int)Math.floor((this.cellCountY/2))*this.cellSize;
			int midX = (int)Math.floor((this.cellCountX/2))*this.cellSize;
			int offset = (size*this.cellSize);
			
			for (int i = midY-offset; i >= 0;i-=offset){
		    	g.drawLine(0, i, this.dim.width, i);
		    }  
		    
		    for (int i = midY+offset; i <= this.dim.height;i+=offset){
		    	g.drawLine(0, i, this.dim.width, i);
		    }
		    
		    for (int i = midX-offset; i >= 0;i-=offset){
		    	g.drawLine(i, 0, i, this.dim.height);
		    }  
		    
		    for (int i = midX+offset; i <= this.dim.height;i+=offset){
		    	g.drawLine(i, 0, i, this.dim.height);
		    }
		}
		
		/**
		 * Draws visible cells to area of view
		 * @param g
		 */
		private void drawCells(Graphics g) {
			int left = view.x/this.cellSize;
			int right = (view.x+view.width)/this.cellSize;
			int top = view.y/this.cellSize;
			int bottom = (view.y+view.height)/this.cellSize;
			
			if(right < cellCountX)
				right +=1;
			if(bottom < cellCountY)
				bottom +=1;
			
			for(int i = left;i < right; i++)
		    	for(int k = top;k < bottom; k++)
		    		if(this.cellState[i][k] == true){
		    			int posX = (i*this.cellSize)+1;
		    			int posY = (k*this.cellSize)+1;
		    			g.setColor(Color.BLACK);
	    				g.drawRect(posX,posY,this.cellSize-1,this.cellSize-1);
	    				g.setColor(Color.BLACK);
	    				g.fillRect(posX,posY,this.cellSize-1,this.cellSize-1);

		    		}
		}
		
		
		
		/**
		 * Draws vertical lines for the grid
		 * @param g
		 */
		
		private void drawVertical(Graphics g) {
			int left = view.x/this.cellSize;
			int right = (view.x+view.width)/this.cellSize;
			
			
			for(int i = 0; i <= this.cellCountX;i++){
		    	int posX = i * this.cellSize;
		    	if(i == (int) Math.floor((this.cellCountX/2))){
		    		g.setColor(Color.RED);
		    		g.drawLine(posX, 0, posX, this.dim.height);
		    		//g.drawLine(posX+1, 0, posX+1, this.dim.height);
		    		g.setColor(Color.LIGHT_GRAY);
		    	}
		    	else
		    		g.drawLine(posX, 0, posX, this.dim.height);	
		    }
		}
		
		/**
		 * Calculates coordinates from mouse X position
		 * @param inY Double mouse position on the grid
		 * @return Integer rounded X coordinate of the mouse position
		 */
		
		private int calculateX(double inX){
			double x = inX/this.cellSize;
			double half;
			
				half = ((this.dim.width/this.cellSize)/2);
			
			return (int)Math.round(((x)-half));
		}
		
		/**
		 * Calculates coordinates from mouse Y position
		 * @param inY Double mouse position on the grid
		 * @return Integer rounded Y coordinate of the mouse position
		 */
		
		private int calculateY(double inY){
			double y = inY/this.cellSize;
			double half = (this.dim.height/this.cellSize)/2;
			
			return (int)Math.round(-((y)-half));
		}
		
		/**
		 * Calculates sector from current coordinates of mouse position
		 * @param coord Double current coordinate
		 * @param sSize Integer amount of cells in a sector
		 * @return Integer of the current sector
		 */
		
		private int calculateSector(double coord, int sSize){
			if(coord > 0)
				return (int) Math.ceil(coord/sSize);
			else{
			double ret = coord;
				ret -= 1;
				return (int) Math.floor(ret/sSize);
			}
				
		}
		
		public void getView(Rectangle r){
			this.view = r;
		}
}
