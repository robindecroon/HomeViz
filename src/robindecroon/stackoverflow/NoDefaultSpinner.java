package robindecroon.stackoverflow;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import robindecroon.homeviz.Constants;
import robindecroon.homeviz.R;
import robindecroon.homeviz.listeners.ActionBarSpinnerListener;
import robindecroon.layout.UsageContainerFragment;
import robindecroon.layout.YouFragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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
 * A modified Spinner that doesn't automatically select the first entry in the
 * list.
 * 
 * Shows the prompt if nothing is selected.
 * 
 * Limitations: does not display prompt if the entry list is empty.
 */
public class NoDefaultSpinner extends Spinner {

	private ActionBarSpinnerListener listener;

	private int lastPostion;
	
	private Context context;

	public NoDefaultSpinner(Context context) {
		super(context);
		this.context = context;
	}

	public NoDefaultSpinner(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;

	}

	public NoDefaultSpinner(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
	}

//	@Override
//	public void setSelection(int position) {
//		super.setSelection(position);
//		if (position == lastPostion && listener != null) {
//			switch (listener.getType()) {
//			case USAGE:
//				Fragment fragment = new UsageContainerFragment();
//				Bundle args = new Bundle();
//				args.putInt(Constants.USAGE_TYPE, position);
//				args.putInt(Constants.FRAGMENT_BUNDLE_TYPE, position);
//				fragment.setArguments(args);
//				((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
//						.replace(R.id.container, fragment).commit();
//				break;
//			case TOTAL:
//				Fragment fragment2 = new YouFragment();
//				((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
//						.replace(R.id.container, fragment2).commit();
//				break;
//			default:
//				break;
//			}
//
//		}
//		lastPostion = position;
////		 if (listener != null)
////		listener.onItemSelected(null, null, position, 0);
//	}

	public void setReSelectListener (
			ActionBarSpinnerListener listener) {
		this.listener = listener;
	}

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

	protected SpinnerAdapter newProxy(SpinnerAdapter obj) {
		if (obj == null) return null;
		return (SpinnerAdapter) java.lang.reflect.Proxy.newProxyInstance(obj
				.getClass().getClassLoader(),
				new Class[] { SpinnerAdapter.class }, new SpinnerAdapterProxy(
						obj));
	}

	/**
	 * Intercepts getView() to display the prompt if position < 0
	 */
	protected class SpinnerAdapterProxy implements InvocationHandler {

		protected SpinnerAdapter obj;
		protected Method getView;

		protected SpinnerAdapterProxy(SpinnerAdapter obj) {
			this.obj = obj;
			try {
				this.getView = SpinnerAdapter.class.getMethod("getView",
						int.class, View.class, ViewGroup.class);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

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