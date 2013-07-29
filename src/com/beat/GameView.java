package com.beat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class GameView extends View {
	public float currentX;
	public float currentY;
	private Bitmap mine, back, friend;
	private Bitmap bom[] = new Bitmap[7];
	private Bitmap enimy[][] = new Bitmap[3][2];
	private Rect rect_bg, rect_kj1, rect_kj2;
	private int screenWidth;
	private int screenHeight;
	private int time;
	private ArrayList<Enimy> enimy_list = new ArrayList<Enimy>();
	private ArrayList<Friend> friend_list = new ArrayList<Friend>();
	// private int e_speed = 20;
	private int e_speed = 2;
	private int speed[]= new int[3];
	
	private int e_width = 40;
	private int e_height = 40;

	
	
	private int m_width = 50;
	private int m_height = 50;
	private Context context;
	private int mine_life = 5;
	private int mine_score = 0;
	private boolean gameover;

	public GameView(Context context, int screenWidth, int screenHeight) {
		super(context);
		this.context = context;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;

		// 设置我方的初始位置
		currentX = screenWidth / 2;
		currentY = screenHeight - 40;

		speed[0]=2;
		speed[1]=3;
		speed[2]=4;
		
		mine = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.mine0);
		back = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.background);
		friend = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.friend0);
		// enimy2 TODO
		bom[0] = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.boom1);
		bom[1] = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.boom2);
		bom[2] = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.boom3);
		bom[3] = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.boom4);
		bom[4] = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.boom5);
		bom[5] = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.boom6);
		bom[6] = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.boom7);
		enimy[0][0]=BitmapFactory.decodeResource(context.getResources(),
				R.drawable.pig00);
		enimy[0][1]=BitmapFactory.decodeResource(context.getResources(),
				R.drawable.pig01);
		enimy[1][0]=BitmapFactory.decodeResource(context.getResources(),
				R.drawable.pig10);
		enimy[1][1]=BitmapFactory.decodeResource(context.getResources(),
				R.drawable.pig11);
		enimy[2][0]=BitmapFactory.decodeResource(context.getResources(),
				R.drawable.pig20);
		enimy[2][1]=BitmapFactory.decodeResource(context.getResources(),
				R.drawable.pig21);
		
		rect_bg = new Rect(0, 0, back.getWidth(), back.getHeight());
		rect_kj1 = new Rect(0, 0, screenWidth, screenHeight);
		rect_kj2 = new Rect(0, -screenHeight, screenWidth, 0);
		setFocusable(true);
	}

	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 创建敌方组员
		createEnimy();
		createFriend();
		// 画背景
		drawBack(canvas);
		// 画我方组员
		drawMine(canvas);
		// 画敌方组员
		drawEnimy(canvas);
		drawFriend(canvas);
		// 画碰撞效果
		drawBom(canvas);

		// 敌方处理函数
		doEnimy();
		doFriend();
		// 背景处理
		doBack();
		// 游戏结束处理
		overDipose();
		time++;
	}

	private void drawBom(Canvas canvas) {
		for (int i = 0; i <= enimy_list.size() - 1; i++) {
			Enimy enimy = enimy_list.get(i);
			if (enimy.getCrash() == true) {
				canvas.drawBitmap(bom[(int) enimy.getBomNum()],
						enimy.getPoint().x, enimy.getPoint().y, null);
				enimy.setBomNum(enimy.getBomNum() + 0.5);
				if (enimy.getBomNum() == 7) {
					enimy_list.remove(i);
					mine_score++;
					sendMsg_score();
				}
			}
		}
		
		
		for (int i = 0; i <= friend_list.size() - 1; i++) {
			Friend friend = friend_list.get(i);
			if (friend.getCrash() == true) {
				canvas.drawBitmap(bom[(int) friend.getBomNum()],
						friend.getPoint().x, friend.getPoint().y, null);
				friend.setBomNum(friend.getBomNum() + 0.5);
				if (friend.getBomNum() == 7) {
					friend_list.remove(i);
				}
			}
		}
	}

	private void createFriend(){
		{
			if(Math.random()>0.99 & friend_list.isEmpty()){
				
				
				
				double random = Math.random();
				int x = 0;
				if (random > 0.5) {
					x = (int) (random * screenWidth) - m_width;
				} else {
					x = (int) (random * screenWidth) + m_width;
				}

				int y = (int) (Math.random() * -screenHeight);
				Friend friend = new Friend();
			
				friend.setPoint(new Point(x, y));// 设置敌方组员位置
				friend_list.add(friend);// 添加敌方组员
			}
		}
	}
	
	private void drawFriend(Canvas canvas){
		for (int i = 0; i <= friend_list.size() - 1; i++) {
			Friend e = friend_list.get(i);
		if (e.getCrash() == false) {
				canvas.drawBitmap(friend, e.getPoint().x,
							e.getPoint().y, null);
		
		}
		
	}
	}
	private void doFriend(){
		
		for (int i = 0; i <= friend_list.size() - 1; i++) {
			int x = friend_list.get(i).getPoint().x;
			int y = friend_list.get(i).getPoint().y;
			friend_list.get(i).setPoint(
					new Point(x, y + 1));
			if (y >= screenHeight) {
				friend_list.remove(i);
			}

			int e_right = x + e_width;
			int e_left = x;
			int m_right = (int) (currentX + m_width);
			int m_left = (int) currentX;

			int e_bottom = y + e_width;
			int e_top = y;
			int m_bottom = (int) (currentY + m_width);
			int m_top = (int) currentY;

			if ((Math.abs(e_right - m_left) < e_width + m_width && Math
					.abs(m_right - e_left) < e_width + m_width)

					&& (Math.abs(e_bottom - m_top) < e_height + m_height && Math
							.abs(m_bottom - e_top) < e_height + m_height)) {
				
				if (friend_list.get(i).getCrash()==false && mine_life > 0) {
					mine_life--;
					sendMsg_life();
				}
				friend_list.get(i).setCrash(true);

			}
		}
	}
	
	private void drawEnimy(Canvas canvas)

	{
		for (int i = 0; i <= enimy_list.size() - 1; i++) {
				Enimy e = enimy_list.get(i);
			if (e.getCrash() == false) {
				
				
					canvas.drawBitmap(enimy[e.getKind()][0], e.getPoint().x,
								e.getPoint().y, null);
			
			}
			
		}

	}

	private void drawMine(Canvas canvas) {
	
			canvas.drawBitmap(mine, currentX, currentY-25, null);

	}

	private void drawBack(Canvas canvas) {
		canvas.drawBitmap(back, rect_bg, rect_kj1, null);
		canvas.drawBitmap(back, rect_bg, rect_kj2, null);
	}

	private void createEnimy() {
		if (mine_score < 50) {
			if (time % 20 == 1) {
				// 随机产生一个点
				// m_width是我方组员个体的宽度，防止越界
				double random = Math.random();
				int x = 0;
				if (random > 0.5) {
					x = (int) (random * screenWidth) - m_width;
				} else {
					x = (int) (random * screenWidth);
				}

				int y = (int) (Math.random() * -screenHeight);
				Enimy enimy = null;
				double kind =Math.random();
				if (kind < 0.7) {
					enimy = new Enimy(0);// 新建敌方组员
				} else if(kind<0.9) {
					enimy = new Enimy(1);// 新建敌方组员
				}
				else {
					enimy = new Enimy(2);
				}

				enimy.setPoint(new Point(x, y));// 设置敌方组员位置
				enimy_list.add(enimy);// 添加敌方组员
			}
		} else if (mine_score < 100) {
			if (time % 15 == 1) {
				// 随机产生一个点
				// m_width是我方组员个体的宽度，防止越界
				double random = Math.random();
				int x = 0;
				if (random > 0.5) {
					x = (int) (random * screenWidth) - m_width;
				} else {
					x = (int) (random * screenWidth);
				}
				int y = (int) (Math.random() * -screenHeight);
				Enimy enimy = null;
				double kind =Math.random();
				if (kind < 0.6) {
					enimy = new Enimy(0);// 新建敌方组员
				} else if(kind<0.85) {
					enimy = new Enimy(1);// 新建敌方组员
				}
				else {
					enimy = new Enimy(2);
				}

				enimy.setPoint(new Point(x, y));// 设置敌方组员位置
				enimy_list.add(enimy);// 添加敌方组员
			}
		} else if (mine_score < 150) {
			if (time % 10 == 1) {
				// 随机产生一个点
				// m_width是我方组员个体的宽度，防止越界
				double random = Math.random();
				int x = 0;
				if (random > 0.5) {
					x = (int) (random * screenWidth) - m_width;
				} else {
					x = (int) (random * screenWidth);
				}
				int y = (int) (Math.random() * -screenHeight);
				Enimy enimy = null;
				double kind =Math.random();
				if (kind < 0.5) {
					enimy = new Enimy(0);// 新建敌方组员
				} else if(kind<0.7) {
					enimy = new Enimy(1);// 新建敌方组员
				}
				else {
					enimy = new Enimy(2);
				}

				enimy.setPoint(new Point(x, y));// 设置敌方组员位置
				enimy_list.add(enimy);// 添加敌方组员
			}
		} else {
			if (time % 3 == 1) {
				// 随机产生一个点
				// m_width是我方组员个体的宽度，防止越界
				double random = Math.random();
				int x = 0;
				if (random > 0.5) {
					x = (int) (random * screenWidth) - m_width;
				} else {
					x = (int) (random * screenWidth);
				}
				int y = (int) (Math.random() * -screenHeight);
				double kind =Math.random();
				Enimy enimy=null;
				if (kind < 0.4) {
					enimy = new Enimy(0);// 新建敌方组员
				} else if(kind<0.6) {
					enimy = new Enimy(1);// 新建敌方组员
				}
				else {
					enimy = new Enimy(2);
				}
				enimy.setPoint(new Point(x, y));// 设置敌方组员位置
				enimy_list.add(enimy);// 添加敌方组员
			}
		}
	}

	private void overDipose() {
		if (mine_life == 0 && gameover == false) {
			Builder builder = new AlertDialog.Builder((Activity) context);
			// 设置对话框的图标
			int	 i=getScore();
			
			builder.setIcon(R.drawable.pig10);
			// 设置对话框的标题
			builder.setTitle("GAME OVER");
			// 设置对话框显示的内容
			if (i>mine_score){
				builder.setMessage("你的得分为:" + mine_score +"\n最高分为:"+i+ "\n是否重新挑战");
				
			}
			else{
				builder.setMessage("你的得分为:" + mine_score +"\n之前最高分为"+i+"\n你碉堡了~"+"\n是否重新挑战");
				setScore(mine_score);
			}
			
			builder.setCancelable(false);
			builder.setPositiveButton("确定"
			// 为列表项的单击事件设置监听器
					, new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							enimy_list.removeAll(enimy_list);
							// 设置飞机的初始位置
							currentX = screenWidth / 2;
							currentY = screenHeight - 40;
							mine_life = 5;
							mine_score = 0;
							sendMsg_life();
							sendMsg_score();
							gameover = false;
						}

					});
			// 为对话框设置一个“取消”按钮
			builder.setNegativeButton("退出",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							BeatActivity.setRun(false);
							System.exit(0);
						}
					});
			// 创建、并显示对话框
			builder.create().show();

			gameover = true;
		}

	}

	private void doEnimy() {
		for (int i = 0; i <= enimy_list.size() - 1; i++) {
			int x = enimy_list.get(i).getPoint().x;
			int y = enimy_list.get(i).getPoint().y;
			enimy_list.get(i).setPoint(
					new Point(x, y + speed[enimy_list.get(i).getKind()]));
			if (y >= screenHeight) {
				enimy_list.remove(i);
				if (mine_life > 0) {
					mine_life--;
					sendMsg_life();
				}

			}

			int e_right = x + e_width;
			int e_left = x;
			int m_right = (int) (currentX + m_width);
			int m_left = (int) currentX;

			int e_bottom = y + e_width;
			int e_top = y;
			int m_bottom = (int) (currentY + m_width);
			int m_top = (int) currentY;

			if ((Math.abs(e_right - m_left) < e_width + m_width && Math
					.abs(m_right - e_left) < e_width + m_width)

					&& (Math.abs(e_bottom - m_top) < e_height + m_height && Math
							.abs(m_bottom - e_top) < e_height + m_height)) {
				enimy_list.get(i).setCrash(true);

			}
		}

	}

	private int getScore(){
		InputStream inStream = null;
		try {
			inStream = this.getContext().openFileInput("Score.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		String content=null;
		try {
			content=read(inStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Toast.makeText(this.getContext(),content+"   ~~~~~",Toast.LENGTH_SHORT).show();
		
		return Integer.valueOf(content);
		
	}
	
	private void setScore(int score){
		FileOutputStream os=null;
		try {
			os = this.getContext().openFileOutput("Score.txt", 0);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		try {
			
			os.write((score+"").getBytes());
			Toast.makeText(this.getContext(),"  set ~~~~~"+score,Toast.LENGTH_SHORT).show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String read(InputStream inStream) throws IOException
	{
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len;
		while((len=inStream.read(buffer))!=-1)
		{
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();
		outStream.close();
		inStream.close();
		return new String(data);
	}
	
	private void sendMsg_life() {
		Message msg = new Message();
		msg.what = mine_life;
		BeatActivity.getHandler_life().sendMessage(msg);
	}

	private void sendMsg_score() {
		Message msg = new Message();
		msg.what = mine_score;
		BeatActivity.getHandler_score().sendMessage(msg);
	}

	private void doBack() {
		//rect_kj1.top += 10;
		rect_kj1.bottom = rect_kj1.top + screenHeight;
		//rect_kj2.top += 10;
		rect_kj2.bottom = rect_kj2.top + screenHeight;

		if (rect_kj1.top >= screenHeight) {
			rect_kj1.top = -screenHeight;
			rect_kj1.bottom = rect_kj1.top + screenHeight;
		}
		if (rect_kj2.top >= screenHeight) {
			rect_kj2.top = -screenHeight;
			rect_kj2.bottom = rect_kj2.top + screenHeight;
		}
	}

	@Override
	// 触摸屏幕时保存前后两点坐标
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			currentX = event.getX() - m_width / 2;
			currentY = event.getY() - m_height / 2;
			doEdge(currentX, currentY);
			break;
		case MotionEvent.ACTION_MOVE:
			currentX = event.getX() - m_width / 2;
			currentY = event.getY() - m_height / 2;
			doEdge(currentX, currentY);
			break;
		case MotionEvent.ACTION_UP:
			break;
		}
		return true;
	}

	private void doEdge(float currentX, float currentY) {
		if (currentX <= 100) {
			currentX = 100;
		}

		if (currentX > screenWidth - m_width) {
			currentX = screenWidth - m_width;
		}

		if (currentY <= 100) {
			currentY = 100;
		}

		if (currentY > screenHeight - m_height) {
			currentY = screenHeight - m_height;
		}
	}
}
