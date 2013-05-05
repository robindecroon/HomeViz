package libraries.optionspinner;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * A button which also draws an arrow.
 * 
 * @author Niels Billen
 * @version 0.1
 */
public class ArrowButton extends View implements
		ListenerContainer<ButtonListener> {
	// Direction of the arrow.
	private int DIRECTION = DIRECTION_LEFT;

	// Flag for indicating the left direction.
	public static final int DIRECTION_LEFT = 0;
	// Flag for indicating the right direction.
	public static final int DIRECTION_RIGHT = 1;

	// Paint object for drawing the arrow.
	private Paint arrowPainter = new Paint(Paint.ANTI_ALIAS_FLAG
			| Paint.DITHER_FLAG);
	// Thickness of the arrow.
	private final int ARROW_THICKNESS = 4;

	/**
	 * Optimization recommended by the compiler. It is not good to allocate a
	 * lot of objects in the onDraw() method since it is called a lot of time.
	 * Therefore, I allocate a rectangle here and in the onDraw() method I use.<br>
	 * <br>
	 * if (!canvas.getClipBounds(rect)) return true;<br>
	 * <br>
	 * Instead of doing:<br>
	 * <br>
	 * Rect rect = canvas.getClipBounds();
	 */
	private final Rect rect = new Rect();

	// Listeners for push events on the button.
	private final ArrayList<ButtonListener> listeners = new ArrayList<ButtonListener>();

	/**
	 * Creates a new arrow button.
	 * 
	 * @param context
	 */
	public ArrowButton(Context context) {
		super(context);
		initialize();
	}

	/**
	 * 
	 * @param context
	 */
	public ArrowButton(Context context, int direction) {
		super(context);
		initialize();
		setArrowDirection(direction);
	}

	/**
	 * 
	 * @param context
	 * @param attrs
	 */
	public ArrowButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialize();
	}

	/**
	 * 
	 */
	private void initialize() {
		arrowPainter.setColor(Color.WHITE);
		arrowPainter.setStyle(Style.STROKE);
		arrowPainter.setStrokeWidth(ARROW_THICKNESS);
	}

	/**
	 * Sets the direction of the arrow.
	 * 
	 * @param direction
	 *            The direction for the arrow.
	 * @throws IllegalArgumentException
	 *             When the direction is not valid.
	 */
	public void setArrowDirection(int direction)
			throws IllegalArgumentException {
		if (direction != DIRECTION_LEFT && direction != DIRECTION_RIGHT)
			throw new IllegalArgumentException(
					"The given direction is not valid");
		DIRECTION = direction;
		invalidate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (!canvas.getClipBounds(rect))
			return;

		int size = ARROW_THICKNESS * 2;
		if (DIRECTION == DIRECTION_LEFT) {
			canvas.drawLine(rect.right - size, rect.top + size,
					rect.exactCenterX(), rect.centerY() + ARROW_THICKNESS / 2,
					arrowPainter);

			canvas.drawLine(rect.right - size, rect.bottom - size,
					rect.exactCenterX(), rect.centerY() + 1, arrowPainter);
		} else {
			canvas.drawLine(rect.left + size, rect.top + size,
					rect.exactCenterX(), rect.centerY() + ARROW_THICKNESS / 2,
					arrowPainter);

			canvas.drawLine(rect.left + size, rect.bottom - size,
					rect.exactCenterX(), rect.centerY() - 1, arrowPainter);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.findingemos.felicity.util.ListenerContainer#addListener(java.lang
	 * .Object)
	 */
	@Override
	public void addListener(ButtonListener listener) {
		if (listener != null)
			listeners.add(listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.findingemos.felicity.util.ListenerContainer#removeListener(java.lang
	 * .Object)
	 */
	@Override
	public void removeListener(ButtonListener listener) {
		listeners.remove(listener);
	}

	/**
	 * 
	 */
	public void notifyDown() {
		for (ButtonListener listener : listeners)
			listener.isDown();
	}

	/**
	 * 
	 */
	public void notifyUp() {
		for (ButtonListener listener : listeners)
			listener.isUp();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			notifyDown();
			return true;
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			notifyUp();
			return true;
		} else
			return super.onTouchEvent(event);
	}
}