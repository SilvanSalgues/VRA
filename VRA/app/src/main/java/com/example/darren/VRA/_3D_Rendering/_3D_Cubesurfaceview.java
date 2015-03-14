// Copyright 2015 Darren McNeely. All Rights Reserved.

package com.example.darren.VRA._3D_Rendering;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class _3D_Cubesurfaceview extends GLSurfaceView {

	float touchedX = 0;
	float touchedY = 0;
	_3D_Cuberenderer renderer;
	public _3D_Cubesurfaceview(Context context) {
		super(context);
		setEGLContextClientVersion(2);
        setRenderer(renderer = new _3D_Cuberenderer(this));
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			touchedX = event.getX();
			touchedY = event.getY();
		} else if (event.getAction() == MotionEvent.ACTION_MOVE)
		{
			renderer.xAngle += (touchedX - event.getX())/2f;
			renderer.yAngle += (touchedY - event.getY())/2f;

			touchedX = event.getX();
			touchedY = event.getY();
		}
		return true;

	}
}