package com.beat;

import android.graphics.Point;

public class Friend {
		private Point point;
		private boolean isCrash;
		private double bomNum;
			
		public void setPoint(Point point)
		{
			this.point = point;
		}
		
		public Point getPoint()
		{
			return point;
		}
		
		public void setCrash(boolean isCrash)
		{
			this.isCrash = isCrash;
		}
		
		public boolean getCrash()
		{
			return isCrash;
		}
		
		public void setBomNum(double bomNum)
		{
			this.bomNum = bomNum;
		}
		
		public double getBomNum()
		{
			return bomNum;
		}
	
}
