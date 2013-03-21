package libraries.nielsbillen;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * A widget that displays some options through which can be scrolled by using
 * two buttons.
 * 
 * @author Niels
 * @version 0.1
 */
public class OptionSpinner extends View implements
		ListenerContainer<SpinnerListener> {
	// The paint object for painting the text
	private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG
			| Paint.DITHER_FLAG);

	// Animation duration of the spinner
	private final int animation_duration = 200;
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

	// The list with available options.
	private final ArrayList<String> options = new ArrayList<String>();

	// The index in the options.
	private int index = 0;

	// Offset in the spinner for drawing the text.
	private float drawX1 = 0.5f;

	// Offset in the spinner for drawing the animation.
	private float drawX2 = 1000f;

	// The text to draw.
	private String currentText = "";

	// Second text for animation purposes.
	private String nextText = "";

	// The thread which animates this
	private AnimationThread thread = null;

	// Listener for changes in the spinner.
	private final ArrayList<SpinnerListener> listeners = new ArrayList<SpinnerListener>();

	private final ArrayList<Integer> animationStack = new ArrayList<Integer>();

	/**
	 * 
	 * @param context
	 */
	public OptionSpinner(Context context,
			ListenerContainer<ButtonListener> left,
			ListenerContainer<ButtonListener> right, String... options) {
		super(context);

		setLeftButton(left);
		setRightButton(right);
		setOptions(options);
		initialize();
	}
	
	public OptionSpinner(Context context,
			ListenerContainer<ButtonListener> left,
			ListenerContainer<ButtonListener> right, int index, String... options) {
		this(context, left, right, options);
		this.index = index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * 
	 * @param context
	 */
	public OptionSpinner(Context context) {
		super(context);
		initialize();
	}

	/**
	 * 
	 * @param context
	 * @param set
	 */
	public OptionSpinner(Context context, AttributeSet set) {
		super(context, set);
		initialize();
	}

	private void initialize() {
		paint.setColor(Color.WHITE);
		paint.setTextAlign(Align.CENTER);
		paint.setTextSize(32);
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

		float drawY = rect.exactCenterY() + paint.descent();

		float drawX = rect.left + this.drawX1 * rect.width();
		canvas.drawText(currentText, drawX, drawY, paint);

		drawX = rect.left + this.drawX2 * rect.width();
		canvas.drawText(nextText, drawX, drawY, paint);

	}

	/**
	 * 
	 * @param left
	 */
	public void setLeftButton(ListenerContainer<ButtonListener> left) {
		if (left != null) {
			left.addListener(new ButtonListener() {
				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * com.findingemos.felicity.visualization.ButtonListener#isUp()
				 */
				@Override
				public void isUp() {
				}

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * com.findingemos.felicity.visualization.ButtonListener#isDown
				 * ()
				 */
				@Override
				public void isDown() {
					moveToPrevious();
				}
			});
		}
	}

	/**
	 * 
	 * @param right
	 */
	public void setRightButton(ListenerContainer<ButtonListener> right) {
		if (right != null) {
			right.addListener(new ButtonListener() {
				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * com.findingemos.felicity.visualization.ButtonListener#isUp()
				 */
				@Override
				public void isUp() {
				}

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * com.findingemos.felicity.visualization.ButtonListener#isDown
				 * ()
				 */
				@Override
				public void isDown() {
					moveToNext();
				}
			});
		}
	}

	/**
	 * 
	 */
	private void moveToPrevious() {
		if (animationStack.size() > 0
				|| (thread != null && !thread.isFinished())) {
			animationStack.add(AnimationThread.LEFT);
			return;
		}
		thread = new AnimationThread(AnimationThread.LEFT, animation_duration);
		thread.start();
	}

	/**
	 * 
	 */
	private void moveToNext() {
		if (animationStack.size() > 0
				|| (thread != null && !thread.isFinished())) {
			animationStack.add(AnimationThread.RIGHT);
			return;
		}
		thread = new AnimationThread(AnimationThread.RIGHT, animation_duration);
		thread.start();
	}

	/**
	 * 
	 * @param options
	 */
	public void setOptions(String... options) throws IllegalArgumentException {
		if (options == null)
			throw new IllegalArgumentException("The given options are null!");
		if (options.length == 0)
			throw new IllegalArgumentException("Give at least one option!");
		this.options.clear();

		for (String string : options)
			this.options.add(string);
		index = Math.min(index, options.length - 1);
		currentText = this.options.get(index);
	}

	/**
	 * A thread which performs the animation of the option spiner.
	 * 
	 * @author Niels
	 * @version 0.1
	 */
	public class AnimationThread extends Thread {
		private boolean isFinished = false; // Whether the animation is
											// finished.
		private int direction; // Direction for the animation.
		private long duration; // Duration for the animation.
		private final int fps = 90; // Number of redrawing frames per second.
		public static final int LEFT = 0; // Field for indicating the left
											// direction.
		public static final int RIGHT = 1; // Field for indicating the right
											// direction.
		private int nextIndex; // The next index, based on the current position.

		/**
		 * 
		 * @param direction
		 * @param duration
		 */
		public AnimationThread(int direction, long duration) {
			if (direction == LEFT) {
				drawX2 = 1.5f;
				nextIndex = index == 0 ? options.size() - 1 : index - 1;
			} else if (direction == RIGHT) {
				drawX2 = -0.5f;
				nextIndex = (index + 1) % options.size();
			} else {
				drawX2 = 1.5f;
				nextIndex = index == 0 ? options.size() - 1 : index - 1;
			}
			this.direction = direction;
			this.duration = duration;

			OptionSpinner.this.nextText = options.get(nextIndex);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			float nbOfFrames = (float) (duration) * (float) (fps) / 1000.0f;
			float incrementPerFrame = 1.f / nbOfFrames;
			float step = 0;
			long startTime = System.currentTimeMillis();

			while (step < 1
					&& (System.currentTimeMillis() - startTime) < duration) {
				step += incrementPerFrame;

				if (direction == LEFT) {
					drawX1 -= incrementPerFrame;
					drawX2 = Math.max(0.5f, drawX2 - incrementPerFrame);
					OptionSpinner.this.postInvalidate();
				} else if (direction == RIGHT) {
					drawX1 += incrementPerFrame;
					drawX2 = Math.min(0.5f, drawX2 + incrementPerFrame);
					OptionSpinner.this.postInvalidate();
				}

				try {
					sleep((long) Math.min((duration / nbOfFrames), 100));
				} catch (InterruptedException e) {
				}
			}
			onFinish();
		}

		/**
		 * 
		 */
		private void onFinish() {
			// Assign correct positions.
			drawX1 = 0.5f;
			drawX2 = 1000f;

			// Change the option spinner
			OptionSpinner.this.index = nextIndex;
			OptionSpinner.this.currentText = nextText;
			OptionSpinner.this.nextText = "";
			OptionSpinner.this.postInvalidate();

			// Notify change.
			notifyChanged(index, currentText);

			// Check the animation stack
			if (animationStack.size() > 0) {
				thread = new AnimationThread(animationStack.remove(0),
						animation_duration);
				thread.start();
			}
			// Set finished
			isFinished = true;
		}

		/**
		 * 
		 * @return
		 */
		public boolean isFinished() {
			return isFinished;
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
	public void addListener(SpinnerListener listener) {
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
	public void removeListener(SpinnerListener listener) {
		listeners.remove(listener);
	}

	/**
	 * 
	 * @param index
	 * @param name
	 */
	private void notifyChanged(int index, String name) {
		for (SpinnerListener listener : listeners)
			listener.optionChanged(index, name);
	}
}