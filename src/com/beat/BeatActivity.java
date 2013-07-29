package com.beat;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

public class BeatActivity extends Activity
{
	private GameView gameView = null;
	private static boolean run = true;
	private static Thread thread;
	private View column;
	private Handler handler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			// ֪ͨgameView����ػ�
			
			gameView.invalidate();
		}
	};
	private static TextView text_life;
	private static TextView text_score;

	private static Handler handler_life = new Handler()
	{
		public void handleMessage(Message msg)
		{
			String life = String.valueOf(msg.what);
			text_life.setText(life);
		}
	};
	private static Handler handler_score = new Handler()
	{
		public void handleMessage(Message msg)
		{
			String score = String.valueOf(msg.what);
			text_score.setText(score);
		}
	};
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// ȥ�����ڱ���
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// ȫ����ʾ
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
			WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		// ��ȡ���ڹ�����
		WindowManager windowManager = getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		
		// �����Ļ��͸�
		int screenWidth = display.getWidth();
		int screenHeight = display.getHeight();
		
		gameView = new GameView(this,screenWidth,screenHeight);
		setContentView(gameView);
		
		column = LayoutInflater.from(this).inflate(R.layout.column, null);
		addContentView(column, new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));

		text_life = (TextView)column.findViewById(R.id.text_life);
		text_score = (TextView)column.findViewById(R.id.text_score);
		text_life.setText("5");
		text_score.setText("0");
		
		thread = new Thread(){
			
			public void run()
			{
				while(run == true)
				{
					Message msg = new Message();
					handler.sendMessage(msg);
					try
					{
						Thread.sleep(10);
					}
					catch(Exception e){}
				}
				
				
				
			}};
			thread.start();
			
	}
	
	public static void setRun(boolean run)
	{
		BeatActivity.run = run;
	}
	
	public static Thread getThread()
	{
		return thread;
	}
	
	public static Handler getHandler_life()
	{
		return handler_life;
	}
	
	public static Handler getHandler_score()
	{
		return handler_score;
	}
}