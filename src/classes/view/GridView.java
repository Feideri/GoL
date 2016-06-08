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
		
		final private int hLines[][];
		final private int vLines[][];
		
		private boolean cellState[][];
		protected Rectangle view;
		protected Point mousePos;
		protected int coordX, coordY, sectSize, sectX, sectY;
		
	
		public GridView(){
			
			this.dim = new Dimension(400000, 400000);
			setPreferredSize(dim);
			
			this.cellSize = 10;
			this.sectSize = 10;
			this.cellCountX = dim.width/cellSize;
			this.cellCountY = dim.height/cellSize;
			this.cellState = new boolean[cellCountX][cellCountY];
			
			this.hLines = new int[2][cellCountY];
			this.vLines = new int[2][cellCountX];
				
			setLineTypes();	
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

		/**
		 * Sets line types for drawing.
		 * Used for initialization of the object and for setting new sector sizes
		 */
		protected void setLineTypes() {
			int midLine = (cellCountY/2);
			int sectLine = midLine % sectSize; //first sector line
			for(int i = 0;i < this.cellCountY;i++){
				hLines[0][i] = i*this.cellSize;
				if(i == this.cellCountY/2){
					hLines[1][i] = 3;
					sectLine += this.sectSize;
				}
				else if (i == sectLine){
					this.hLines[1][i] = 1;
					sectLine += sectSize;
				}		
				else
					this.hLines[1][i] = 0;		
			}
			
			midLine = (cellCountX/2);
			sectLine = midLine % sectSize; //first sector line
			for(int i = 0;i < cellCountX;i++){
				this.vLines[0][i] = i*cellSize;
				if(i == cellCountX/2){
					this.vLines[1][i] = 3;
					sectLine += sectSize;
				}
				else if (i == sectLine){
					this.vLines[1][i] = 1;
					sectLine += sectSize;
				}		
				else
					this.vLines[1][i] = 0;		
			}
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

		    drawCells(g);
		    drawHorizontal(g); 
		    drawVertical(g);
		}
	
		/**
		 * Draws horizontal lines for the grid in the visible area
		 * @param g
		 */
		
		private void drawHorizontal(Graphics g) {
			int top = this.view.y/this.cellSize;
			int bottom = (this.view.y+this.view.height)/this.cellSize;
			int leftEdge = this.view.x;
			int rightEdge = this.view.x+this.view.width;
			
			if(bottom < this.cellCountY)
				bottom +=1;
			if(top > 0)
				top -=1;	
			
			g.setColor(Color.LIGHT_GRAY);
		    for(int i = top; i < bottom;i++){
		    	int posY = this.hLines[0][i];
		    	if(this.hLines[1][i] == 1){
		    		g.setColor(Color.DARK_GRAY);
		    		g.drawLine(leftEdge, posY, rightEdge, posY);
		    		g.setColor(Color.LIGHT_GRAY);
		    	}
		    	else if(this.hLines[1][i] == 3){
		    		g.setColor(Color.RED);
		    		g.drawLine(leftEdge, posY, rightEdge, posY);
		    		g.setColor(Color.LIGHT_GRAY);	
		    	}
		    	else
		    		g.drawLine(leftEdge, posY, rightEdge, posY);
		    }   
		}
		
		/**
		 * Draws vertical lines for the grid in the visible area
		 * @param g
		 */
		
		private void drawVertical(Graphics g) {
			int left = view.x/this.cellSize;
			int right = (view.x+view.width)/this.cellSize;
			int topEdge = this.view.y;
			int bottomEdge = this.view.y+this.view.height;
			
			if(left > 0)
				left -=1;
			if(right < this.cellCountX)
				right +=1;
			
			g.setColor(Color.LIGHT_GRAY);
			for(int i = left; i < right;i++){
				int posX = this.vLines[0][i];
				if(this.vLines[1][i] == 1){
		    		g.setColor(Color.DARK_GRAY);
		    		g.drawLine(posX, topEdge, posX, bottomEdge);
		    		g.setColor(Color.LIGHT_GRAY);
		    	}
				else if(this.vLines[1][i] == 3){
		    		g.setColor(Color.RED);
		    		g.drawLine(posX, topEdge, posX, bottomEdge);
		    		g.setColor(Color.LIGHT_GRAY);	
		    	}
		    	else
		    		g.drawLine(posX, topEdge, posX, bottomEdge);
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
		/**
		 * Gets the view area reference to the current view area
		 * @param r Rectangle of the view area from the scrollpane viewport
		 */
		
		public void getView(Rectangle r){
			this.view = r;
		}
}
