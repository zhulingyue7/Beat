package com.beat;

import android.graphics.Point;
public class Enimy 
{
	/**�з�λ������*/
	private Point point;
	/**�ж��Ƿ���ײ*/
	private boolean isCrash;
	/**��ըͼƬ����*/
	private double bomNum;
	private int kind;
	private int isAct;
	
	
	public Enimy() 
	{
		
	}
	
	
	
	public Enimy(int kind){
		this.kind =kind;
	}
	public int getKind(){
		return kind; 
	}
	
	public void act(){
		isAct=1;
	}
	
	public int getAct(){
		return isAct;
	}
	
	public void stopAct(){
		isAct=0;
	}
	
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
