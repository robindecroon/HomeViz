package libraries.stackoverflow;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

/**
 * A modified Spinner that doesn't automatically select the first entry in the list.
 * 
 * Shows the prompt if nothing is selected.
 * 
 * Limitations: does not display prompt if the entry list is empty.
 * 
 * source: <url>http://stackoverflow.com/a/3427058/1741111</url>
 */
public class NoDefaultSpinner extends Spinner {

	/**
	 * Instantiates a new no default spinner.
	 *
	 * @param context the context
	 */
	public NoDefaultSpinner(Context context) {
		super(context);
	}

	/**
	 * Instantiates a new no default spinner.
	 *
	 * @param context the context
	 * @param attrs the attrs
	 */
	public NoDefaultSpinner(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	/**
	 * Instantiates a new no default spinner.
	 *
	 * @param context the context
	 * @param attrs the attrs
	 * @param defStyle the def style
	 */
	public NoDefaultSpinner(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/* (non-Javadoc)
	 * @see android.widget.Spinner#setAdapter(android.widget.SpinnerAdapter)
	 */
	@Override
	public void setAdapter(SpinnerAdapter orig) {
		final SpinnerAdapter adapter = newProxy(orig);

		super.setAdapter(adapter);

		try {
			final Method m = AdapterView.class.getDeclaredMethod(
					"setNextSelectedPositionInt", int.class);
			m.setAccessible(true);
			m.invoke(this, -1);

			final Method n = AdapterView.class.getDeclaredMethod(
					"setSelectedPositionInt", int.class);
			n.setAccessible(true);
			n.invoke(this, -1);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * New proxy.
	 *
	 * @param obj the obj
	 * @return the spinner adapter
	 */
	protected SpinnerAdapter newProxy(SpinnerAdapter obj) {
		if (obj == null)
			return null;
		return (SpinnerAdapter) java.lang.reflect.Proxy.newProxyInstance(obj
				.getClass().getClassLoader(),
				new Class[] { SpinnerAdapter.class }, new SpinnerAdapterProxy(
						obj));
	}

	/**
	 * Intercepts getView() to display the prompt if position < 0.
	 */
	protected class SpinnerAdapterProxy implements InvocationHandler {

		/** The obj. */
		protected SpinnerAdapter obj;
		
		/** The get view. */
		protected Method getView;

		/**
		 * Instantiates a new spinner adapter proxy.
		 *
		 * @param obj the obj
		 */
		protected SpinnerAdapterProxy(SpinnerAdapter obj) {
			this.obj = obj;
			try {
				this.getView = SpinnerAdapter.class.getMethod("getView",
						int.class, View.class, ViewGroup.class);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		/* (non-Javadoc)
		 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
		 */
		@Override
		public Object invoke(Object proxy, Method m, Object[] args)
				throws Throwable {
			try {
				return m.equals(getView) && (Integer) (args[0]) < 0 ? getView(
						(Integer) args[0], (View) args[1], (ViewGroup) args[2])
						: m.invoke(obj, args);
			} catch (InvocationTargetException e) {
				throw e.getTargetException();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		/**
		 * Gets the view.
		 *
		 * @param position the position
		 * @param convertView the convert view
		 * @param parent the parent
		 * @return the view
		 * @throws IllegalAccessException the illegal access exception
		 */
		protected View getView(int position, View convertView, ViewGroup parent)
				throws IllegalAccessException {
			if (position < 0) {
				final TextView v = (TextView) ((LayoutInflater) getContext()
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
						.inflate(android.R.layout.simple_spinner_item, parent,
								false);
				v.setTextColor(Color.WHITE);
				v.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
				v.setHeight(400);
				v.setText(getPrompt());
				return v;
			}

			return obj.getView(position, convertView, parent);
		}

	}

}